alter table LINEUP_AIRPORT add constraint FK_LINEUP_AIRPORT_ON_CATEGORY foreign key (CATEGORY_ID) references LINEUP_SITE_CATEGORY(ID);
create index IDX_LINEUP_AIRPORT_ON_CATEGORY on LINEUP_AIRPORT (CATEGORY_ID);