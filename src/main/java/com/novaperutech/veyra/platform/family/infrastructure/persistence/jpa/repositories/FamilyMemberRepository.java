package com.novaperutech.veyra.platform.family.infrastructure.persistence.jpa.repositories;

import com.novaperutech.veyra.platform.family.domain.model.aggregates.FamilyMember;
import com.novaperutech.veyra.platform.family.domain.model.valueobjects.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;



@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember,Long>
{
    Optional<FamilyMember> findByUserId(UserId userId);

}

