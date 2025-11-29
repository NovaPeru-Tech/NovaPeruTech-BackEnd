package com.novaperutech.veyra.platform.family.interfaces.rest;

import com.novaperutech.veyra.platform.family.domain.services.FamilyMemberCommandService;
import com.novaperutech.veyra.platform.family.domain.services.FamilyMemberQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/family-members",produces = APPLICATION_JSON_VALUE)

@Tag(name = "Family members",description = "Available endpoints for managing family members")
public class FamilyMembersController {
    private final FamilyMemberCommandService familyMemberCommandService;
    private final FamilyMemberQueryService familyMemberQueryService;

    public FamilyMembersController(FamilyMemberCommandService familyMemberCommandService, FamilyMemberQueryService familyMemberQueryService) {
        this.familyMemberCommandService = familyMemberCommandService;
        this.familyMemberQueryService = familyMemberQueryService;
    }

}

