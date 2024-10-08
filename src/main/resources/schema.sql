CREATE TABLE IF NOT EXISTS movies
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title       VARCHAR(255)                            NOT NULL,
    director    VARCHAR(255)                            NOT NULL,
    release_date DATE                                    NOT NULL,
    genre       VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

DELETE
from movies;

ALTER TABLE movies
    ALTER COLUMN id RESTART WITH 1;