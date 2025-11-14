package com.novaperutech.veyra.platform.nursing.interfaces.rest;

import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetAllMedicationsByResidentIdQuery;
import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetMedicationByIdQuery;
import com.novaperutech.veyra.platform.nursing.domain.services.MedicationCommandServices;
import com.novaperutech.veyra.platform.nursing.domain.services.MedicationQueryServices;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.resources.CreateMedicationResource;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.resources.MedicationResource;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.transform.CreateMedicationCommandFromResourceAssembler;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.transform.MedicationResourceFromEntityAssembler;
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
@RequestMapping(value = "/api/v1/medications",produces = APPLICATION_JSON_VALUE)
@Tag(name = "Medications", description = "Available medication endpoints")
public class MedicationsController {
    private final MedicationCommandServices medicationCommandServices;
    private final MedicationQueryServices medicationQueryServices;

    public MedicationsController(MedicationCommandServices medicationCommandServices, MedicationQueryServices medicationQueryServices) {
        this.medicationCommandServices = medicationCommandServices;
        this.medicationQueryServices = medicationQueryServices;
    }

    @PostMapping("resident/{residentId}")
    @Operation(summary = "Create a new medication for resident",description = "Create a new medication for resident")
    @ApiResponses(value =
            {
                    @ApiResponse(responseCode = "201",description = " Medication created"),
                    @ApiResponse(responseCode = "400",description = "Bad request")
            })
    @Parameter(name = "residentId",description = "The unique identifier of the resident", required = true)
    public ResponseEntity<MedicationResource>createMedicationForResident(@PathVariable Long residentId, @RequestBody CreateMedicationResource resource)
    {
        var medicationCommandAssembler= CreateMedicationCommandFromResourceAssembler.toCommandFromResource(resource,residentId);
        var medicationCommand=medicationCommandServices.handle(medicationCommandAssembler);
        if (medicationCommand==null||medicationCommand==0L){return ResponseEntity.badRequest().build();}
        var medicationId= new GetMedicationByIdQuery(medicationCommand);
        var medicationFindByIdQuery= medicationQueryServices.handle(medicationId);
        if (medicationFindByIdQuery.isEmpty()){return ResponseEntity.notFound().build();}
        var medicationResource= MedicationResourceFromEntityAssembler.toResourceFromEntity(medicationFindByIdQuery.get());
         return new ResponseEntity<>(medicationResource, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get medication by ID",description = "Get medication by ID")
    @ApiResponses(value={
       @ApiResponse(responseCode = "200",description = "Medication found"),
       @ApiResponse(responseCode = "404",description = "Medication not found")
    })
    @Parameter(name = "id", description = " The unique identifier of the medication",required = true)
    public ResponseEntity<MedicationResource>getMedicationById(@PathVariable Long id)
    {
        var medicationQuery=medicationQueryServices.handle(new GetMedicationByIdQuery(id));
        if (medicationQuery.isEmpty()){return ResponseEntity.notFound().build();}
        var medicationEntity= medicationQuery.get();
        var medicationResource=MedicationResourceFromEntityAssembler.toResourceFromEntity(medicationEntity);
        return ResponseEntity.ok(medicationResource);
    }
@GetMapping("resident/{residentId}")
    @Operation(summary = "Get all medications by resident id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Medications found"),
            @ApiResponse(responseCode = "404",description = "Medications not found")
    })
@Parameter(name = "residentId", description = " The unique identifier of the resident",required = true)
    public ResponseEntity<List<MedicationResource>> getMedicationsByResidentId(@PathVariable Long residentId)
{
    var medicationQuery= medicationQueryServices.handle(new GetAllMedicationsByResidentIdQuery(residentId));
    if (medicationQuery.isEmpty()){return ResponseEntity.notFound().build();}
    var medicationResource= medicationQuery.stream().map(MedicationResourceFromEntityAssembler::toResourceFromEntity).toList();
    return ResponseEntity.ok(medicationResource);
}
}
