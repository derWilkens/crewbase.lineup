alter table LINEUP_PAYLOAD add constraint FK_LINEUP_PAYLOAD_ON_SITE_A foreign key (SITE_A_ID) references LINEUP_SITE(ID);
create index IDX_LINEUP_PAYLOAD_ON_SITE_A on LINEUP_PAYLOAD (SITE_A_ID);
