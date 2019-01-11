alter table LINEUP_TRANSFER change column ANCHOR_WAYPOINT_ID ANCHOR_WAYPOINT_ID__U92561 varchar(32)^
drop index IDX_LINEUP_TRANSFER_ON_ANCHOR_WAYPOINT on LINEUP_TRANSFER ;
alter table LINEUP_TRANSFER add column HEAD_WAYPOINT_ID varchar(32) ;
alter table LINEUP_TRANSFER add column VERSION integer ^
update LINEUP_TRANSFER set VERSION = 0 where VERSION is null ;
alter table LINEUP_TRANSFER modify column VERSION integer not null ;
alter table LINEUP_TRANSFER add column CREATE_TS datetime(3) ;
alter table LINEUP_TRANSFER add column CREATED_BY varchar(50) ;
alter table LINEUP_TRANSFER add column UPDATE_TS datetime(3) ;
alter table LINEUP_TRANSFER add column UPDATED_BY varchar(50) ;
alter table LINEUP_TRANSFER add column DELETE_TS datetime(3) ;
alter table LINEUP_TRANSFER add column DELETED_BY varchar(50) ;
alter table LINEUP_TRANSFER add column SITE_ID varchar(32) ;
alter table LINEUP_TRANSFER add column TAKE_OFF time(3) ;
alter table LINEUP_TRANSFER add column STOPOVER_TIME integer ;
alter table LINEUP_TRANSFER add column TRANSFER_ID varchar(32) ;
alter table LINEUP_TRANSFER add column NEXT_WAYPOINT_ID varchar(32) ;
