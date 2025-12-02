package com.novaperutech.veyra.platform.nursing.application.internal.commandservices;
import com.novaperutech.veyra.platform.nursing.application.internal.outboundservices.acl.ExternalIamService;
import com.novaperutech.veyra.platform.nursing.domain.model.aggregates.Familiar;
import com.novaperutech.veyra.platform.nursing.domain.model.commands.CreateFamiliarCommand;
import com.novaperutech.veyra.platform.nursing.domain.services.FamiliarCommandService;
import com.novaperutech.veyra.platform.nursing.infrastructure.persistence.jpa.repositories.FamiliarRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class FamiliarCommandServiceImpl implements FamiliarCommandService  {
    private final FamiliarRepository familiarRepository;
 private  final ExternalIamService externalIamService;
    public FamiliarCommandServiceImpl(FamiliarRepository familiarRepository, ExternalIamService externalIamService) {
        this.familiarRepository = familiarRepository;
        this.externalIamService = externalIamService;
    }
    @Override
    public Long handle(CreateFamiliarCommand command) {
        var userId = externalIamService.createUser(
                command.username(),
                command.password(),
                List.of("ROLE_FAMILIAR")
        );
        if (familiarRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException("Familiar already exists for this user");
        }
        var familiar = new Familiar(userId);
        familiarRepository.save(familiar);
        return familiar.getId();
    }
}