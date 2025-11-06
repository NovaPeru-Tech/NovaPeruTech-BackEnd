package com.novaperutech.veyra.platform.profiles.domain.model.aggregates;

import com.novaperutech.veyra.platform.profiles.domain.model.commands.CreateBusinessProfileCommand;
import com.novaperutech.veyra.platform.profiles.domain.model.valueobjects.BusinessName;
import com.novaperutech.veyra.platform.profiles.domain.model.valueobjects.Ruc;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class BusinessProfile extends Profile{
@Embedded
    private BusinessName businessName;
@Embedded
private Ruc ruc;
protected  BusinessProfile(){super();}
    public BusinessProfile(CreateBusinessProfileCommand command){
    super(command.emailAddress(),command.street(),command.number(),command.city(),command.postalCode(),command.country(),command.photoUrl(),command.phoneNumber());
     this.businessName= new BusinessName(command.businessName());
     this.ruc= new Ruc(command.ruc());

    }
}
