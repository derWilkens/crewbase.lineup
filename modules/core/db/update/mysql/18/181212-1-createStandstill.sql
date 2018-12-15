create table LINEUP_STANDSTILL (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    DTYPE varchar(100),
    --
    SITE_ID varchar(32),
    TRANSFER_ID varchar(32),
    NEXT_WAYPOINT_ID varchar(32),
    --
    -- from lineup$AnchorWaypoint
    START_DATE_TIME datetime(3),
    --
    -- from lineup$Waypoint
    TAKE_OFF time(3),
    PREVIOUS_STANDSTILL_ID varchar(32),
    STOPOVER_TIME integer,
    --
    -- from lineup$Transfer
    TRANSFER_ORDER_NO integer not null,
    CREW_CHANGE_ID varchar(32),
    OPERATED_BY_ID varchar(32),
    MODE_OF_TRANSFER_ID varchar(32),
    CRAFT_TYPE_ID varchar(32),
    --
    primary key (ID)
);
