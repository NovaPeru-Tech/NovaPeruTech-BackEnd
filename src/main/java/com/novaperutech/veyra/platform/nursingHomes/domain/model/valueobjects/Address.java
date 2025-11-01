package com.novaperutech.veyra.platform.nursingHomes.domain.model.valueobjects;

import com.novaperutech.veyra.platform.nursingHomes.domain.exceptions.NullAddressException;
import jakarta.persistence.Embeddable;

@Embeddable
public record Address(String address) {
    public Address{
        if (address==null||address.isBlank()){
           throw new NullAddressException(address);
        }
    }
}
