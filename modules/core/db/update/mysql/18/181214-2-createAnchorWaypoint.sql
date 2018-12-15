alter table LINEUP_ANCHOR_WAYPOINT add constraint FK_LINEUP_ANCHOR_WAYPOINT_ON_ID foreign key (ID) references LINEUP_STANDSTILL(ID) on delete CASCADE;
