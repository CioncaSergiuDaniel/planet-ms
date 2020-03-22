package com.abac.planet.to;

import lombok.*;

/**
 * Planet transfer object
 *
 * @author sergiu-daniel.cionca
 */
@Builder(builderClassName = "Builder")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PlanetTO {

    private String id;

    private String name;

    private byte[] image;

    private String status;
}
