alter table LINEUP_TICKET add constraint FK_LINEUP_TICKET_ON_PASSENGER foreign key (PASSENGER_ID) references LINEUP_APP_USER(ID);
create index IDX_LINEUP_TICKET_ON_PASSENGER on LINEUP_TICKET (PASSENGER_ID);
