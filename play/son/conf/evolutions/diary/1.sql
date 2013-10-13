# --- !Ups
CREATE TABLE location(
    id INTEGER PRIMARY KEY,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL
);
CREATE TABLE image(
    id INTEGER PRIMARY KEY,
    name VARCHAR(30) UNIQUE NOT NULL,
    content BLOB NOT NULL
);
CREATE TABLE person(
    id INTEGER PRIMARY KEY,
    name VARCHAR(50) UNIQUE,
    img_id INTEGER,
    FOREIGN KEY(img_id) REFERENCES image(id)
);
CREATE TABLE event(
    id VARCHAR(30) NOT NULL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    datetime DATE NOT NULL,
    location_id INT,
    FOREIGN KEY(location_id) REFERENCES location(id)
);
CREATE TABLE event_image(
    id INTEGER PRIMARY KEY,
    event_id INTEGER,
    img_id INTEGER,
    FOREIGN KEY(event_id) REFERENCES event(id),
    FOREIGN KEY(img_id) REFERENCES image(id)
);
CREATE TABLE event_person(
    id INTEGER PRIMARY KEY,
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

