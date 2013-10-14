# --- !Ups
CREATE SEQUENCE location_seq;
CREATE SEQUENCE image_seq;
CREATE SEQUENCE person_seq;
CREATE SEQUENCE event_seq;
CREATE SEQUENCE event_image_seq;
CREATE SEQUENCE event_person_seq;

CREATE TABLE location(
    id INTEGER DEFAULT nextval('location_seq'),
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL
);
CREATE TABLE image(
    id INTEGER DEFAULT nextval('image_seq'),
    name VARCHAR(30) UNIQUE NOT NULL,
    content BLOB NOT NULL
);
CREATE TABLE person(
    id INTEGER DEFAULT nextval('person_seq'),
    name VARCHAR(50) UNIQUE NOT NULL,
    profile VARCHAR(100),
    img_id INTEGER,
    FOREIGN KEY(img_id) REFERENCES image(id)
);
CREATE TABLE event(
    id VARCHAR(30) NOT NULL DEFAULT nextval('event_seq'),
    title VARCHAR(100) NOT NULL,
    content VARCHAR(2000),
    datetime DATE NOT NULL,
    location_id INT,
    FOREIGN KEY(location_id) REFERENCES location(id)
);
CREATE TABLE event_image(
    id INTEGER DEFAULT nextval('event_image_seq'),
    event_id INTEGER,
    img_id INTEGER,
    FOREIGN KEY(event_id) REFERENCES event(id),
    FOREIGN KEY(img_id) REFERENCES image(id)
);
CREATE TABLE event_person(
    id INTEGER DEFAULT nextval('event_person_seq'),
    event_id INTEGER,
    person_id INTEGER,
    FOREIGN KEY(event_id) REFERENCES event(id),
    FOREIGN KEY(person_id) REFERENCES person(id)
);


# --- !Downs
DROP TABLE event;
DROP TABLE event_image;
DROP TABLE event_person;
DROP TABLE person;
DROP TABLE image;
DROP TABLE location;

DROP SEQUENCE location_seq;
DROP SEQUENCE image_seq;
DROP SEQUENCE person_seq;
DROP SEQUENCE event_seq;
DROP SEQUENCE event_image_seq;
DROP SEQUENCE event_person_seq;