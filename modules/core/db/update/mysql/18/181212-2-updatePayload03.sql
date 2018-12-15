alter table LINEUP_PAYLOAD add constraint FK_LINEUP_PAYLOAD_ON_SITE_B foreign key (SITE_B_ID) references LINEUP_SITE(ID);
create index IDX_LINEUP_PAYLOAD_ON_SITE_B on LINEUP_PAYLOAD (SITE_B_ID);
