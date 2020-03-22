package com.abac.planet.bm.service;

import com.abac.planet.bm.entity.PlanetCrew;
import com.abac.planet.bm.metrics.BmMetricsService;
import com.abac.planet.bm.repository.PlanetManagementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * planet management service class in order to assign crew to planet
 *
 * @author sergiu-daniel.cionca
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlanetManagementService {

    private final BmMetricsService bmMetricsService;

    private final PlanetManagementRepository planetManagementRepository;

    public Mono<Boolean> isPlanetMappedToCrew(final String planetExternalId) {
        return planetManagementRepository.existsByPlanetExternalIdIs(planetExternalId);
    }

    public void assignCrewToPlanet(final String planetExternalId, final String crewExternalId) {
        final PlanetCrew.Builder builder = PlanetCrew.builder();
        builder.id(System.currentTimeMillis());
        builder.planetExternalId(planetExternalId);
        builder.crewExternalId(crewExternalId);

        planetManagementRepository.save(builder.build())
                .doOnSuccess(p -> bmMetricsService.incrementAssignedPlanets())
                .subscribe(p -> log.info(p.toString()));
    }
}
