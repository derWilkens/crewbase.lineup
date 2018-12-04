-- update LINEUP_TRANSFER set CREW_CHANGE_ID = <default_value> where CREW_CHANGE_ID is null ;
alter table LINEUP_TRANSFER modify column CREW_CHANGE_ID varchar(32) not null ;
