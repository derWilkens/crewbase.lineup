alter table LINEUP_MAINTENANCE_CAMPAIGN add column START_DATE datetime(3) ;
alter table LINEUP_MAINTENANCE_CAMPAIGN add column END_DATE datetime(3) ;
alter table LINEUP_MAINTENANCE_CAMPAIGN drop column START_ cascade ;
alter table LINEUP_MAINTENANCE_CAMPAIGN drop column END_ cascade ;
