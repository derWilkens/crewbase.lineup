alter table LINEUP_TRANSFER add constraint FK_LINEUP_TRANSFER_ON_NEXT_WAYPOINT foreign key (NEXT_WAYPOINT_ID) references LINEUP_WAYPOINT(ID);
