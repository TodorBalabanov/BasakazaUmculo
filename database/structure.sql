CREATE DATABASE mididermi WITH TEMPLATE = template0 ENCODING = 'UTF8';
ALTER DATABASE mididermi OWNER TO postgres;
COMMENT ON DATABASE mididermi IS 'MIDIDERMI database storage.';


SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;
SET search_path = public, pg_catalog;
SET default_tablespace = '';
SET default_with_oids = false;


CREATE PROCEDURAL LANGUAGE plpgsql;
ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;


CREATE SEQUENCE melodies_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
ALTER TABLE melodies_id_seq OWNER TO postgres;
COMMENT ON SEQUENCE melodies_id_seq IS 'Provide melodies table primary key unique value.';


CREATE TABLE melodies (
    id integer NOT NULL,
    system_id bigint,
    sequence character varying,
    timber integer,
    score integer,
    genre integer
);
ALTER TABLE melodies OWNER TO postgres;
ALTER TABLE ONLY melodies ADD CONSTRAINT standard PRIMARY KEY (id);


COMMENT ON TABLE melodies IS 'Melodies acting on the server side melody pool.';
COMMENT ON COLUMN melodies.id IS 'Unique identifier.';
COMMENT ON COLUMN melodies.system_id IS 'Unique identifier used into application system.';
COMMENT ON COLUMN melodies.sequence IS 'Sequence of notes, integer numbers separated by space symbol, grouped by four for each note property (note index, offset from begging, duration of the note, velocity of the note). ';
COMMENT ON COLUMN melodies.timber IS 'Timber index (instrument index into loaded bank of instruments).';
COMMENT ON COLUMN melodies.score IS 'Melody score obtained by the end users.';
COMMENT ON COLUMN melodies.genre IS 'Genre index provided by predefined list of genres.';


CREATE FUNCTION add_melody(system_id bigint, sequence character varying, timber integer, score integer, genre integer) RETURNS void
AS $_$DECLARE unique_id INTEGER;
BEGIN
	DELETE FROM melodies WHERE melodies.system_id = $1;
	unique_id = nextval('melodies_id_seq');
	INSERT INTO melodies VALUES (unique_id, $1, $2, $3, $4, $5);
END$_$ LANGUAGE plpgsql;
ALTER FUNCTION add_melody(system_id bigint, sequence character varying, timber integer, score integer, genre integer) OWNER TO postgres;
COMMENT ON FUNCTION add_melody(bigint, character varying, integer, integer, integer) IS 'Add melody or replace existing melody.';


CREATE FUNCTION delete_melodies() RETURNS void
AS $$BEGIN 
	DELETE FROM melodies;
END$$ LANGUAGE plpgsql;
ALTER FUNCTION delete_melodies() OWNER TO postgres;
COMMENT ON FUNCTION delete_melodies() IS 'Delete all melodies.';


CREATE FUNCTION get_all_melodies() RETURNS SETOF melodies
AS $$BEGIN
	RETURN QUERY SELECT * FROM melodies;
END$$ LANGUAGE plpgsql;
ALTER FUNCTION get_all_melodies() OWNER TO postgres;
COMMENT ON FUNCTION get_all_melodies() IS 'Return all available melodies.';
