alter table LINEUP_PERIOD_TEMPLATE drop column PERIOD_KIND cascade ;
alter table LINEUP_PERIOD_TEMPLATE add column PERIOD_KIND varchar(70) ^
update LINEUP_PERIOD_TEMPLATE set PERIOD_KIND = 'eu.crewbase.lineup.entity.period.AttendencePeriod' where PERIOD_KIND is null ;
alter table LINEUP_PERIOD_TEMPLATE modify column PERIOD_KIND varchar(70) ;
