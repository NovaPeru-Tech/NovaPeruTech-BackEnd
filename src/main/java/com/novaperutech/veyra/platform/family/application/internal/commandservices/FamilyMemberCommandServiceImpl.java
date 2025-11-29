package com.novaperutech.veyra.platform.family.application.internal.commandservices;
import com.novaperutech.veyra.platform.family.application.internal.outboundservices.acl.ExternalNursingService;
import com.novaperutech.veyra.platform.family.domain.services.FamilyMemberCommandService;
import com.novaperutech.veyra.platform.family.infrastructure.persistence.jpa.repositories.FamilyMemberRepository;
import org.springframework.stereotype.Service;

@Service
public class FamilyMemberCommandServiceImpl implements FamilyMemberCommandService {
    private final FamilyMemberRepository familyMemberRepository;
    private final ExternalNursingService externalNursingService;
    public FamilyMemberCommandServiceImpl(FamilyMemberRepository familyMemberRepository, ExternalNursingService externalNursingService) {
        this.familyMemberRepository = familyMemberRepository;
        this.externalNursingService = externalNursingService;
    }


}
