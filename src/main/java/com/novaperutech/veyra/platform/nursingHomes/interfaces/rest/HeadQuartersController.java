package com.novaperutech.veyra.platform.nursingHomes.interfaces.rest;

import com.novaperutech.veyra.platform.nursingHomes.domain.model.queries.GetAllHeadQuarterByNursingHomeIdQuery;
import com.novaperutech.veyra.platform.nursingHomes.domain.model.queries.GetHeadQuarterByIdQuery;
import com.novaperutech.veyra.platform.nursingHomes.domain.services.HeadQuarterCommandService;
import com.novaperutech.veyra.platform.nursingHomes.domain.services.HeadQuarterQueryService;
import com.novaperutech.veyra.platform.nursingHomes.interfaces.rest.resources.CreateHeadQuarterResource;
import com.novaperutech.veyra.platform.nursingHomes.interfaces.rest.resources.HeadQuarterResource;
import com.novaperutech.veyra.platform.nursingHomes.interfaces.rest.tranform.CreateHeadQuarterCommandFromResourceAssembler;
import com.novaperutech.veyra.platform.nursingHomes.interfaces.rest.tranform.HeadQuarterResourceFromEntityAssembler;
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
/**
 * REST controller for managing headquarter resources.
 * <p>
 * This controller provides RESTful endpoints for performing operations on headquarters
 * within the nursing homes domain. It follows REST architectural principles and uses
 * the CQRS pattern by delegating command and query operations to separate services.
 * All endpoints produce JSON responses as specified by the APPLICATION_JSON_VALUE media type.
 * </p>
 *
 * @author NovaPeru Tech
 * @version 1.0
 * @see HeadQuarterCommandService
 * @see HeadQuarterQueryService
 * @see HeadQuarterResource
 */
@RestController
@RequestMapping(value = "api/v1/headquarters",produces =APPLICATION_JSON_VALUE)
@Tag(name = "HeadQuarters", description = "Available HeadQuarters Endpoints")
public class HeadQuartersController {

    /**
     * Service for handling headquarter command operations (write operations).
     */
 private final HeadQuarterCommandService headQuarterCommandService;
    /**
     * Service for handling headquarter query operations (read operations).
     */
    private final HeadQuarterQueryService headQuarterQueryService;

    /**
     * Constructs a new HeadQuartersController with the required services.
     *
     * @param headQuarterCommandService the service for processing headquarter commands,
     *                                  injected by Spring's dependency injection
     * @param headQuarterQueryService   the service for processing headquarter queries,
     *                                  injected by Spring's dependency injection
     */
    public HeadQuartersController(HeadQuarterCommandService headQuarterCommandService, HeadQuarterQueryService headQuarterQueryService) {
        this.headQuarterCommandService = headQuarterCommandService;
        this.headQuarterQueryService = headQuarterQueryService;
    }

    /**
     * Creates a new headquarter for a specific nursing home.
     * <p>
     * This endpoint accepts a POST request with headquarter details and creates a new
     * headquarter entity associated with the specified nursing home. The operation follows
     * these steps:
     * <ol>
     *   <li>Transforms the incoming resource into a command</li>
     *   <li>Processes the command to create the headquarter</li>
     *   <li>Retrieves the newly created headquarter</li>
     *   <li>Transforms the entity into a resource representation</li>
     *   <li>Returns the created resource with HTTP 201 status</li>
     * </ol>
     * </p>
     *
     * @param nursingHomeId the unique identifier of the nursing home to which the
     *                      headquarter will belong, extracted from the URL path
     * @param resource      the request body containing the headquarter creation data
     *                      (title and address)
     * @return a {@link ResponseEntity} containing the created {@link HeadQuarterResource}
     *         with HTTP 201 (CREATED) status if successful, or HTTP 404 (NOT FOUND)
     *         if the creation fails or the created headquarter cannot be retrieved
     */
    @PostMapping("/{nursingHomeId}")
    @Operation( summary = "Create a new headquarter for a nursing home",description = "Create a new headquarter for a nursing home")
    @ApiResponses(value = {
       @ApiResponse(responseCode = "200", description = " nursing home HeadQuarter created"),
       @ApiResponse(responseCode = "404",description = "bad request ")
    })
    @Parameter(name = "nursingHomeId", description = "The nursing home identifier", required = true)
    public ResponseEntity<HeadQuarterResource> createdHeadQuarter(@PathVariable Long nursingHomeId, @RequestBody CreateHeadQuarterResource resource){
    var headQuarterCommand= CreateHeadQuarterCommandFromResourceAssembler.toCommandFromResource(nursingHomeId,resource);
    var headQuarterId=headQuarterCommandService.handle(headQuarterCommand);
    if (headQuarterId==null){
        return ResponseEntity.notFound().build();
    }
    var getHeadQuarterByIdQuery=new GetHeadQuarterByIdQuery(headQuarterId);
    var headQuarter= headQuarterQueryService.handle(getHeadQuarterByIdQuery);
    if (headQuarter.isEmpty()){
        return ResponseEntity.notFound().build();
    }
    var headQuarterEntity=headQuarter.get();
    var headQuarterResource= HeadQuarterResourceFromEntityAssembler.toResourceFromEntity(headQuarterEntity);
    return new ResponseEntity<>(headQuarterResource, HttpStatus.CREATED);

    }


    /**
     * Retrieves all headquarters associated with a specific nursing home.
     * <p>
     * This endpoint accepts a GET request and returns a list of all headquarters
     * that belong to the specified nursing home. The operation follows these steps:
     * <ol>
     *   <li>Creates a query with the nursing home ID</li>
     *   <li>Retrieves all headquarters for that nursing home</li>
     *   <li>Transforms the entities into resource representations</li>
     *   <li>Returns the list with HTTP 200 status if found, or HTTP 404 if no headquarters exist</li>
     * </ol>
     * </p>
     *
     * @param nursingHomeId the unique identifier of the nursing home whose headquarters
     *                      are to be retrieved, extracted from the URL path
     * @return a {@link ResponseEntity} containing a list of {@link HeadQuarterResource}
     *         with HTTP 200 (OK) status if headquarters are found, or HTTP 404 (NOT FOUND)
     *         if no headquarters exist for the specified nursing home
     */

    @GetMapping("/{nursingHomeId}")
    @Operation(summary = "Get headquarters by nursing home id", description = "Get headquearters by nursing home id")
           @ApiResponses(value = {
                   @ApiResponse(responseCode = "200", description = "HeadQuarters found"),
                  @ApiResponse(responseCode = "404",description = "HeadQuarters not found ")
           })

    @Parameter(name = "nursingHomeId", description = "The nursing home identifier", required = true)
    public ResponseEntity<List<HeadQuarterResource>> GetAllHeadQuartersByNursingHomeId(@PathVariable Long nursingHomeId){
        var getAllHeadQuarterByNursingHomeId= new GetAllHeadQuarterByNursingHomeIdQuery(nursingHomeId);
        var headquarters=headQuarterQueryService.handle(getAllHeadQuarterByNursingHomeId);
        if (headquarters.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var headQuarterResource=headquarters.stream().map(HeadQuarterResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(headQuarterResource);
    }
}
