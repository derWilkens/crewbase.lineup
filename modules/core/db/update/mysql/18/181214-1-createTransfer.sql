create table LINEUP_TRANSFER (
    ID varchar(32),
    --
    TRANSFER_ORDER_NO integer not null,
    ANCHOR_WAYPOINT_ID varchar(32),
    CREW_CHANGE_ID varchar(32),
    OPERATED_BY_ID varchar(32),
    MODE_OF_TRANSFER_ID varchar(32),
    CRAFT_TYPE_ID varchar(32),
    --
    primary key (ID)
);
