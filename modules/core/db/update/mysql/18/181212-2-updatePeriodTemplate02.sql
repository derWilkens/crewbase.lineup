alter table LINEUP_PERIOD_TEMPLATE add constraint FK_LINEUP_PERIOD_TEMPLATE_ON_USER foreign key (USER_ID) references SEC_USER(ID);
create index IDX_LINEUP_PERIOD_TEMPLATE_ON_USER on LINEUP_PERIOD_TEMPLATE (USER_ID);
