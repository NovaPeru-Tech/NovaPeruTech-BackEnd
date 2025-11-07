package com.novaperutech.veyra.platform.profiles.domain.model.aggregates;

import com.novaperutech.veyra.platform.profiles.domain.model.commands.CreatePersonProfileCommand;
import com.novaperutech.veyra.platform.profiles.domain.model.valueobjects.Age;
import com.novaperutech.veyra.platform.profiles.domain.model.valueobjects.BirthDate;
import com.novaperutech.veyra.platform.profiles.domain.model.valueobjects.Dni;
import com.novaperutech.veyra.platform.profiles.domain.model.valueobjects.PersonName;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class PersonProfile extends Profile{
    @Embedded
    private PersonName personName;
    @Embedded
    private BirthDate birthDate;
    @Embedded
    private Age age;
    @Embedded
    private Dni dni;
    protected PersonProfile(){super();}
    public PersonProfile(CreatePersonProfileCommand command){
        super(command.emailAddress(),command.street(),command.number(),command.city(),command.postalCode(),command.country(),command.photo(),command.phoneNumber());
        this.personName= new PersonName(command.firstName(),command.lastName());
        this.birthDate=new BirthDate(command.birthDate());
        this.age= new Age(command.Age());
        this.dni=new Dni(command.dni());
    }
    public PersonProfile updatePersonProfile(String dni, String firstName, String lastName, LocalDate birthDate, Integer Age, String emailAddress, String street,
                                             String number,String city,String postalCode,String country, String photo, String phoneNumber){
        this.personName= new PersonName(firstName, lastName);
        this.dni=new Dni(dni);
        this.age= new Age(Age);
        this.birthDate=new BirthDate(birthDate);
        updateAddress(street, number, city, postalCode, country);
        updateEmail(emailAddress);
        updatePhoto(photo);
        updatePhoneNumber(phoneNumber);
        return this;


    }
}
