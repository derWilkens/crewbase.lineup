alter table LINEUP_PERIOD_TEMPLATE add constraint FK_LINEUP_PERIOD_TEMPLATE_FUNCTION_CATEGORY foreign key (FUNCTION_CATEGORY_ID) references LINEUP_FUNCTION_CATEGORY(ID);
alter table LINEUP_PERIOD_TEMPLATE add constraint FK_LINEUP_PERIOD_TEMPLATE_SITE foreign key (SITE_ID) references LINEUP_SITE(ID);
alter table LINEUP_PERIOD_TEMPLATE add constraint FK_LINEUP_PERIOD_TEMPLATE_USER foreign key (USER_ID) references SEC_USER(ID);
create index IDX_LINEUP_PERIOD_TEMPLATE_FUNCTION_CATEGORY on LINEUP_PERIOD_TEMPLATE (FUNCTION_CATEGORY_ID);
create index IDX_LINEUP_PERIOD_TEMPLATE_SITE on LINEUP_PERIOD_TEMPLATE (SITE_ID);
create index IDX_LINEUP_PERIOD_TEMPLATE_USER on LINEUP_PERIOD_TEMPLATE (USER_ID);
