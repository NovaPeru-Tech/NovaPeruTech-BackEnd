package com.novaperutech.veyra.platform.hcm.interfaces.rest;
import com.novaperutech.veyra.platform.hcm.domain.model.queries.GetAllStaffQuery;
import com.novaperutech.veyra.platform.hcm.domain.model.queries.GetStaffByIdQuery;
import com.novaperutech.veyra.platform.hcm.domain.services.StaffCommandServices;
import com.novaperutech.veyra.platform.hcm.domain.services.StaffQueryServices;
import com.novaperutech.veyra.platform.hcm.interfaces.rest.resources.CreateStaffResource;
import com.novaperutech.veyra.platform.hcm.interfaces.rest.resources.StaffResource;
import com.novaperutech.veyra.platform.hcm.interfaces.rest.resources.UpdateStaffResource;
import com.novaperutech.veyra.platform.hcm.interfaces.rest.transform.CreateStaffCommandFromResourceAssembler;
import com.novaperutech.veyra.platform.hcm.interfaces.rest.transform.StaffResourceFromEntityAssembler;
import com.novaperutech.veyra.platform.hcm.interfaces.rest.transform.UpdateStaffCommandFromAssembler;
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
@RequestMapping(value = "/api/v1/staff",produces = APPLICATION_JSON_VALUE)
@Tag(name = "Staff Endpoint", description = "Available endpoints ")
public class StaffController {
private final StaffQueryServices staffQueryServices;
private final StaffCommandServices staffCommandServices;

    public StaffController(StaffQueryServices staffQueryServices, StaffCommandServices staffCommandServices) {
        this.staffQueryServices = staffQueryServices;
        this.staffCommandServices = staffCommandServices;
    }

    @PostMapping
    @Operation(summary = "Create new Staff",description = "Create new Staff")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Staff created"),
            @ApiResponse(responseCode = "400",description = "Bad request")
    })
    public ResponseEntity<StaffResource>createStaff(@RequestBody CreateStaffResource resource){
        var staffCommand= CreateStaffCommandFromResourceAssembler.toCommandFromResource(resource);
        var staffId= staffCommandServices.handle(staffCommand);
        if (staffId==null||staffId==0L){ return ResponseEntity.badRequest().build();}
        var getStaffByIdQuery= new GetStaffByIdQuery(staffId);
        var staff=staffQueryServices.handle(getStaffByIdQuery);
        if (staff.isEmpty()){return ResponseEntity.notFound().build();}
        var staffEntity= staff.get();
        var staffResource= StaffResourceFromEntityAssembler.toResourceFromEntity(staffEntity);
        return new ResponseEntity<>(staffResource, HttpStatus.CREATED);

    }
    @PutMapping("/{staffId}")
    @Operation(summary = " Staff updated by ID",description = "Staff updated by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = " staff update"),
            @ApiResponse(responseCode = "404",description = "staff not found")
    })
    @Parameter(name = "staffId",description = " The unique identifier of the staff",required = true)
    public ResponseEntity<StaffResource> updateStaff(@PathVariable Long staffId,@RequestBody UpdateStaffResource resource){
        var updatestaffCommand= UpdateStaffCommandFromAssembler.toCommandFromResource(staffId,resource);
        var staffUpdate=staffCommandServices.handle(updatestaffCommand);
        if (staffUpdate.isEmpty()){return ResponseEntity.badRequest().build();}
        var staffEntity= staffUpdate.get();
        var staffResource= StaffResourceFromEntityAssembler.toResourceFromEntity(staffEntity);
        return ResponseEntity.ok(staffResource);

    }
    @GetMapping
    @Operation(summary = "Get all staff",description = "Get all staff ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = " staff found"),
            @ApiResponse(responseCode = "404",description = "staff not found")
    })
    public ResponseEntity<List<StaffResource>> getAllStaff(){
        var staff= staffQueryServices.handle(new GetAllStaffQuery());
        if (staff.isEmpty()){return ResponseEntity.notFound().build();}
        var staffResource=staff.stream().map(StaffResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(staffResource);
    }
}
