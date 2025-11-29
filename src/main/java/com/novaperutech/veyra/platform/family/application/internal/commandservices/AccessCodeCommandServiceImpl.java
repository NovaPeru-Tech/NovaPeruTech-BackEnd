package com.novaperutech.veyra.platform.family.application.internal.commandservices;
import com.novaperutech.veyra.platform.family.application.internal.outboundservices.acl.ExternalNursingService;
import com.novaperutech.veyra.platform.family.domain.model.aggregates.AccessCode;
import com.novaperutech.veyra.platform.family.domain.model.commands.GenerateAccessCodeCommand;
import com.novaperutech.veyra.platform.family.domain.model.commands.RedeemAccessCodeCommand;
import com.novaperutech.veyra.platform.family.domain.model.valueobjects.ResidentId;
import com.novaperutech.veyra.platform.family.domain.model.valueobjects.UserId;
import com.novaperutech.veyra.platform.family.domain.services.AccessCodeCommandService;
import com.novaperutech.veyra.platform.family.infrastructure.persistence.jpa.repositories.AccessCodeRepository;
import com.novaperutech.veyra.platform.family.infrastructure.persistence.jpa.repositories.FamilyMemberRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;
@Service
public class AccessCodeCommandServiceImpl implements AccessCodeCommandService {
    private final AccessCodeRepository accessCodeRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final ExternalNursingService externalNursingService;
    public AccessCodeCommandServiceImpl(AccessCodeRepository accessCodeRepository, FamilyMemberRepository familyMemberRepository, ExternalNursingService externalNursingService) {
        this.accessCodeRepository = accessCodeRepository;
        this.familyMemberRepository = familyMemberRepository;
        this.externalNursingService = externalNursingService;
    }
    @Override
    public String handle(GenerateAccessCodeCommand command) {
        var residentExists = externalNursingService.fetchResidentById(command.residentId());
        if (residentExists.isEmpty()) {
            throw new IllegalArgumentException("Resident with id " + command.residentId() + " does not exist");
        }
         String code=generateUniqueCode();
        var residentId = new ResidentId(command.residentId());
        var accessCode = new AccessCode(
                code,
                residentId,
                command.familyEmail(),
                command.validityDays()
        );
        accessCodeRepository.save(accessCode);
      return code;
    }
    @Override
    public void handle(RedeemAccessCodeCommand command) {
      var accessCode=accessCodeRepository.findByCode(command.code()).orElseThrow(()-> new IllegalArgumentException("Invalid access code"));
     if (accessCode.isValid()){
      if (accessCode.isExpired()) {
          throw new IllegalArgumentException("Access code has expired");
      }
      throw new IllegalArgumentException("Access code has already been used");
      }
     var userId= new UserId(command.userId());
     var familyMember=familyMemberRepository.findByUserId(userId)
             .orElseThrow(()->new IllegalArgumentException("No family member profile found for user id"));
       if (familyMember.hasResident(accessCode.getResidentId())){throw new IllegalArgumentException("Resident is already linked to this family member");}
       familyMember.addResident(accessCode.getResidentId());
       familyMemberRepository.save(familyMember);
       accessCode.markAsUsed();
       accessCodeRepository.save(accessCode);

    }
    private String generateUniqueCode() {
        String code;
        do {
            code = UUID.randomUUID()
                    .toString()
                    .substring(0, 8)
                    .toUpperCase()
                    .replaceAll("(.{3})(.{3})(.{2})", "$1-$2-$3");
        } while (accessCodeRepository.findByCode(code).isPresent());
        return code;
    }
}