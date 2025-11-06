package com.novaperutech.veyra.platform.profiles.application.acl;

import com.novaperutech.veyra.platform.profiles.domain.model.commands.CreatePersonProfileCommand;
import com.novaperutech.veyra.platform.profiles.domain.model.queries.GetPersonProfileByDniQuery;
import com.novaperutech.veyra.platform.profiles.domain.model.valueobjects.Dni;
import com.novaperutech.veyra.platform.profiles.domain.services.PersonProfileCommandService;
import com.novaperutech.veyra.platform.profiles.domain.services.PersonProfileQueryService;
import com.novaperutech.veyra.platform.profiles.interfaces.acl.ProfilesContextFacade;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ProfileContextFacadeImpl implements ProfilesContextFacade {
   private final PersonProfileCommandService personProfileCommandService;
   private final PersonProfileQueryService personProfileQueryService;


    public ProfileContextFacadeImpl(PersonProfileCommandService personProfileCommandService, PersonProfileQueryService personProfileQueryService) {
        this.personProfileCommandService = personProfileCommandService;
        this.personProfileQueryService = personProfileQueryService;
    }

    @Override
    public Long createPersonProfile(String dni, String firstName, String lastName,
       LocalDate birthDate, Integer Age, String emailAddress,String street, String number,
       String city, String postalCode,String country, String photo, String phoneNumber) {
        var createPersonProfileCommand=
        new CreatePersonProfileCommand
        (dni,firstName,lastName,birthDate,Age,emailAddress,street,number,city,postalCode,country,photo,phoneNumber);
        var personProfile= personProfileCommandService.handle(createPersonProfileCommand);
        return personProfile.isEmpty()?Long.valueOf(0L):personProfile.get().getId();
    }

    @Override
    public Long fetchPersonProfileIdByDni(String dni) {
        var getProfileByDniQuery= new GetPersonProfileByDniQuery(new Dni(dni));
        var personProfile= personProfileQueryService.handle(getProfileByDniQuery);
        return personProfile.isEmpty()?Long.valueOf(0L):personProfile.get().getId();
    }
}
