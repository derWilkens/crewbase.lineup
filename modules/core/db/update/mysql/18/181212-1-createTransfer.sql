create table LINEUP_TRANSFER (
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
    TRANSFER_ORDER_NO integer not null,
    CREW_CHANGE_ID varchar(32),
    OPERATED_BY_ID varchar(32),
    MODE_OF_TRANSFER_ID varchar(32),
    CRAFT_TYPE_ID varchar(32),
    --
    primary key (ID)
);
