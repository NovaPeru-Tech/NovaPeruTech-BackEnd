package com.novaperutech.veyra.platform.nursing.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record RoomNumber(String roomNumber) {
 public RoomNumber(){this(UUID.randomUUID().toString());}
public RoomNumber {
     if (roomNumber ==null|| roomNumber.isBlank())
     {
         throw  new IllegalArgumentException("Room record id cannot be null or blank");
     }

}
}
