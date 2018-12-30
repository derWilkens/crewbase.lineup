create table LINEUP_AIRPORT (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    SITE_NAME varchar(50) not null,
    LATITUDE double precision,
    LONGITUDE double precision,
    ITEM_DESIGNATION varchar(7),
    PARENT_SITE_ID varchar(32),
    SHORT_ITEM_DESIGNATION varchar(4),
    CATEGORY_ID varchar(32),
    SITE_TYPE_ID varchar(32),
    --
    ICAO_CODE varchar(4),
    IATA_CODE varchar(4),
    --
    primary key (ID)
);
