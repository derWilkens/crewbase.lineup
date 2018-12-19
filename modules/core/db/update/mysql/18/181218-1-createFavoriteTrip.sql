create table LINEUP_FAVORITE_TRIP (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    START_SITE_ID varchar(32) not null,
    DESTINATION_ID varchar(32) not null,
    EMAIL_NOTIFICATION boolean,
    ROUND_TRIP boolean,
    --
    primary key (ID)
);
