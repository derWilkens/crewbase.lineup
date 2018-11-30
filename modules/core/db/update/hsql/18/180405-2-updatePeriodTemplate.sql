alter table LINEUP_PERIOD_TEMPLATE alter column PERIOD_KIND rename to PERIOD_KIND__UNUSED ;
alter table LINEUP_PERIOD_TEMPLATE add column PERIOD_KIND varchar(70) ;
