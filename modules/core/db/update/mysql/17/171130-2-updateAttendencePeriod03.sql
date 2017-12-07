alter table LINEUP_ATTENDENCE_PERIOD drop column OPERATION_PERIOD_ID cascade ;
alter table LINEUP_ATTENDENCE_PERIOD add column OPERATION_PERIOD_ID varchar(32) ;
