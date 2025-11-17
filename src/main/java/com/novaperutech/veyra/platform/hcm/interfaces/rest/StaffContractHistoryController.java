package com.novaperutech.veyra.platform.hcm.interfaces.rest;
import com.novaperutech.veyra.platform.hcm.domain.model.queries.GetActiveContractByStaffMemberId;
import com.novaperutech.veyra.platform.hcm.domain.model.queries.GetAllContractsByStaffMemberId;
import com.novaperutech.veyra.platform.hcm.domain.model.queries.GetContractByStaffMemberIdAndContractId;
import com.novaperutech.veyra.platform.hcm.domain.services.StaffCommandServices;
import com.novaperutech.veyra.platform.hcm.domain.services.StaffQueryServices;
import com.novaperutech.veyra.platform.hcm.interfaces.rest.resources.AddContractResource;
import com.novaperutech.veyra.platform.hcm.interfaces.rest.resources.ContractResource;
import com.novaperutech.veyra.platform.hcm.interfaces.rest.resources.UpdateContractResource;
import com.novaperutech.veyra.platform.hcm.interfaces.rest.transform.AddContractCommandFromResourceAssembler;
import com.novaperutech.veyra.platform.hcm.interfaces.rest.transform.ContractResourceFromEntityAssembler;
import com.novaperutech.veyra.platform.hcm.interfaces.rest.transform.UpdateContractCommandFromResourceAssembler;
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
@RequestMapping(value = "/api/v1/staff/{staffMemberId}/contract",produces = APPLICATION_JSON_VALUE)
@Tag(name = "Contracts Endpoints",description = "Available endpoints")
public class StaffContractHistoryController {
    private final StaffCommandServices staffCommandServices;
    private final StaffQueryServices staffQueryServices;

    public StaffContractHistoryController(StaffCommandServices staffCommandServices, StaffQueryServices staffQueryServices) {
        this.staffCommandServices = staffCommandServices;
        this.staffQueryServices = staffQueryServices;
    }

    @PostMapping
    @Operation(summary = "Add a contract to the contract")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Add contract "),
            @ApiResponse(responseCode = "404",description = "Bad request")
    })
    public ResponseEntity<ContractResource> addContract(@PathVariable Long staffMemberId, @RequestBody AddContractResource resource) {
        var contractCommand= AddContractCommandFromResourceAssembler.toCommandFromResource(staffMemberId,resource);
        var contractAdd= staffCommandServices.handle(contractCommand);
        if (contractAdd==null){return ResponseEntity.notFound().build();}
        var contractQuery= new GetActiveContractByStaffMemberId(contractAdd);
        var contractEntity= staffQueryServices.handle(contractQuery);
        if (contractEntity.isEmpty()){return ResponseEntity.notFound().build();}
        var contractResource= ContractResourceFromEntityAssembler.ToResourceFromEntity(contractEntity.get());
        return new ResponseEntity<>( contractResource, HttpStatus.CREATED);
    }


    @PatchMapping("/{contractId}")
    @Operation(summary = "Update contract status", description = "Updates only the status of a contract")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contract status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Staff or contract not found")
    })
    public ResponseEntity<ContractResource> updateContractStatus(
            @PathVariable Long staffMemberId,
            @PathVariable Long contractId,
            @RequestBody UpdateContractResource resource) {

        var updateContract = UpdateContractCommandFromResourceAssembler.toCommandFromResource(staffMemberId, contractId, resource);
        staffCommandServices.handle(updateContract);
        var query= new GetContractByStaffMemberIdAndContractId(staffMemberId, contractId);
       var contract= staffQueryServices.handle(query);
       if (contract.isEmpty()){return ResponseEntity.notFound().build();}
       var contractEntity= contract.get();
       var contractResource = ContractResourceFromEntityAssembler.ToResourceFromEntity(contractEntity);
       return ResponseEntity.ok(contractResource);
    }
    @GetMapping
    public ResponseEntity<List<ContractResource>>getAllContractByStaffMemberId(@PathVariable Long staffMemberId)
    {
       var MemberId= new GetAllContractsByStaffMemberId(staffMemberId);
        var getAllContractByStaffMemberId= staffQueryServices.handle(MemberId);
        if (getAllContractByStaffMemberId.isEmpty()){return ResponseEntity.notFound().build();}
        var contractResource= getAllContractByStaffMemberId.stream().map(ContractResourceFromEntityAssembler::ToResourceFromEntity).toList();
        return ResponseEntity.ok(contractResource);
    }

    @GetMapping("/{contractId}")
    @Operation(
            summary = "Get a specific contract of a staff member",
            description = "Retrieves the details of a specific contract belonging to the given staff member. "
                    + "If the contract is not found, a 404 Not Found response is returned."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Contract successfully retrieved"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Staff member or contract not found"
            ),
    })
    public ResponseEntity<ContractResource>getContractByStaffMemberAndContractId(@PathVariable Long staffMemberId,@PathVariable Long contractId){
        var getContractByStaffMemberAndContractIdQuery= new GetContractByStaffMemberIdAndContractId(staffMemberId,contractId);
        var query= staffQueryServices.handle(getContractByStaffMemberAndContractIdQuery);
        if (query.isEmpty()){return ResponseEntity.notFound().build();}
        var contractEntity=query.get();
         var contractResource= ContractResourceFromEntityAssembler.ToResourceFromEntity(contractEntity);
         return ResponseEntity.ok(contractResource);

    }

}
