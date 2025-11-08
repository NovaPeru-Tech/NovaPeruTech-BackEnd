package com.novaperutech.veyra.platform.hcm.application.internal.queryservices;

import com.novaperutech.veyra.platform.hcm.domain.model.aggregates.Staff;
import com.novaperutech.veyra.platform.hcm.domain.model.queries.GetAllStaffQuery;
import com.novaperutech.veyra.platform.hcm.domain.model.queries.GetStaffByIdQuery;
import com.novaperutech.veyra.platform.hcm.domain.services.StaffQueryServices;
import com.novaperutech.veyra.platform.hcm.infrastructure.persistence.jpa.repositories.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class StaffQueryServiceImpl implements StaffQueryServices {
    private final StaffRepository staffRepository;

    public StaffQueryServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public Optional<Staff> handle(GetStaffByIdQuery query) {
      return staffRepository.findById(query.id());
    }

    @Override
    public List<Staff> handle(GetAllStaffQuery query) {
        return staffRepository.findAll();
    }
}
