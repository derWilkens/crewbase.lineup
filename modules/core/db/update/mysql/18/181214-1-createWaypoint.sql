create table LINEUP_WAYPOINT (
    ID varchar(32),
    --
    TAKE_OFF time(3),
    PREVIOUS_STANDSTILL_ID varchar(32),
    STOPOVER_TIME integer,
    --
    primary key (ID)
);
