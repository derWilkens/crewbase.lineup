alter table LINEUP_PERIOD_TEMPLATE add column REMARK varchar(255) ;
alter table LINEUP_PERIOD_TEMPLATE drop column PERIOD_KIND cascade ;
alter table LINEUP_PERIOD_TEMPLATE add column PERIOD_KIND varchar(70) ;
