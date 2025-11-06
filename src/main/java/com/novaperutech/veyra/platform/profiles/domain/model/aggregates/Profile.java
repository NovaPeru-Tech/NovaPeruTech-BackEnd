package com.novaperutech.veyra.platform.profiles.domain.model.aggregates;

import com.novaperutech.veyra.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.novaperutech.veyra.platform.profiles.domain.model.valueobjects.PhoneNumber;
import com.novaperutech.veyra.platform.profiles.domain.model.valueobjects.Photo;
import com.novaperutech.veyra.platform.profiles.domain.model.valueobjects.StreetAddress;
import com.novaperutech.veyra.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class Profile extends AuditableAbstractAggregateRoot<Profile> {
    @Embedded
    private EmailAddress emailAddress;
    @Embedded
    private  StreetAddress streetAddress;
    @Embedded
    private Photo photo;
    @Embedded
    private PhoneNumber phoneNumber;
    protected Profile() {
    }
    protected Profile(String emailAddress,  String street,
                      String number,
                      String city,
                      String postalCode,
                      String country, String photo, String phoneNumber) {
        this.phoneNumber = new PhoneNumber(phoneNumber);
        this.emailAddress = new EmailAddress(emailAddress);
        this.streetAddress = new StreetAddress(street, number, city, postalCode, country);
        this.photo = new Photo (photo);
    }
    public void updateEmail(EmailAddress newEmail) {
        this.emailAddress = newEmail;
    }

    public void updateAddress(StreetAddress newAddress) {
        this.streetAddress = newAddress;
    }

    public void updatePhoto(Photo newPhoto) {
        this.photo = newPhoto;
    }
    public void updatePhoneNumber(PhoneNumber newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }

}
