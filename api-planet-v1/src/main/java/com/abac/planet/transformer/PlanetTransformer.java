package com.abac.planet.transformer;

import com.abac.planet.bm.entity.Planet;
import com.abac.planet.to.PlanetTO;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PlanetTransformer {

    /**
     * planet to transfer object
     *
     * @param planet {@link Planet}
     * @return {@link PlanetTO}
     */
    public PlanetTO toPlanetTO(final Planet planet) {
        return PlanetTO.builder()
                .id(planet.getExternalId())
                .name(planet.getName())
                .image(planet.getImage().array())
                .status(planet.getStatus().getValue())
                .build();
    }
}
