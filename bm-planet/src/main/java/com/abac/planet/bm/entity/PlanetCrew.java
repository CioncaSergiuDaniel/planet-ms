package com.abac.planet.bm.entity;

import lombok.*;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Builder(builderClassName = "Builder")
@Table(value = "planet_crew")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlanetCrew {

    @PrimaryKeyColumn(
            name = "id",
            ordinal = 2,
            type = PrimaryKeyType.CLUSTERED,
            ordering = Ordering.DESCENDING)
    private long id;

    @PrimaryKeyColumn(name = "planet_external_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String planetExternalId;

    @PrimaryKeyColumn(name = "crew_external_id", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String crewExternalId;
}
