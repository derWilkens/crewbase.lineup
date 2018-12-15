alter table LINEUP_WAYPOINT change column TRANSFER_ID TRANSFER_ID__U94257 varchar(32)^
drop index IDX_LINEUP_WAYPOINT_TRANSFER on LINEUP_WAYPOINT ;
alter table LINEUP_WAYPOINT drop foreign key FK_LINEUP_WAYPOINT_TRANSFER;
alter table LINEUP_WAYPOINT change column CLIENT CLIENT__U47565 int^
alter table LINEUP_WAYPOINT modify column CLIENT__U47565 int null ;
alter table LINEUP_WAYPOINT change column DELETED_BY DELETED_BY__U51757 varchar(50)^
alter table LINEUP_WAYPOINT change column DELETE_TS DELETE_TS__U33697 datetime^
alter table LINEUP_WAYPOINT change column UPDATED_BY UPDATED_BY__U05222 varchar(50)^
alter table LINEUP_WAYPOINT change column UPDATE_TS UPDATE_TS__U95118 datetime^
alter table LINEUP_WAYPOINT change column CREATED_BY CREATED_BY__U22463 varchar(50)^
alter table LINEUP_WAYPOINT change column CREATE_TS CREATE_TS__U46998 datetime^
alter table LINEUP_WAYPOINT change column VERSION VERSION__U52145 int^
alter table LINEUP_WAYPOINT modify column VERSION__U52145 int null ;
alter table LINEUP_WAYPOINT add column PREVIOUS_STANDSTILL_ID varchar(32) ;
alter table LINEUP_WAYPOINT add column ID varchar(32) ^
update LINEUP_WAYPOINT set ID = newid() where ID is null ;
alter table LINEUP_WAYPOINT modify column ID varchar(32) not null ;
alter table LINEUP_WAYPOINT add column VERSION integer ^
update LINEUP_WAYPOINT set VERSION = 0 where VERSION is null ;
alter table LINEUP_WAYPOINT modify column VERSION integer not null ;
alter table LINEUP_WAYPOINT add column CREATE_TS datetime(3) ;
alter table LINEUP_WAYPOINT add column CREATED_BY varchar(50) ;
alter table LINEUP_WAYPOINT add column UPDATE_TS datetime(3) ;
alter table LINEUP_WAYPOINT add column UPDATED_BY varchar(50) ;
alter table LINEUP_WAYPOINT add column DELETE_TS datetime(3) ;
alter table LINEUP_WAYPOINT add column DELETED_BY varchar(50) ;
alter table LINEUP_WAYPOINT add column SITE_ID varchar(32) ;
alter table LINEUP_WAYPOINT add column TRANSFER_ID varchar(32) ;
alter table LINEUP_WAYPOINT add column NEXT_WAYPOINT_ID varchar(32) ;
