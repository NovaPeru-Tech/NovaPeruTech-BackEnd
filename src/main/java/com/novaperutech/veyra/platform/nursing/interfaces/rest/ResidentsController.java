package com.novaperutech.veyra.platform.nursing.interfaces.rest;

import com.novaperutech.veyra.platform.nursing.domain.model.commands.DeleteResidentCommand;
import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetAllResidentsQuery;
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
@RequestMapping(value = "/api/v1/nursing", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Residents",description = "Available ")
public class ResidentsController {
private final ResidentCommandServices residentCommandServices;
private final ResidentQueryServices residentQueryServices;

    public ResidentsController(ResidentCommandServices residentCommandServices, ResidentQueryServices residentQueryServices) {
        this.residentCommandServices = residentCommandServices;
        this.residentQueryServices = residentQueryServices;
    }
    @PostMapping
    @Operation(summary = "Create a new Resident")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Resident created"),
            @ApiResponse(responseCode = "400",description = "Bad request")
    })
    public ResponseEntity<ResidentResource>createResident(@RequestBody CreateResidentResource resource)
    {
       var residentCommand= CreateResidentCommandFromResourceAssembler.toCommandFromResource(resource);
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
    @Operation(summary = "Updated  for resident by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "update resident"),
            @ApiResponse(responseCode = "400",description = "resident not found ")
    })
    @Parameter(name = "residentId",description = " The unique identifier of the resident",required = true)
    public ResponseEntity<ResidentResource>updateResident(@PathVariable Long residentId, @RequestBody UpdateResidentResource resource)
    {
      var updateResidentCommand= UpdateResidentCommandFromResourceAssembler.toCommandFromResource(residentId, resource);
       var updateResident= residentCommandServices.handle(updateResidentCommand);
       if (updateResident.isEmpty()){return ResponseEntity.notFound().build();}
       var residentEntity=updateResident.get();
       var residentResource= ResidentResourceFromEntityAssembler.toResourceFromEntity(residentEntity);
       return ResponseEntity.ok(residentResource);
    }
    @GetMapping("/{residentId}")
    @Operation(summary = "Get resident by id")
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


    @GetMapping
    @Operation(summary = "Get all residents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "residents found"),
            @ApiResponse(responseCode = "404",description = "residents not found")
    })
    public ResponseEntity<List<ResidentResource>>getAllResidents(){
        var resident= residentQueryServices.handle(new GetAllResidentsQuery());
        if (resident.isEmpty()){return ResponseEntity.notFound().build();}
        var residentResource= resident.stream().map(ResidentResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(residentResource);
    }
    @DeleteMapping("/{residentId}")
    @Operation(summary = "Delete resident by id")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",description = "resident found "),
            @ApiResponse(responseCode = "404",description = "resident not found")
    })
    @Parameter(name = "residentId", description = " The unique identifier of the resident",required = true)
    public ResponseEntity<?>deleteResident( @PathVariable Long residentId){
     var deleteResidentCommand= new DeleteResidentCommand(residentId);
      residentCommandServices.handle(deleteResidentCommand);
      return ResponseEntity.ok("Resident with give id successfully deleted");
    }
}
