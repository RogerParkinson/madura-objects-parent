CREATE TABLE SQL_LOCK
(
  lockName  VARCHAR(100) PRIMARY KEY NOT NULL,
  ownerName  VARCHAR(100)  NOT NULL,
  started  VARCHAR(255)  NOT NULL,
  comments  VARCHAR(255),
  hostAddress  VARCHAR(100) NOT NULL
);

