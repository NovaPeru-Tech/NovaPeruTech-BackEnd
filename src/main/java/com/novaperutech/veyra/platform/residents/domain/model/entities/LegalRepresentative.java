package com.novaperutech.veyra.platform.residents.domain.model.entities;
import com.novaperutech.veyra.platform.residents.domain.model.valueobjects.Name;
import com.novaperutech.veyra.platform.residents.domain.model.valueobjects.PhoneNumber;
import com.novaperutech.veyra.platform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class LegalRepresentative extends AuditableModel {
@Embedded
@AttributeOverrides(value = {
        @AttributeOverride(name = "firstName",column =@Column(name="Guardian_first_name") ),
        @AttributeOverride(name = "lastName",column =@Column(name="Guardian_last_name") )
})
    private Name name ;
@Embedded
@AttributeOverride(name = "phoneNumber",column =@Column(name="Guardian_phone_number") )
private PhoneNumber phoneNumber ;
public LegalRepresentative(String firstName, String lastName,String phoneNumber){
    this.name= new Name(firstName,lastName);
    this.phoneNumber=new PhoneNumber(phoneNumber);
}
}
