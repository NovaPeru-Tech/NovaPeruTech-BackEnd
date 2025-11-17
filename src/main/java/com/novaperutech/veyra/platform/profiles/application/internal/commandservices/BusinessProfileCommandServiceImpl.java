package com.novaperutech.veyra.platform.profiles.application.internal.commandservices;

import com.novaperutech.veyra.platform.profiles.domain.model.aggregates.BusinessProfile;
import com.novaperutech.veyra.platform.profiles.domain.model.commands.CreateBusinessProfileCommand;
import com.novaperutech.veyra.platform.profiles.domain.model.valueobjects.BusinessName;
import com.novaperutech.veyra.platform.profiles.domain.model.valueobjects.Ruc;
import com.novaperutech.veyra.platform.profiles.domain.services.BusinessProfileCommandService;
import com.novaperutech.veyra.platform.profiles.infrastructure.persistence.jpa.repositories.BusinessProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BusinessProfileCommandServiceImpl implements BusinessProfileCommandService {
private final BusinessProfileRepository businessProfileRepository;

    public BusinessProfileCommandServiceImpl(BusinessProfileRepository businessProfileRepository) {
        this.businessProfileRepository = businessProfileRepository;
    }

    @Override
    public Optional<BusinessProfile> handle(CreateBusinessProfileCommand command) {
        var businessName= new BusinessName(command.businessName());
        var ruc= new Ruc(command.ruc());
        if (businessProfileRepository.existsByBusinessName(businessName)){
            throw new IllegalArgumentException("Business name already exists");
        }
        else if (businessProfileRepository.existsByRuc(ruc)){
            throw new IllegalArgumentException("Ruc already exists");
        }
        var businessProfile= new BusinessProfile(command);
        try{
            businessProfileRepository.save(businessProfile);
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
        return Optional.of(businessProfile);
    }
}
