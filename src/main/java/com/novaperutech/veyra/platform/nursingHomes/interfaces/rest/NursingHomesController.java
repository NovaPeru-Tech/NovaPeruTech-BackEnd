package com.novaperutech.veyra.platform.nursingHomes.interfaces.rest;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.aggregates.NursingHome;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.queries.GetAllNursingHomeQuery;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.queries.GetNursingHomeByIdQuery;
import com.novaperutech.veyra.platform.nursingHomes.domain.services.NursingHomeCommandService;
import com.novaperutech.veyra.platform.nursingHomes.domain.services.NursingHomeQueryService;
import com.novaperutech.veyra.platform.nursingHomes.interfaces.rest.resources.CreateNursingHomeResource;
import com.novaperutech.veyra.platform.nursingHomes.interfaces.rest.resources.NursingHomeResource;
import com.novaperutech.veyra.platform.nursingHomes.interfaces.rest.tranform.CreateNursingHomeCommandFromResourceAssembler;
import com.novaperutech.veyra.platform.nursingHomes.interfaces.rest.tranform.NursingHomeResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for managing nursing home operations.
 * This controller provides endpoints for creating and retrieving nursing home information.
 * It exposes RESTFul APIs that follow standard HTTP conventions and return JSON responses.
 *
 * @author NovaPeru Tech
 * @version 1.0
 * @see NursingHomeCommandService
 * @see NursingHomeQueryService
 */
@RestController
@RequestMapping(value ="/api/v1/nursing-homes",produces = APPLICATION_JSON_VALUE)
@Tag(name = "Nursing Homes",description = "Endpoints for managing nursing home")
public class NursingHomesController {
    private final NursingHomeCommandService nursingHomeCommandService;
    private final NursingHomeQueryService nursingHomeQueryService;
    /**
     * Constructs a new NursingHomesController with the required services.
     *
     * @param nursingHomeCommandService the service to handle command operations
     * @param nursingHomeQueryService the service to handle query operations
     */
    public NursingHomesController(NursingHomeCommandService nursingHomeCommandService, NursingHomeQueryService nursingHomeQueryService) {
        this.nursingHomeCommandService = nursingHomeCommandService;
        this.nursingHomeQueryService = nursingHomeQueryService;
    }
    /**
     * Creates a new nursing home.
     * This endpoint receives nursing home data, validates it, and persists it to the database.
     * It returns the created nursing home resource with HTTP status 201 if successful,
     * or HTTP status 400 if the input data is invalid or the operation fails.
     *
     * @param resource the {@link CreateNursingHomeResource} containing the nursing home data
     * @return a {@link ResponseEntity} containing the created {@link NursingHomeResource} and HTTP status 201,
     *         or HTTP status 400 if the creation fails
     */
    @PostMapping
    @Operation(summary = "Create a new Nursing Home",description = "Create a new Nursing Home")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Nursing home created successfully"),
            @ApiResponse(responseCode = "400",description = "Invalid input data")
    })
    public ResponseEntity<NursingHomeResource> createNursingHome(@RequestBody CreateNursingHomeResource resource){
        Optional<NursingHome>nursingHomeResource=nursingHomeCommandService.handle(CreateNursingHomeCommandFromResourceAssembler.toCommandFromResource(resource));
    return nursingHomeResource
            .map(home->new ResponseEntity<>(NursingHomeResourceFromEntityAssembler.toCommandFromEntity(home),CREATED))
            .orElseGet(()->ResponseEntity.badRequest().build());
    }
    /**
     * Retrieves all nursing homes.
     * This endpoint fetches all nursing home entities from the database and converts them
     * to resource representations. It returns HTTP status 200 with the list of nursing homes
     * if found, or HTTP status 404 if no nursing homes exist.
     *
     * @return a {@link ResponseEntity} containing a {@link List} of {@link NursingHomeResource} and HTTP status 200,
     *         or HTTP status 404 if no nursing homes are found
     */
    @GetMapping
    @Operation(summary = "Get all nursing homes", description = "Retrieve all nursing homes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Nursing homes found"),
            @ApiResponse(responseCode = "404",description = "Nursing homes not found")
    })
    public ResponseEntity<List<NursingHomeResource>>getAllNursingHome(){
        var nursingHomes=nursingHomeQueryService.handle(new GetAllNursingHomeQuery());
        if (nursingHomes.isEmpty()){return ResponseEntity.notFound().build();}
         var nursingHomeResource=nursingHomes.stream()
                 .map(NursingHomeResourceFromEntityAssembler::toCommandFromEntity)
                 .toList();
        return ResponseEntity.ok(nursingHomeResource);
    }
    /**
     * Retrieves a nursing home by its unique identifier.
     * This endpoint fetches a specific nursing home using the provided ID.
     * It returns HTTP status 200 with the nursing home resource if found,
     * or HTTP status 404 if the nursing home does not exist.
     *
     * @param nursingHomeId the unique identifier of the nursing home
     * @return a {@link ResponseEntity} containing the {@link NursingHomeResource} and HTTP status 200,
     *         or HTTP status 404 if the nursing home is not found
     */
    @GetMapping("/{nursingHomeId}")
    @Operation(summary = "Get a nursing home by ID",description = "Retrieve a nursing home by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Nursing home found"),
            @ApiResponse(responseCode = "400",description = "Invalid query parameters"),
            @ApiResponse(responseCode = "404",description = "Nursing home not found")
    })
    @Parameter(name = "nursingHomeId",description = "The unique identifier of the nursing home",required = true)
    public ResponseEntity<NursingHomeResource>getNursingHomeById(@PathVariable Long nursingHomeId){
        var getNursingHomeIdQuery= new GetNursingHomeByIdQuery(nursingHomeId);
        var nursingHome= nursingHomeQueryService.handle(getNursingHomeIdQuery);
        if(nursingHome.isEmpty()){return ResponseEntity.notFound().build();}
        var nursingHomeEntity=nursingHome.get();
        var nursingHomeResource=NursingHomeResourceFromEntityAssembler.toCommandFromEntity(nursingHomeEntity);
        return ResponseEntity.ok(nursingHomeResource);
    }

}
