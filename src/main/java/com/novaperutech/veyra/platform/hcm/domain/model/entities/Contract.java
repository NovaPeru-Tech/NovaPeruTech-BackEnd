package com.novaperutech.veyra.platform.hcm.domain.model.entities;

import com.novaperutech.veyra.platform.hcm.domain.model.aggregates.Staff;
import com.novaperutech.veyra.platform.hcm.domain.model.valueobjetcs.*;
import com.novaperutech.veyra.platform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDate;

@Entity
@Getter
public class Contract extends AuditableModel {
@ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;
@Embedded
    private ContractPeriod contractPeriod;
@Embedded
    private TypeOfContract typeOfContract;
@Enumerated(EnumType.STRING)
private ContractStatus contractStatus;
@Enumerated(EnumType.STRING)
private StaffRole staffRole;
@Enumerated(EnumType.STRING)
private WorkShift workShift;
public Contract(Staff staff,LocalDate startDate, LocalDate endDate,String typeOfContract,String staffRole,String workShift){
    this.staff = staff;
    this.contractPeriod = new ContractPeriod(startDate,endDate);
    this.typeOfContract = new TypeOfContract(typeOfContract);
    this.staffRole= StaffRole.valueOf(staffRole);
    this.workShift= WorkShift.valueOf(workShift);
    this.contractStatus=ContractStatus.ACTIVE;
    }
    public Contract(){this.contractStatus = ContractStatus.PENDING;}
}
