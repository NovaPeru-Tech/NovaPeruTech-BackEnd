package com.novaperutech.veyra.platform.family.interfaces.rest;
import com.novaperutech.veyra.platform.family.domain.services.AccessCodeCommandService;
import com.novaperutech.veyra.platform.family.interfaces.rest.resources.AccessCodeResource;
import com.novaperutech.veyra.platform.family.interfaces.rest.resources.GenerateAccessCodeResource;
import com.novaperutech.veyra.platform.family.interfaces.rest.resources.RedeemAccessCodeResource;
import com.novaperutech.veyra.platform.family.interfaces.rest.transform.GenerateAccessCodeCommandFromResourceAssembler;
import com.novaperutech.veyra.platform.family.interfaces.rest.transform.RedeemAccessCodeCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(value = "/api/v1/access-codes",produces = APPLICATION_JSON_VALUE)
@Tag(name = "Access codes",description = "Available endpoints")
public class AccessCodesController {
private final AccessCodeCommandService accessCodeCommandService;

    public AccessCodesController(AccessCodeCommandService accessCodeCommandService) {
        this.accessCodeCommandService = accessCodeCommandService;
    }
    @PostMapping("/redeem")
    @Operation(summary = "Redeem an access code", description = "Allows a user to redeem an existing access code.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Access code redeemed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid access code")
    })
    public ResponseEntity<Void>redeemAccessCode(@RequestBody RedeemAccessCodeResource resource,
                                                  @AuthenticationPrincipal UserDetails userDetails){
        Long userId=Long.parseLong(userDetails.getUsername());
        var commandResource= RedeemAccessCodeCommandFromResourceAssembler.toCommandFromResource(resource,userId);
         accessCodeCommandService.handle(commandResource);
         return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/generate")
    @Operation(summary = "Generate a new access code", description = "Generates a unique access code for a family member.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Access code generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public ResponseEntity<AccessCodeResource> generateAccessCode(
            @RequestBody GenerateAccessCodeResource resource){
     var command= GenerateAccessCodeCommandFromResourceAssembler.toCommandFromResource(resource);
     accessCodeCommandService.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}