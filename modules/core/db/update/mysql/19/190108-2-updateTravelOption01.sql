alter table LINEUP_TRAVEL_OPTION add constraint FK_LINEUP_TRAVEL_OPTION_ON_TRANSFER foreign key (TRANSFER_ID) references LINEUP_TRANSFER(ID);
create index IDX_LINEUP_TRAVEL_OPTION_ON_TRANSFER on LINEUP_TRAVEL_OPTION (TRANSFER_ID);