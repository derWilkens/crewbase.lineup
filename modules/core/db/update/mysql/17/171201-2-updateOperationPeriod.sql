alter table LINEUP_OPERATION_PERIOD add column START_DATE datetime(3) ;
alter table LINEUP_OPERATION_PERIOD add column END_DATE datetime(3) ;
alter table LINEUP_OPERATION_PERIOD drop column START_ cascade ;
alter table LINEUP_OPERATION_PERIOD drop column END_ cascade ;
