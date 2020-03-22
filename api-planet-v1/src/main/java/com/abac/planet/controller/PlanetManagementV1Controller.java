package com.abac.planet.controller;

import com.abac.planet.bm.constraints.UniquePlanetConstraintById;
import com.abac.planet.bm.constraints.UniquePlanetMappedConstraint;
import com.abac.planet.bm.service.PlanetManagementService;
import com.abac.planet.common.ErrorTO;
import com.abac.planet.common.constants.HeaderConstants;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * Planet Management v1 APIs
 *
 * @author sergiu-daniel.cionca
 */
@Api(value = "Planet Management API v1", tags = "Planet Management API (v1)", authorizations = @Authorization(value = "basic"))
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Validated
public class PlanetManagementV1Controller {

    private final PlanetManagementService planetManagementService;

    @ApiOperation(value = "Assign crew to planet.", notes = "Planet can have only one crew assigned at a time.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Crew assigned successfully to planet.", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Bad request.", response = ErrorTO.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorTO.class), })
    @PostMapping(value = "/api/v1/planet/assign", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> assignCrewToPlanet(
            @RequestHeader(name = HeaderConstants.CREW_ID) @NotNull(message = "Crew id must not be null!") final String crewId,
            @RequestHeader(name = HeaderConstants.PLANET_ID) @UniquePlanetConstraintById(message = "Planet with provided id is not available!!!") @UniquePlanetMappedConstraint(message = "Planet with provided id has already a crew assigned to it!!!") final String planetId) {
        log.info("Attempt to assign crew with id: {} to planet with id: {}", crewId, planetId);

        planetManagementService.assignCrewToPlanet(planetId, crewId);

        log.info("Successfully assigned crew with id: {} to planet with id: {}", crewId, planetId);

        return ResponseEntity.ok().build();
    }
}
