create table LINEUP_ANCHOR_WAYPOINT (
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
    START_DATE_TIME datetime(3),
    --
    primary key (ID)
);
