# Users schema

# --- !Ups

CREATE TABLE todoitem
( ID          serial PRIMARY KEY,
  PRIORITY    INTEGER NOT NULL,
  DESCRIPTION TEXT,
  ISDONE      BOOLEAN
);

# --- !Downs

DROP TABLE todoitem;




