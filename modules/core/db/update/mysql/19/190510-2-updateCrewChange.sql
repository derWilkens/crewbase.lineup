alter table LINEUP_CREW_CHANGE change column START_DATE START_DATE__U06761 date^
alter table LINEUP_CREW_CHANGE modify column START_DATE__U06761 date null ;
alter table LINEUP_CREW_CHANGE add column START_DATE datetime(3) ^
update LINEUP_CREW_CHANGE set START_DATE = current_date where START_DATE is null ;
alter table LINEUP_CREW_CHANGE modify column START_DATE datetime(3) not null ;
