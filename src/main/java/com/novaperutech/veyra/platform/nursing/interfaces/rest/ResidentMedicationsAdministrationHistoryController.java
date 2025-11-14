package com.novaperutech.veyra.platform.nursing.interfaces.rest;

import com.novaperutech.veyra.platform.nursing.domain.model.commands.AddMedicationAdministrationCommand;
import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetMedicationAdministrationsByResidentIdQuery;
import com.novaperutech.veyra.platform.nursing.domain.services.ResidentCommandServices;
import com.novaperutech.veyra.platform.nursing.domain.services.ResidentQueryServices;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.resources.AddMedicationAdministrationResource;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.resources.MedicationAdministrationResource;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.transform.MedicationAdministrationResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/residents/{residentId}/medications-administration",produces = APPLICATION_JSON_VALUE)
@Tag(name = "Medications administration for residents Endpoint",description = "Available medications administration endpoints")
public class ResidentMedicationsAdministrationHistoryController {
    private final ResidentCommandServices residentCommandServices;
   private  final ResidentQueryServices residentQueryServices;

    public ResidentMedicationsAdministrationHistoryController(ResidentCommandServices residentCommandServices, ResidentQueryServices residentQueryServices) {
        this.residentCommandServices = residentCommandServices;
        this.residentQueryServices = residentQueryServices;
    }

    @PostMapping
@Operation(summary = "Add administration medication to a resident ")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "201",description = "created administration medication"),
             @ApiResponse(responseCode = "400",description = "bad request")
     })
    public ResponseEntity<MedicationAdministrationResource>addMedicationAdministrationR(@RequestBody AddMedicationAdministrationResource resource, @PathVariable Long residentId)
{
    var command= new AddMedicationAdministrationCommand(residentId,resource.medicationName(),resource.staffMemberId(),resource.administeredAt(),resource.quantityAdministered(),resource.wasAdministered(),resource.notes());
    residentCommandServices.handle(command);
     var query= new GetMedicationAdministrationsByResidentIdQuery(residentId);
        var medicationAdministrationHistory=residentQueryServices.handle(query);
        if (medicationAdministrationHistory.isEmpty()){return ResponseEntity.notFound().build();}
        var lastMedicationAdministrationHistory= medicationAdministrationHistory.getLast();
        var medicationAdministrationResource= MedicationAdministrationResourceFromEntityAssembler.toResourceFromEntity(lastMedicationAdministrationHistory);
        return new ResponseEntity<>(medicationAdministrationResource, HttpStatus.CREATED);
}
@GetMapping
    @Operation(summary = "Get all medications administrations by resident Id")
 @ApiResponses(value =
         {
                 @ApiResponse(responseCode = "200",description = "medication administrations found"),
                 @ApiResponse(responseCode = "404",description = "medication administrations not found")
         })

    public ResponseEntity<List<MedicationAdministrationResource>> getMedicationsAdministrationByResidentId(@PathVariable Long residentId)
{
    var query= residentQueryServices.handle(new GetMedicationAdministrationsByResidentIdQuery(residentId));
    if (query.isEmpty()){return ResponseEntity.notFound().build();}
    var medicationAdministrationsResource=query.stream().map(MedicationAdministrationResourceFromEntityAssembler::toResourceFromEntity).toList();
    return ResponseEntity.ok(medicationAdministrationsResource);
}
}
