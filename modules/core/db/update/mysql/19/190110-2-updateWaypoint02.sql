alter table LINEUP_WAYPOINT add constraint FK_LINEUP_WAYPOINT_ON_TRANSFER foreign key (TRANSFER_ID) references LINEUP_TRANSFER(ID);
create index IDX_LINEUP_WAYPOINT_ON_TRANSFER on LINEUP_WAYPOINT (TRANSFER_ID);