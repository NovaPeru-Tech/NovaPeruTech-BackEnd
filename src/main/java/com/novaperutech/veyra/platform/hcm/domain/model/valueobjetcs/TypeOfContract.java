package com.novaperutech.veyra.platform.hcm.domain.model.valueobjetcs;

public record TypeOfContract(String typeOfContract) {
    public TypeOfContract{
        if (typeOfContract==null|| typeOfContract.isBlank()){
            throw  new IllegalArgumentException(" type of contract cannot be null or blank");
        }
    }
}
