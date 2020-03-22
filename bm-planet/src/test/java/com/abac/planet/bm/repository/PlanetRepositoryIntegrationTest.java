//package com.abac.planet.bm.repository;
//
//import static org.junit.Assert.assertEquals;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.ByteBuffer;
//import java.nio.file.Files;
//import java.util.HashMap;
//import java.util.Optional;
//import java.util.UUID;
//
//import org.apache.cassandra.exceptions.ConfigurationException;
//import org.apache.commons.lang.StringUtils;
//import org.apache.thrift.transport.TTransportException;
//import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.cassandra.core.CassandraAdminOperations;
//import org.springframework.data.cassandra.core.cql.CqlIdentifier;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.abac.planet.bm.configuration.CassandraConfiguration;
//import com.abac.planet.bm.entity.Planet;
//import com.abac.planet.bm.entity.enums.Status;
//import com.datastax.driver.core.Cluster;
//import com.datastax.driver.core.Session;
//import com.datastax.driver.core.utils.UUIDs;
//import reactor.core.publisher.Mono;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = CassandraConfiguration.class)
//public class PlanetRepositoryIntegrationTest {
//
//    public static final String KEYSPACE_CREATION_QUERY =
//            "CREATE KEYSPACE IF NOT EXISTS space WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";
//
//    public static final String KEYSPACE_ACTIVATE_QUERY = "USE space;";
//
//    public static final String DATA_TABLE_NAME = "planet";
//
//    @Autowired
//    private PlanetRepository planetRepository;
//
//    @Autowired
//    private CassandraAdminOperations adminTemplate;
//
//    //
//
//    @BeforeClass
//    public static void startCassandraEmbedded()
//            throws InterruptedException, TTransportException, ConfigurationException, IOException {
//        EmbeddedCassandraServerHelper.startEmbeddedCassandra();
//        final Cluster cluster = Cluster.builder().addContactPoints("127.0.0.1").withPort(9042).build();
//
//        final Session session = cluster.connect();
//        session.execute(KEYSPACE_CREATION_QUERY);
//        session.execute(KEYSPACE_ACTIVATE_QUERY);
//
//        Thread.sleep(5000);
//    }
//
//    @Before
//    public void createTable() throws InterruptedException, TTransportException, ConfigurationException, IOException {
//        adminTemplate
//                .createTable(true, CqlIdentifier.cqlId(DATA_TABLE_NAME), Planet.class, new HashMap<String, Object>());
//    }
//
//    @Test
//    public void planetSaved_validPlanet_successfullyStored() throws IOException {
//        // given
//        final File image = new File("src/test/resources/image/sky.jpg");
//        final byte[] imageContent = Files.readAllBytes(image.toPath());
//        final ByteBuffer byteBuffer = ByteBuffer.wrap(imageContent);
//
//        final UUID key = UUIDs.timeBased();
//        final String externalId = UUID.randomUUID().toString();
//        final Planet planet = new Planet(key, externalId, "Planet_1", byteBuffer, Status.EN_ROUTE, "crew_id");
//
//        // when
//        planetRepository.save(planet);
//
//        // then
//        final Mono<Planet> planets = planetRepository.findById(Long.valueOf(key.toString()));
//
//        final Planet planet_1 = planets.block();
//        //final Planet planet_1 = planets.iterator().next();
//
//        //assertEquals(key, planet_1.getId());
//        assertEquals("Planet_1", planet_1.getName());
//        assertEquals(imageContent.length, planet_1.getImage().array().length);
//        assertEquals(Status.EN_ROUTE, planet_1.getStatus());
//        assertEquals("crew_id", planet_1.getCrewId());
//    }
//
//    @Test
//    public void aa(){
//
//        String s = extractName("BMW-CarData-Report.jrxml");
//
//    }
//
//    private String extractName(final String completeFileName) {
//        String fileName = "BMW-CarData-Report";
//        if (StringUtils.isNotEmpty(completeFileName)) {
//            fileName = completeFileName.substring(0, completeFileName.indexOf("."));
//        }
//
//        return fileName;
//    }
//
//    @After
//    public void dropTable() {
//        adminTemplate.dropTable(CqlIdentifier.cqlId(DATA_TABLE_NAME));
//    }
//
//    @AfterClass
//    public static void stopCassandraEmbedded() {
//        EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
//    }
//}
