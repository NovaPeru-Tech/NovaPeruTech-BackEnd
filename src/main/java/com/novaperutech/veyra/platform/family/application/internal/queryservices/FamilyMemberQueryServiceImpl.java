package com.novaperutech.veyra.platform.family.application.internal.queryservices;

import com.novaperutech.veyra.platform.family.domain.model.aggregates.FamilyMember;
import com.novaperutech.veyra.platform.family.domain.model.queries.GetFamilyMemberByIdQuery;
import com.novaperutech.veyra.platform.family.domain.model.queries.GetFamilyMemberByUserIdQuery;
import com.novaperutech.veyra.platform.family.domain.model.queries.GetResidentsByUserIdQuery;
import com.novaperutech.veyra.platform.family.domain.services.FamilyMemberQueryService;
import com.novaperutech.veyra.platform.family.infrastructure.persistence.jpa.repositories.FamilyMemberRepository;
import com.novaperutech.veyra.platform.family.domain.model.valueobjects.ResidentId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class FamilyMemberQueryServiceImpl implements FamilyMemberQueryService {
    private final FamilyMemberRepository familyMemberRepository;

    public FamilyMemberQueryServiceImpl(FamilyMemberRepository familyMemberRepository) {
        this.familyMemberRepository = familyMemberRepository;
    }


    @Override
    public List<ResidentId> handle(GetResidentsByUserIdQuery query) {
        var familyMember = familyMemberRepository.findByUserId(query.userId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No family member profile found for user with id " + query.userId()
                ));

        return new ArrayList<>(familyMember.getResidentIds());
    }

    @Override
    public Optional<FamilyMember> handle(GetFamilyMemberByIdQuery query) {
        return familyMemberRepository.findById(query.familyMemberId());
    }

    @Override
    public Optional<FamilyMember> handle(GetFamilyMemberByUserIdQuery query) {
        return familyMemberRepository.findByUserId(query.userId());
    }
}
