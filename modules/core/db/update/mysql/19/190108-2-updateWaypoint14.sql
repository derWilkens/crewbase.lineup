alter table LINEUP_WAYPOINT add constraint FK_LINEUP_WAYPOINT_ON_HEAD_WAYPOINT foreign key (HEAD_WAYPOINT_ID) references LINEUP_WAYPOINT(ID);
create index IDX_LINEUP_WAYPOINT_ON_HEAD_WAYPOINT on LINEUP_WAYPOINT (HEAD_WAYPOINT_ID);