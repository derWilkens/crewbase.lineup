alter table LINEUP_LOCATION add constraint FK_LINEUP_LOCATION_ON_CATEGORY foreign key (CATEGORY_ID) references LINEUP_SITE_CATEGORY(ID);
create index IDX_LINEUP_LOCATION_ON_CATEGORY on LINEUP_LOCATION (CATEGORY_ID);