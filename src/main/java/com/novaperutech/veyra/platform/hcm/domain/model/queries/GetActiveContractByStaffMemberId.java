package com.novaperutech.veyra.platform.hcm.domain.model.queries;

public record GetActiveContractByStaffMemberId(Long staffId) {
    public GetActiveContractByStaffMemberId {
        if (staffId==null|| staffId<=0){
            throw new IllegalArgumentException("StaffId cannot be null or less then or equal to 0");
        }
    }
}
