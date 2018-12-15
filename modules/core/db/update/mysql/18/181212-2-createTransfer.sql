alter table LINEUP_TRANSFER add constraint FK_LINEUP_TRANSFER_ON_SITE foreign key (SITE_ID) references LINEUP_SITE(ID);
alter table LINEUP_TRANSFER add constraint FK_LINEUP_TRANSFER_ON_TRANSFER foreign key (TRANSFER_ID) references LINEUP_TRANSFER(ID);
alter table LINEUP_TRANSFER add constraint FK_LINEUP_TRANSFER_ON_NEXT_WAYPOINT foreign key (NEXT_WAYPOINT_ID) references LINEUP_WAYPOINT(ID);
alter table LINEUP_TRANSFER add constraint FK_LINEUP_TRANSFER_ON_CREW_CHANGE foreign key (CREW_CHANGE_ID) references LINEUP_CREW_CHANGE(ID);
alter table LINEUP_TRANSFER add constraint FK_LINEUP_TRANSFER_ON_OPERATED_BY foreign key (OPERATED_BY_ID) references LINEUP_COMPANY(ID);
alter table LINEUP_TRANSFER add constraint FK_LINEUP_TRANSFER_ON_MODE_OF_TRANSFER foreign key (MODE_OF_TRANSFER_ID) references LINEUP_MODE_OF_TRANSFER(ID);
alter table LINEUP_TRANSFER add constraint FK_LINEUP_TRANSFER_ON_CRAFT_TYPE foreign key (CRAFT_TYPE_ID) references LINEUP_CRAFT_TYPE(ID);
create index IDX_LINEUP_TRANSFER_ON_SITE on LINEUP_TRANSFER (SITE_ID);
create index IDX_LINEUP_TRANSFER_ON_TRANSFER on LINEUP_TRANSFER (TRANSFER_ID);
create index IDX_LINEUP_TRANSFER_ON_NEXT_WAYPOINT on LINEUP_TRANSFER (NEXT_WAYPOINT_ID);
create index IDX_LINEUP_TRANSFER_ON_CREW_CHANGE on LINEUP_TRANSFER (CREW_CHANGE_ID);
create index IDX_LINEUP_TRANSFER_ON_OPERATED_BY on LINEUP_TRANSFER (OPERATED_BY_ID);
create index IDX_LINEUP_TRANSFER_ON_MODE_OF_TRANSFER on LINEUP_TRANSFER (MODE_OF_TRANSFER_ID);
create index IDX_LINEUP_TRANSFER_ON_CRAFT_TYPE on LINEUP_TRANSFER (CRAFT_TYPE_ID);
