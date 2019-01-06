create table LINEUP_MAIL_NOTIFICATION (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    --
    TRANSFER_ID varchar(32),
    USER_ID varchar(32),
    SENT datetime(3),
    --
    primary key (ID)
);
