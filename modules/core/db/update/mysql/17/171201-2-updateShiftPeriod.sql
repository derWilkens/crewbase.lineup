alter table LINEUP_SHIFT_PERIOD add column START_DATE datetime(3) ;
alter table LINEUP_SHIFT_PERIOD add column END_DATE datetime(3) ;
alter table LINEUP_SHIFT_PERIOD drop column START_ cascade ;
alter table LINEUP_SHIFT_PERIOD drop column END_ cascade ;
