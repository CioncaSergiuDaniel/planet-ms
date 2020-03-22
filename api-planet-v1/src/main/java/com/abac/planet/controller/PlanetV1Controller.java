package com.abac.planet.controller;

import com.abac.planet.bm.constraints.UniquePlanetConstraintByName;
import com.abac.planet.bm.entity.Planet;
import com.abac.planet.bm.entity.enums.Status;
import com.abac.planet.bm.service.PlanetService;
import com.abac.planet.common.ErrorTO;
import com.abac.planet.to.PlanetTO;
import com.abac.planet.transformer.PlanetTransformer;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Planet v1 APIs
 *
 * @author sergiu-daniel.cionca
 */
@Api(value = "Planet API v1", tags = "Planet API (v1)", authorizations = @Authorization(value = "basic"))
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Validated
public class PlanetV1Controller {

    private final PlanetService planetService;

    private final PlanetTransformer planetTransformer;

    @ApiOperation(value = "Retrieves a list of planets.", notes = "Planets are retrieved without the crew details assigned to them.", response = PlanetTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Valid list of planets.", response = PlanetTO.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorTO.class), })
    @GetMapping(value = "/api/v1/planets", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getPlanets() {
        final List<PlanetTO> planets = new ArrayList<>();

        planetService.loadPlanets().toStream()
                .map(planetTransformer::toPlanetTO)
                .collect(Collectors.toCollection(() -> planets));

        return ResponseEntity.ok().body(planets);
    }

    @ApiOperation(value = "Create a new planet.", notes = "Create a planet without a crew assigned to it.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Valid list of planets.", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Bad request.", response = ErrorTO.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorTO.class), })
    @PostMapping(value = "/api/v1/planet/{name}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPlanet(
            @PathVariable("name") @UniquePlanetConstraintByName(message = "Planet with provided name already exists!!!") final String name,
            @RequestParam("image") @NotNull(message = "Image is required in order to successfully create a planet!") final MultipartFile image) throws IOException {

        log.info("Attempt to persist Planet with name: {}", name);

        planetService.storePlanet(Planet.builder()
                .externalId(UUID.randomUUID().toString())
                .name(name)
                .image(ByteBuffer.wrap(image.getBytes()))
                .status(Status.TODO)
                .build());

        log.info("Planet: {} successfully saved!", name);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
