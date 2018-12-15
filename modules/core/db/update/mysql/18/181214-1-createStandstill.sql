create table LINEUP_STANDSTILL (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    DTYPE varchar(31),
    --
    SITE_ID varchar(32),
    TRANSFER_ID varchar(32),
    NEXT_WAYPOINT_ID varchar(32),
    --
    primary key (ID)
);
