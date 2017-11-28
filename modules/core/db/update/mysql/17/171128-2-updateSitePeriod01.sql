alter table LINEUP_SITE_PERIOD add constraint FK_LINEUP_SITE_PERIOD_FUNCTION_CATEGORY foreign key (FUNCTION_CATEGORY_ID) references LINEUP_FUNCTION_CATEGORY(ID);
create index IDX_LINEUP_SITE_PERIOD_FUNCTION_CATEGORY on LINEUP_SITE_PERIOD (FUNCTION_CATEGORY_ID);
