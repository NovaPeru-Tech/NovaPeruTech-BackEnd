package com.novaperutech.veyra.platform.nursing.interfaces.rest;

import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetAllNursingHomeQuery;
import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetNursingHomeByIdQuery;
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
@RequestMapping(value = "/api/v1/nursing-homes",produces = APPLICATION_JSON_VALUE)
@Tag(name = "Nursing Homes",description = "Available nursing home Endpoints")
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
        var getNursingHomeByIdQuery= new GetNursingHomeByIdQuery(nursingHomeId);
       var nursingHome= nursingHomeQueryServices.handle(getNursingHomeByIdQuery);
       if (nursingHome.isEmpty()){return ResponseEntity.notFound().build();}
       var nursingHomeEntity= nursingHome.get();
       var nursingHomeResource= NursingHomeFromEntityAssembler.toResourceFromEntity(nursingHomeEntity);
       return new ResponseEntity<>(nursingHomeResource, HttpStatus.CREATED);
    }
    @GetMapping("/{nursingHomeId}")
    @Operation(
            summary = "Get Nursing Home by ID",
            description = "Retrieves the Nursing Home by its unique identifier. Returns 404 if not found."    )
    @Parameter(
            name = "nursingHomeId",
            description = "The unique identifier for which to fetch the associated Nursing Home",
            required = true
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the Nursing Home details"),
            @ApiResponse(responseCode = "404", description = "Nursing home not found"),
    })
    public ResponseEntity<NursingHomeResource>getNursingHomeById(@PathVariable Long nursingHomeId)
    {
        var getNursingHomeByIdQuery= new GetNursingHomeByIdQuery(nursingHomeId);
        var nursingHome= nursingHomeQueryServices.handle(getNursingHomeByIdQuery);
        if (nursingHome.isEmpty()){return ResponseEntity.notFound().build();}
        var nursingHomeEntity= nursingHome.get();
        var nursingHomeResource= NursingHomeFromEntityAssembler.toResourceFromEntity(nursingHomeEntity);
        return ResponseEntity.ok(nursingHomeResource);
    }
     @GetMapping
     @Operation(
             summary = "Get all nursing homes",
             description = "Retrieves a list of all registered nursing homes"
     )
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200", description = "Successfully retrieved all nursing homes"),
             @ApiResponse(responseCode = "404", description = "No nursing homes found")
     })
    public ResponseEntity<List<NursingHomeResource>>getAllNursingHomes()
     {
         var nursingHome= nursingHomeQueryServices.handle(new GetAllNursingHomeQuery());
         if (nursingHome.isEmpty()){return ResponseEntity.notFound().build();}
         var nursingHomeResource=nursingHome.stream().map(NursingHomeFromEntityAssembler::toResourceFromEntity).toList();
         return ResponseEntity.ok(nursingHomeResource);
     }


}
