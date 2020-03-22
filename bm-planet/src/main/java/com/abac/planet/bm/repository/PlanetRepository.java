package com.abac.planet.bm.repository;

import com.abac.planet.bm.entity.Planet;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Mono;

/**
 * Planet repository for reactive cassandra to store the planet
 *
 * @author sergiu-daniel.cionca
 */
public interface PlanetRepository extends ReactiveCassandraRepository<Planet, Long> {

    /**
     * check planet existence by name
     *
     * @param name
     *
     * @return {@link Mono<Boolean>}
     *                  existence flag
     */
    @AllowFiltering
    Mono<Boolean> existsPlanetByNameIs(final String name);

    /**
     * check planet existence by externalId
     *
     * @param externalId
     *
     * @return {@link Mono<Boolean>}
     *                  existence flag
     */
    @AllowFiltering
    Mono<Boolean> existsByExternalIdIs(final String externalId);
}
