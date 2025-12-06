package com.novaperutech.veyra.platform.hcm.interfaces.rest;
import com.novaperutech.veyra.platform.hcm.domain.model.queries.GetAllStaffMemberByNursingHomeIdQuery;
import com.novaperutech.veyra.platform.hcm.domain.model.queries.GetStaffByIdQuery;
import com.novaperutech.veyra.platform.hcm.domain.model.valueobjects.NursingHomeId;
import com.novaperutech.veyra.platform.hcm.domain.services.StaffCommandServices;
import com.novaperutech.veyra.platform.hcm.domain.services.StaffQueryServices;
import com.novaperutech.veyra.platform.hcm.interfaces.rest.resources.CreateStaffResource;
import com.novaperutech.veyra.platform.hcm.interfaces.rest.resources.StaffResource;
import com.novaperutech.veyra.platform.hcm.interfaces.rest.transform.CreateStaffCommandFromResourceAssembler;
import com.novaperutech.veyra.platform.hcm.interfaces.rest.transform.StaffResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Nursing Homes")
@RequestMapping(value = "api/v1/nursing-homes/{nursingHomeId}/staff")
public class NursingHomeStaffController {
    private final StaffQueryServices staffQueryServices;
    private final StaffCommandServices staffCommandServices;

    public NursingHomeStaffController(StaffQueryServices staffQueryServices, StaffCommandServices staffCommandServices) {
        this.staffQueryServices = staffQueryServices;
        this.staffCommandServices = staffCommandServices;
    }

    @PostMapping
    @Operation(summary = "Create new Staff member",description = "Create new Staff member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Staff member created"),
            @ApiResponse(responseCode = "400",description = "Bad request")
    })
    public ResponseEntity<StaffResource> createStaff(@RequestBody CreateStaffResource resource, @PathVariable Long nursingHomeId){
        var staffCommand= CreateStaffCommandFromResourceAssembler.toCommandFromResource(resource,nursingHomeId);
        var staffId= staffCommandServices.handle(staffCommand);
        if (staffId==null||staffId==0L){ return ResponseEntity.badRequest().build();}
        var getStaffByIdQuery= new GetStaffByIdQuery(staffId);
        var staff=staffQueryServices.handle(getStaffByIdQuery);
        if (staff.isEmpty()){return ResponseEntity.notFound().build();}
        var staffEntity= staff.get();
        var staffResource= StaffResourceFromEntityAssembler.toResourceFromEntity(staffEntity);
        return new ResponseEntity<>(staffResource, HttpStatus.CREATED);

    }
    @GetMapping
    @Operation(summary = "Get all staff members for a nursing home",
            description = "Get all staff members associated with the specified nursing home")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Staff members retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Nursing home not found or no staff members exist")
    })
    public ResponseEntity<List<StaffResource>> getAllStaff(@PathVariable Long nursingHomeId) {
        var staff = staffQueryServices.handle(
                new GetAllStaffMemberByNursingHomeIdQuery(new NursingHomeId(nursingHomeId)));

        return ResponseEntity.ok(
                staff.stream()
                        .map(StaffResourceFromEntityAssembler::toResourceFromEntity)
                        .toList()
        );
    }
}


