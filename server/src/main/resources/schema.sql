CREATE TABLE IF NOT EXISTS USERS
(
    USER_ID BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    NAME    VARCHAR(255)                                        NOT NULL,
    EMAIL   VARCHAR(255) UNIQUE                                 NOT NULL
);

CREATE TABLE IF NOT EXISTS ITEMS
(
    ITEM_ID      BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    NAME         VARCHAR(255)                                        NOT NULL,
    DESCRIPTION  VARCHAR(100)                                        NOT NULL,
    IS_AVAILABLE BOOLEAN                                         DEFAULT TRUE,
    OWNER_ID     BIGINT                                                      ,
    REQUEST_ID   BIGINT
);

CREATE TABLE IF NOT EXISTS BOOKINGS
(
    BOOKING_ID BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    START_DATE    TIMESTAMP                                        NOT NULL,
    END_DATE      TIMESTAMP                                        NOT NULL,
    ITEM_ID       BIGINT                                                   ,
    BOOKER_ID     BIGINT                                                   ,
    STATUS        VARCHAR
);

CREATE TABLE IF NOT EXISTS COMMENTS
(
    COMMENT_ID BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    TEXT       VARCHAR(1000)                                       NOT NULL,
    ITEM_ID    BIGINT                                                      ,
    AUTHOR_ID  BIGINT                                                      ,
    CREATED    TIMESTAMP                                           NOT NULL
);

CREATE TABLE IF NOT EXISTS REQUESTS
(
    REQUEST_ID   BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    DESCRIPTION  VARCHAR(1000)                                       NOT NULL,
    REQUESTER_ID BIGINT                                                      ,
    CREATED      TIMESTAMP                                           NOT NULL
);