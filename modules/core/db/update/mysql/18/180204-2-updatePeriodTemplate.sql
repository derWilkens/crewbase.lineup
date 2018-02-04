alter table LINEUP_PERIOD_TEMPLATE change column PERIOD_KIND PERIOD_KIND__UNUSED varchar(70);
alter table LINEUP_PERIOD_TEMPLATE add column PERIOD_KIND varchar(70) ;
