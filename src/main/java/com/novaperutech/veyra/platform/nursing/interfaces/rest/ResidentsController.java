package com.novaperutech.veyra.platform.nursing.interfaces.rest;

import com.novaperutech.veyra.platform.nursing.domain.model.commands.DeleteResidentCommand;
import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetActiveResidentsByNursingHomeId;
import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetAllResidentsByNursingHomeIdQuery;
import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetResidentByIdQuery;
import com.novaperutech.veyra.platform.nursing.domain.services.ResidentCommandServices;
import com.novaperutech.veyra.platform.nursing.domain.services.ResidentQueryServices;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.resources.CreateResidentResource;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.resources.ResidentResource;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.resources.UpdateResidentResource;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.transform.CreateResidentCommandFromResourceAssembler;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.transform.ResidentResourceFromEntityAssembler;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.transform.UpdateResidentCommandFromResourceAssembler;
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
@RequestMapping(value = "/api/v1/residents", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Residents",description = "Available resident endpoints")
public class ResidentsController {
private final ResidentCommandServices residentCommandServices;
private final ResidentQueryServices residentQueryServices;

    public ResidentsController(ResidentCommandServices residentCommandServices, ResidentQueryServices residentQueryServices) {
        this.residentCommandServices = residentCommandServices;
        this.residentQueryServices = residentQueryServices;
    }
    @PostMapping("/nursingHome/{nursingHomeId}")
    @Operation(summary = "Create a new resident in a nursing home")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Resident created"),
            @ApiResponse(responseCode = "400",description = "Bad request")
    })
    @Parameter(name = "nursingHomeId", description = "The unique identifier of the nursing home", required = true)
    public ResponseEntity<ResidentResource>createResident(@RequestBody CreateResidentResource resource, @PathVariable Long nursingHomeId)
    {
       var residentCommand= CreateResidentCommandFromResourceAssembler.toCommandFromResource(resource,nursingHomeId);
       var residentId= residentCommandServices.handle(residentCommand);
       if (residentId==null||residentId==0L){return ResponseEntity.badRequest().build();}
       var getResidentByIdQuery=new GetResidentByIdQuery(residentId);
       var resident= residentQueryServices.handle(getResidentByIdQuery);
       if (resident.isEmpty()){return ResponseEntity.notFound().build();}
       var residentEntity=resident.get();
       var residentResource= ResidentResourceFromEntityAssembler.toResourceFromEntity(residentEntity);
       return new ResponseEntity<>(residentResource, HttpStatus.CREATED);
    }
    @PutMapping("/{residentId}")
    @Operation(summary = "Update resident by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resident updated"),
            @ApiResponse(responseCode = "404", description = "Resident not found")
    })
    @Parameter(name = "residentId", description = "The unique identifier of the resident", required = true)
    public ResponseEntity<ResidentResource>updateResident(@PathVariable Long residentId, @RequestBody UpdateResidentResource resource)
    {
      var updateResidentCommand= UpdateResidentCommandFromResourceAssembler.toCommandFromResource(residentId,resource);
       var updateResident= residentCommandServices.handle(updateResidentCommand);
       if (updateResident.isEmpty()){return ResponseEntity.notFound().build();}
       var residentEntity=updateResident.get();
       var residentResource= ResidentResourceFromEntityAssembler.toResourceFromEntity(residentEntity);
       return ResponseEntity.ok(residentResource);
    }
    @GetMapping("/{residentId}")
    @Operation(summary = "Get resident by ID")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",description = "resident found "),
            @ApiResponse(responseCode = "404",description = "resident not found")
    })
    @Parameter(name = "residentId", description = " The unique identifier of the resident",required = true)
    public ResponseEntity<ResidentResource>getResidentById(@PathVariable Long residentId){
        var resident= residentQueryServices.handle(new GetResidentByIdQuery(residentId));
        if (resident.isEmpty()){return ResponseEntity.notFound().build();}
        var residentEntity= resident.get();
        var residentResource= ResidentResourceFromEntityAssembler.toResourceFromEntity(residentEntity);
        return ResponseEntity.ok(residentResource);
    }


    @GetMapping("/nursingHome/{nursingHomeId}")
    @Operation(summary = "Get all residents by nursing home")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Residents found"),
            @ApiResponse(responseCode = "404",description = "Residents not found")
    })
    @Parameter(name = "nursingHomeId", description = "The unique identifier of the nursing home", required = true)
    public ResponseEntity<List<ResidentResource>>getAllResidents(@PathVariable Long nursingHomeId){
        var resident= residentQueryServices.handle(new GetAllResidentsByNursingHomeIdQuery(nursingHomeId));
        if (resident.isEmpty()){return ResponseEntity.notFound().build();}
        var residentResource= resident.stream().map(ResidentResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(residentResource);
    }
    @DeleteMapping("/{residentId}")
    @Operation(summary = "Delete resident by ID",description = "Delete resident")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",description = "Resident deleted"),
            @ApiResponse(responseCode = "404",description = "Resident not found")
    })
    @Parameter(name = "residentId", description = "The unique identifier of the resident", required = true)
    public ResponseEntity<?>deleteResident( @PathVariable Long residentId){
     var deleteResidentCommand= new DeleteResidentCommand(residentId);
      residentCommandServices.handle(deleteResidentCommand);
      return ResponseEntity.ok("Resident with given id successfully deleted");
    }
    @GetMapping("/nursingHome/{nursingHomeId}/active")
    @Operation(summary = "Get all active residents by nursing home")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "residents actives found"),
            @ApiResponse(responseCode = "404",description = "residents  actives not found")
    })
    @Parameter(name = "nursingHomeId", description = "The unique identifier of the nursing home", required = true)
    public ResponseEntity<List<ResidentResource>>getAllResidentActives(@PathVariable Long nursingHomeId){
        var resident= residentQueryServices.handle( new GetActiveResidentsByNursingHomeId(nursingHomeId));
        if (resident.isEmpty()){return ResponseEntity.notFound().build();}
        var residentResource=resident.stream().map(ResidentResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(residentResource);
    }
}
