package com.novaperutech.veyra.platform.nursing.interfaces.rest;

import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetAllNursingHomeQuery;
import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetNursingHomeByBusinessProfileIdQuery;
import com.novaperutech.veyra.platform.nursing.domain.model.valueobjetcs.BusinessProfileId;
import com.novaperutech.veyra.platform.nursing.domain.services.NursingHomeCommandServices;
import com.novaperutech.veyra.platform.nursing.domain.services.NursingHomeQueryServices;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.resources.CreateNursingHomeResource;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.resources.NursingHomeResource;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.transform.CreateNursingHomeCommandFromResourceAssembler;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.transform.NursingHomeFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/nursing-home",produces = APPLICATION_JSON_VALUE)
@Tag(name = "Nursing homes",description = "Available nursing home Endpoints")
public class NursingHomesController {
    private final NursingHomeCommandServices nursingHomeCommandServices;
    private final NursingHomeQueryServices nursingHomeQueryServices;

    public NursingHomesController(NursingHomeCommandServices nursingHomeCommandServices, NursingHomeQueryServices nursingHomeQueryServices) {
        this.nursingHomeCommandServices = nursingHomeCommandServices;
        this.nursingHomeQueryServices = nursingHomeQueryServices;
    }

    @PostMapping
    @Operation(
            summary = "Create a new nursing home",
            description = "Creates a new nursing home with the provided business profile information and returns the created nursing home resource"
    )    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Nursing home create"),
            @ApiResponse(responseCode = "400",description = "Bad request")
    })
    public ResponseEntity<NursingHomeResource>createNursingHome(@RequestBody CreateNursingHomeResource resource){
        var createNursingHomeCommand= CreateNursingHomeCommandFromResourceAssembler.toCommandFromResource(resource);
        var nursingHomeId=nursingHomeCommandServices.handle(createNursingHomeCommand);
        if (nursingHomeId==null||nursingHomeId==0L){return ResponseEntity.badRequest().build();}
        var aux= new BusinessProfileId(nursingHomeId);
        var getNursingHomeByBusinessProfileId= new GetNursingHomeByBusinessProfileIdQuery(aux);
       var nursingHome= nursingHomeQueryServices.handle(getNursingHomeByBusinessProfileId);
       if (nursingHome.isEmpty()){return ResponseEntity.notFound().build();}
       var nursingHomeEntity= nursingHome.get();
       var nursingHomeResource= NursingHomeFromEntityAssembler.toEntityFromResource(nursingHomeEntity);
       return new ResponseEntity<>(nursingHomeResource, HttpStatus.CREATED);
    }
    @GetMapping("/{businessProfileId}")
    @Operation(
            summary = "Get Nursing Home by Business Profile ID",
            description = "Retrieves the Nursing Home associated with the given Business Profile ID. Returns 404 if no Nursing Home is found for the provided ID."
    )
    @Parameter(
            name = "businessProfileId",
            description = "The unique identifier of the Business Profile for which to fetch the associated Nursing Home",
            required = true
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the Nursing Home details"),
            @ApiResponse(responseCode = "404", description = "Nursing home not found"),
    })
    public ResponseEntity<NursingHomeResource>getNursingHomeByBusinessProfileId(@PathVariable Long businessProfileId)
    {
        var aux= new BusinessProfileId(businessProfileId);
        var businessProfileQuery= nursingHomeQueryServices.handle(new GetNursingHomeByBusinessProfileIdQuery(aux));
        if(businessProfileQuery.isEmpty()){return ResponseEntity.notFound().build();}
        var businessResource= NursingHomeFromEntityAssembler.toEntityFromResource(businessProfileQuery.get());
        return ResponseEntity.ok(businessResource);
    }
     @GetMapping
     @Operation(summary = "",description = "")
     @ApiResponses(value =
             {@ApiResponse()
             ,@ApiResponse()})
    public ResponseEntity<List<NursingHomeResource>>getAllNursingHomes()
     {
         var nursingHome= nursingHomeQueryServices.handle(new GetAllNursingHomeQuery());
         if (nursingHome.isEmpty()){return ResponseEntity.notFound().build();}
         var nursingHomeResource=nursingHome.stream().map(NursingHomeFromEntityAssembler::toEntityFromResource).toList();
         return ResponseEntity.ok(nursingHomeResource);
     }


}
