package com.abac.planet.bm.repository;

import com.abac.planet.bm.entity.PlanetCrew;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Mono;

/**
 * Planet management repository for reactive cassandra to store the planet assigned to crew
 *
 * @author sergiu-daniel.cionca
 */
public interface PlanetManagementRepository extends ReactiveCassandraRepository<PlanetCrew, Long> {

    @AllowFiltering
    Mono<Boolean> existsByPlanetExternalIdIs(final String planetExternalId);
}
