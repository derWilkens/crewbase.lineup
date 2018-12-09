create table LINEUP_WAY (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    DEPARTURE_SITE_ID varchar(32),
    ARRIVAL_SITE_ID varchar(32),
    TRAVEL_TIME integer,
    OCCUPIED_SEATS integer,
    --
    primary key (ID)
);
