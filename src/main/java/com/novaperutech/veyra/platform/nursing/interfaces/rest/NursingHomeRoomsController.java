package com.novaperutech.veyra.platform.nursing.interfaces.rest;

import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetLastAddedRoomByNursingHomeIdQuery;
import com.novaperutech.veyra.platform.nursing.domain.model.queries.GetRoomsForNursingHomeIdQuery;
import com.novaperutech.veyra.platform.nursing.domain.services.NursingHomeCommandServices;
import com.novaperutech.veyra.platform.nursing.domain.services.NursingHomeQueryServices;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.resources.CreateRoomResource;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.resources.RoomResource;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.transform.CreateRoomCommandFromResourceAssembler;
import com.novaperutech.veyra.platform.nursing.interfaces.rest.transform.RoomResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@CrossOrigin(origins = {"http://localhost:4200"})@Tag(name = "Nursing Homes")
@RestController
@RequestMapping(value = "/api/v1/nursing-homes/{nursingHomeId}/rooms", produces = APPLICATION_JSON_VALUE)
public class NursingHomeRoomsController {

    private final NursingHomeCommandServices nursingHomeCommandServices;
    private final NursingHomeQueryServices nursingHomeQueryServices;

    public NursingHomeRoomsController(NursingHomeCommandServices nursingHomeCommandServices,
                                      NursingHomeQueryServices nursingHomeQueryServices) {
        this.nursingHomeCommandServices = nursingHomeCommandServices;
        this.nursingHomeQueryServices = nursingHomeQueryServices;
    }
    @PostMapping
    @Operation(summary = "Add a room to nursing home", description = "Create a new room for the specified nursing home")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Room created for nursing home"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    public ResponseEntity<RoomResource> addRoom(@PathVariable Long nursingHomeId,
                                                @RequestBody CreateRoomResource resource) {
        var roomCommand = CreateRoomCommandFromResourceAssembler.toCommandFromResource(nursingHomeId, resource);
        nursingHomeCommandServices.handle(roomCommand);

        var getLastRoomQuery = new GetLastAddedRoomByNursingHomeIdQuery(nursingHomeId);
        var room = nursingHomeQueryServices.handle(getLastRoomQuery);

        if (room.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var roomResource = RoomResourceFromEntityAssembler.toResourceFromEntity(room.get());
        return new ResponseEntity<>(roomResource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all rooms", description = "Get all rooms for the specified nursing home")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rooms retrieved successfully")
    })
    public ResponseEntity<List<RoomResource>> getAllRooms(@PathVariable Long nursingHomeId) {
        var getRoomsQuery = new GetRoomsForNursingHomeIdQuery(nursingHomeId);
        var rooms = nursingHomeQueryServices.handle(getRoomsQuery);

        var roomResources = rooms.stream()
                .map(RoomResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(roomResources);
    }
}