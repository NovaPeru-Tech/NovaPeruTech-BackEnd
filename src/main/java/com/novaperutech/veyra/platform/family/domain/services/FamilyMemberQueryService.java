package com.novaperutech.veyra.platform.family.domain.services;


import com.novaperutech.veyra.platform.family.domain.model.aggregates.FamilyMember;
import com.novaperutech.veyra.platform.family.domain.model.queries.GetFamilyMemberByIdQuery;
import com.novaperutech.veyra.platform.family.domain.model.queries.GetFamilyMemberByUserIdQuery;
import com.novaperutech.veyra.platform.family.domain.model.queries.GetResidentsByUserIdQuery;
import com.novaperutech.veyra.platform.family.domain.model.valueobjects.ResidentId;

import java.util.List;
import java.util.Optional;

public interface FamilyMemberQueryService {
List<ResidentId>handle(GetResidentsByUserIdQuery query);
Optional<FamilyMember>handle(GetFamilyMemberByIdQuery query);
Optional<FamilyMember>handle(GetFamilyMemberByUserIdQuery query);
}
