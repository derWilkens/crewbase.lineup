alter table LINEUP_SITE_PERIOD add constraint FK_LINEUP_SITE_PERIOD_SITE foreign key (SITE_ID) references LINEUP_SITE(ID);
create index IDX_LINEUP_SITE_PERIOD_SITE on LINEUP_SITE_PERIOD (SITE_ID);