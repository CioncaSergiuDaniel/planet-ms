package com.abac.planet.bm.service;

import com.abac.planet.bm.entity.Planet;
import com.abac.planet.bm.metrics.BmMetricsService;
import com.abac.planet.bm.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * planet service class in order to work with planets
 *
 * @author sergiu-daniel.cionca
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlanetService {

    private final BmMetricsService bmMetricsService;

    private final PlanetRepository planetRepository;

    public Flux<Planet> loadPlanets() {
        return planetRepository.findAll();
    }

    public void storePlanet(final Planet planet) {
        // add the id of entity right before the persist
        planet.setId(System.currentTimeMillis());

        planetRepository.save(planet)
                .doOnSuccess(p -> bmMetricsService.incrementPersistedPlanets())
                .subscribe(p -> log.info(p.toString()));
    }

    public Mono<Boolean> existsByName(final String name) {
        return planetRepository.existsPlanetByNameIs(name);
    }

    public Mono<Boolean> existsByExternalId(final String externalId) {
        return planetRepository.existsByExternalIdIs(externalId);
    }
}
