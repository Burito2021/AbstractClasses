create table REVISERS
(
    ID            BIGINT AUTO_INCREMENT,
    SURNAME VARCHAR(36) NOT NULL,
    NAME       VARCHAR(36) NOT NULL,
    AGE      VARCHAR(36) NOT NULL,
    ENTRY_DATE  DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (ID)
);