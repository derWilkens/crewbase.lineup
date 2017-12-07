alter table LINEUP_PERIOD_TEMPLATE add column PERIOD_KIND varchar(50) ;
alter table LINEUP_PERIOD_TEMPLATE drop column FUNCTION_CATEGORY_ID cascade ;
