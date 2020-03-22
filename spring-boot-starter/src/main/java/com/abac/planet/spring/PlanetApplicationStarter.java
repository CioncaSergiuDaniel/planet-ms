package com.abac.planet.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

/**
 * Starter of Planet Service
 *
 * @author sergiu-daniel.cionca
 */
@SpringBootApplication(scanBasePackages = "com.abac.planet")
@EnableReactiveCassandraRepositories("com.abac.planet.bm.repository")
public class PlanetApplicationStarter {

    /**
     * Starts the application.
     *
     * @param args the arguments given at command line
     */
    public static void main(final String[] args) {
        SpringApplication.run(PlanetApplicationStarter.class, args);
    }

}
