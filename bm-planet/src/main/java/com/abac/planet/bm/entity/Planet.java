package com.abac.planet.bm.entity;

import com.abac.planet.bm.entity.enums.Status;
import com.datastax.driver.core.DataType;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.*;

import java.nio.ByteBuffer;

@Builder(builderClassName = "Builder")
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Planet {

    @PrimaryKey
    private long id;

    @Column(value = "external_id")
    @Indexed
    private String externalId;

    @Indexed
    private String name;

    private ByteBuffer image;

    @CassandraType(type = DataType.Name.TEXT)
    private Status status;
}
