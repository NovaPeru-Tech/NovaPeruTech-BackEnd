package com.novaperutech.veyra.platform.payments.infrastructure.persistence.stripe;

import com.novaperutech.veyra.platform.payments.domain.model.valueobjects.PlanType;
import com.novaperutech.veyra.platform.payments.domain.model.valueobjects.SubscriptionPeriod;
import com.novaperutech.veyra.platform.payments.infrastructure.persistence.stripe.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class StripeServiceImpl implements StripeService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    private final Map<String, String> priceIds = new HashMap<>();
    private final Map<String, String> productIds = new HashMap<>();

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
        log.info("Initializing Stripe with API key");
        initializePrices();
    }

    private void initializePrices() {
        try {
            createOrUpdateProduct("Family Plan", PlanType.FAMILY);
            createOrUpdateProduct("Nursing Home Plan", PlanType.NURSING_HOME);
            log.info("Stripe prices initialized successfully");
        } catch (StripeException e) {
            log.error("Error initializing Stripe prices: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to initialize Stripe prices", e);
        }
    }

    private void createOrUpdateProduct(String name, PlanType planType) throws StripeException {
        log.info("Creating product and prices for: {}", name);
        ProductCreateParams productParams = ProductCreateParams.builder()
                .setName(name)
                .setDescription("Subscription plan for " + name)
                .build();
        Product product = Product.create(productParams);
        productIds.put(planType.name(), product.getId());
        PriceCreateParams monthlyPriceParams = PriceCreateParams.builder()
                .setProduct(product.getId())
                .setUnitAmount((long) (planType.getMonthlyPrice() * 100))
                .setCurrency("usd")
                .setRecurring(
                        PriceCreateParams.Recurring.builder()
                                .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                                .build()
                )
                .setNickname(name + " - Monthly")
                .build();
        Price monthlyPrice = Price.create(monthlyPriceParams);
        priceIds.put(planType.name() + "_MONTHLY", monthlyPrice.getId());
        PriceCreateParams annualPriceParams = PriceCreateParams.builder()
                .setProduct(product.getId())
                .setUnitAmount((long) (planType.getAnnualPrice() * 100))
                .setCurrency("usd")
                .setRecurring(
                        PriceCreateParams.Recurring.builder()
                                .setInterval(PriceCreateParams.Recurring.Interval.YEAR)
                                .build()
                )
                .setNickname(name + " - Annual")
                .build();
        Price annualPrice = Price.create(annualPriceParams);
        priceIds.put(planType.name() + "_ANNUALLY", annualPrice.getId());

        log.info("Created prices for {}: Monthly={}, Annual={}",
                name, monthlyPrice.getId(), annualPrice.getId());
    }

    @Override
    public Customer createOrGetCustomer(Long userId, String email) {
        try {
            log.info("Creating or retrieving customer for userId: {}", userId);
            CustomerSearchParams searchParams = CustomerSearchParams.builder()
                    .setQuery("metadata['userId']:'" + userId + "'")
                    .build();
            CustomerSearchResult result = Customer.search(searchParams);

            if (!result.getData().isEmpty()) {
                Customer existingCustomer = result.getData().get(0);
                log.info("Found existing customer: {}", existingCustomer.getId());
                return existingCustomer;
            }
            CustomerCreateParams.Builder paramsBuilder = CustomerCreateParams.builder()
                    .putMetadata("userId", userId.toString());

            if (email != null && !email.isBlank()) {
                paramsBuilder.setEmail(email);
            }

            Customer newCustomer = Customer.create(paramsBuilder.build());
            log.info("Created new customer: {}", newCustomer.getId());
            return newCustomer;

        } catch (StripeException e) {
            log.error("Error creating Stripe customer for userId {}: {}", userId, e.getMessage(), e);
            throw new RuntimeException("Error creating Stripe customer: " + e.getMessage(), e);
        }
    }

    @Override
    public com.stripe.model.Subscription createSubscription(
            String customerId,
            PlanType planType,
            SubscriptionPeriod period,
            String paymentMethodId) {
        try {
            log.info("Creating subscription for customer: {}, plan: {}, period: {}",
                    customerId, planType, period);
            PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodId);
            paymentMethod.attach(PaymentMethodAttachParams.builder()
                    .setCustomer(customerId)
                    .build());
            log.info("Payment method {} attached to customer {}", paymentMethodId, customerId);
            Customer customer = Customer.retrieve(customerId);
            customer.update(CustomerUpdateParams.builder()
                    .setInvoiceSettings(
                            CustomerUpdateParams.InvoiceSettings.builder()
                                    .setDefaultPaymentMethod(paymentMethodId)
                                    .build()
                    )
                    .build());

            String priceId = getPriceId(planType, period);
            log.info("Using price ID: {}", priceId);

            SubscriptionCreateParams params = SubscriptionCreateParams.builder()
                    .setCustomer(customerId)
                    .addItem(
                            SubscriptionCreateParams.Item.builder()
                                    .setPrice(priceId)
                                    .build()
                    )
                    .setPaymentBehavior(SubscriptionCreateParams.PaymentBehavior.DEFAULT_INCOMPLETE)
                    .setPaymentSettings(
                            SubscriptionCreateParams.PaymentSettings.builder()
                                    .setPaymentMethodTypes(
                                            List.of(
                                                    SubscriptionCreateParams.PaymentSettings.PaymentMethodType.CARD
                                            )
                                    )
                                    .build()
                    )
                    .addExpand("latest_invoice.payment_intent")
                    .putMetadata("planType", planType.name())
                    .putMetadata("period", period.name())
                    .build();

            com.stripe.model.Subscription subscription = com.stripe.model.Subscription.create(params);
            log.info("Subscription created successfully: {}", subscription.getId());
            return subscription;

        } catch (StripeException e) {
            log.error("Error creating Stripe subscription: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating Stripe subscription: " + e.getMessage(), e);
        }
    }

    @Override
    public com.stripe.model.Subscription updateSubscription(
            String subscriptionId,
            PlanType planType,
            SubscriptionPeriod period) {
        try {
            log.info("Updating subscription: {} to plan: {}, period: {}",
                    subscriptionId, planType, period);

            com.stripe.model.Subscription subscription =
                    com.stripe.model.Subscription.retrieve(subscriptionId);

            String newPriceId = getPriceId(planType, period);
            String currentItemId = subscription.getItems().getData().get(0).getId();

            SubscriptionUpdateParams params = SubscriptionUpdateParams.builder()
                    .addItem(
                            SubscriptionUpdateParams.Item.builder()
                                    .setId(currentItemId)
                                    .setPrice(newPriceId)
                                    .build()
                    )
                    .setProrationBehavior(SubscriptionUpdateParams.ProrationBehavior.CREATE_PRORATIONS)
                    .putMetadata("planType", planType.name())
                    .putMetadata("period", period.name())
                    .build();

            com.stripe.model.Subscription updatedSubscription = subscription.update(params);
            log.info("Subscription updated successfully: {}", subscriptionId);
            return updatedSubscription;

        } catch (StripeException e) {
            log.error("Error updating Stripe subscription {}: {}", subscriptionId, e.getMessage(), e);
            throw new RuntimeException("Error updating Stripe subscription: " + e.getMessage(), e);
        }
    }

    @Override
    public com.stripe.model.Subscription cancelSubscription(String subscriptionId) {
        try {
            log.info("Canceling subscription: {}", subscriptionId);

            com.stripe.model.Subscription subscription =
                    com.stripe.model.Subscription.retrieve(subscriptionId);

            com.stripe.model.Subscription canceledSubscription = subscription.cancel();
            log.info("Subscription canceled successfully: {}", subscriptionId);
            return canceledSubscription;

        } catch (StripeException e) {
            log.error("Error canceling Stripe subscription {}: {}", subscriptionId, e.getMessage(), e);
            throw new RuntimeException("Error canceling Stripe subscription: " + e.getMessage(), e);
        }
    }

    @Override
    public com.stripe.model.Subscription retrieveSubscription(String subscriptionId) {
        try {
            log.info("Retrieving subscription: {}", subscriptionId);
            return com.stripe.model.Subscription.retrieve(subscriptionId);
        } catch (StripeException e) {
            log.error("Error retrieving Stripe subscription {}: {}", subscriptionId, e.getMessage(), e);
            throw new RuntimeException("Error retrieving Stripe subscription: " + e.getMessage(), e);
        }
    }

    @Override
    public PaymentIntent createPaymentIntent(Long amountInCents, String currency, String customerId) {
        try {
            log.info("Creating payment intent: amount={}, currency={}, customer={}",
                    amountInCents, currency, customerId);

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amountInCents)
                    .setCurrency(currency)
                    .setCustomer(customerId)
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                    .setEnabled(true)
                                    .build()
                    )
                    .addExpand("latest_charge")
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);
            log.info("Payment intent created: {}", paymentIntent.getId());
            return paymentIntent;

        } catch (StripeException e) {
            log.error("Error creating payment intent: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating payment intent: " + e.getMessage(), e);
        }
    }


    @Override
    public PaymentIntent retrievePaymentIntent(String paymentIntentId) {
        try {
            log.info("Retrieving payment intent: {}", paymentIntentId);
            return PaymentIntent.retrieve(paymentIntentId);
        } catch (StripeException e) {
            log.error("Error retrieving payment intent {}: {}", paymentIntentId, e.getMessage(), e);
            throw new RuntimeException("Error retrieving payment intent: " + e.getMessage(), e);
        }
    }

    @Override
    public String getPriceId(PlanType planType, SubscriptionPeriod period) {
        String key = planType.name() + "_" + period.name();
        String priceId = priceIds.get(key);

        if (priceId == null) {
            throw new IllegalArgumentException(
                    "Price not found for plan: " + planType + ", period: " + period
            );
        }

        return priceId;
    }
}