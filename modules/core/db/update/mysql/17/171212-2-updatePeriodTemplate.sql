alter table LINEUP_PERIOD_TEMPLATE add column DURATION1 integer ;
alter table LINEUP_PERIOD_TEMPLATE add column DURATION2 integer ;
alter table LINEUP_PERIOD_TEMPLATE add column DURATION3 integer ;
alter table LINEUP_PERIOD_TEMPLATE drop column DEFAULT_DURATION cascade ;
alter table LINEUP_PERIOD_TEMPLATE drop column PERIOD_KIND cascade ;
alter table LINEUP_PERIOD_TEMPLATE add column PERIOD_KIND varchar(70) ;
