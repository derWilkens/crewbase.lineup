alter table LINEUP_CREW_CHANGE change column FLIGHT_DATE FLIGHT_DATE__U75000 date^
alter table LINEUP_CREW_CHANGE modify column FLIGHT_DATE__U75000 date null ;
alter table LINEUP_CREW_CHANGE add column START_DATE date ^
update LINEUP_CREW_CHANGE set START_DATE = current_date where START_DATE is null ;
alter table LINEUP_CREW_CHANGE modify column START_DATE date not null ;
