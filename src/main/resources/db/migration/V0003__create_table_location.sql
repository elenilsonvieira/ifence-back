CREATE TABLE IF NOT EXISTS locations (
	id BIGSERIAL,
	latitude DECIMAL(10,8) NOT NULL,
	longitude DECIMAL(11,8) NOT NULL,
	bracelet_id BIGINT NOT NULL,
	creation_date TIMESTAMP NOT NULL,
	
	CONSTRAINT location_pk PRIMARY KEY (id),
	CONSTRAINT bracelet_id_fk_location FOREIGN KEY (bracelet_id) REFERENCES bracelets(id) ON DELETE CASCADE,
	CONSTRAINT valid_latitude_location CHECK (latitude >= -90 AND latitude <= 90),
	CONSTRAINT valid_longitude_location CHECK (longitude >= -180 AND latitude <= 180)
);