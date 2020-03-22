CREATE TABLE IF NOT EXISTS planet_by_crew
(
	id bigint,
	planet_external_id text,
	crew_external_id text,
    PRIMARY KEY((planet_external_id, crew_external_id))
);