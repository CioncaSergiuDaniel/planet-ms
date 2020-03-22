package com.abac.planet.bm.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * metrics provided by bm-planet
 * exposed trough actuator
 *
 * @author sergiu-daniel.cionca
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BmMetricsService {

    private final MeterRegistry meterRegistry;

    public void incrementPersistedPlanets() {
        meterRegistry.counter("persisted_raw_planet").increment();
    }

    public void incrementAssignedPlanets() {
        meterRegistry.counter("assigned_planet_count").increment();
    }
}
