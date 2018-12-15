create table LINEUP_WAYPOINT (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    SITE_ID varchar(32),
    TRANSFER_ID varchar(32),
    NEXT_WAYPOINT_ID varchar(32),
    --
    TAKE_OFF time(3),
    PREVIOUS_STANDSTILL_ID varchar(32),
    STOPOVER_TIME integer,
    --
    primary key (ID)
);
