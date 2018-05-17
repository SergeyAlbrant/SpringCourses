CREATE TABLE users (
  id         INTEGER PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(30),
  last_name  VARCHAR(30),
  email      VARCHAR(30),
  birthday   TIMESTAMP
);

CREATE TABLE events (
  id         INTEGER PRIMARY KEY AUTO_INCREMENT,
  name       VARCHAR(30),
  base_price DOUBLE,
  rating     varchar(255)
  check (rating in ('LOW', 'MID', 'HIGH'))
);

CREATE TABLE tickets (
  id        INTEGER PRIMARY KEY AUTO_INCREMENT,
  seat      INTEGER,
  date_time TIMESTAMP,
  user_id   INTEGER,
  FOREIGN KEY (user_id) REFERENCES users (id),
  event_id  INTEGER,
  FOREIGN KEY (event_id) REFERENCES events (id)
);

CREATE TABLE event_counter (
  id                 INTEGER PRIMARY KEY AUTO_INCREMENT,
  event_counter_case INTEGER,
  count              BIGINT

);

CREATE TABLE discount_counter (
  id                INTEGER PRIMARY KEY AUTO_INCREMENT,
  discount_strategy VARCHAR(50),
  count             BIGINT,
  user_id           INTEGER,
  FOREIGN KEY (user_id) REFERENCES users (id),
)