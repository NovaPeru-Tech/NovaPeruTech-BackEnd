package com.novaperutech.veyra.platform.residents.interfaces.rest;

import com.novaperutech.veyra.platform.residents.domain.model.queries.GetAllResidentsQuery;
import com.novaperutech.veyra.platform.residents.domain.model.queries.GetResidentByIdQuery;
import com.novaperutech.veyra.platform.residents.domain.services.ResidentCommandService;
import com.novaperutech.veyra.platform.residents.domain.services.ResidentQueryService;
import com.novaperutech.veyra.platform.residents.interfaces.rest.resources.*;
import com.novaperutech.veyra.platform.residents.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/residents",produces = APPLICATION_JSON_VALUE)
@Tag(name = "residents", description = "Endpoints for managing residents")
public class ResidentController {

    private final ResidentCommandService residentCommandService;
    private final ResidentQueryService residentQueryService;

    public ResidentController(ResidentCommandService residentCommandService,
                              ResidentQueryService residentQueryService) {
        this.residentCommandService = residentCommandService;
        this.residentQueryService = residentQueryService;
    }

    @GetMapping
    public ResponseEntity<List<ResidentResource>>getAllResidents(){
        var resident= residentQueryService.handle(new GetAllResidentsQuery());
        if(resident.isEmpty()){return ResponseEntity.notFound().build();}
        var residentResource=resident.stream().map(ResidentResourceFromEntityAssembler::toResource).toList();
        return ResponseEntity.ok(residentResource);
    }


    @PostMapping
    @Operation(summary = "Create a new Resident",description = "Create a new Resident")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Resident created successfully"),
            @ApiResponse(responseCode = "400",description = "Invalid input data")
    })
    public ResponseEntity<ResidentResource> createResident( @RequestBody CreateResidentResource resource) {
        var residentCommand = CreateResidentCommandFromResourceAssembler.toCommandFromResource(resource);
        var residentId= residentCommandService.handle(residentCommand);
        if (residentId==null){return ResponseEntity.badRequest().build();}
        var getResidentByIdQuery=new GetResidentByIdQuery(residentId);
        var resident= residentQueryService.handle(getResidentByIdQuery);
        if (resident.isEmpty()){return ResponseEntity.notFound().build();}
        var residentEntity= resident.get();
        var residentResource=ResidentResourceFromEntityAssembler.toResource(residentEntity);
        return new ResponseEntity<>(residentResource, HttpStatus.CREATED);

    }

}
