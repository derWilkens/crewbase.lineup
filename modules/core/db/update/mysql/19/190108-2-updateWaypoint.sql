alter table LINEUP_WAYPOINT add column SITE_ID varchar(32) ;
alter table LINEUP_WAYPOINT add column TRANSFER_ID varchar(32) ;
alter table LINEUP_WAYPOINT add column NEXT_WAYPOINT_ID varchar(32) ;
alter table LINEUP_WAYPOINT add column TRANSFER_ORDER_NO integer ^
update LINEUP_WAYPOINT set TRANSFER_ORDER_NO = 0 where TRANSFER_ORDER_NO is null ;
alter table LINEUP_WAYPOINT modify column TRANSFER_ORDER_NO integer not null ;
alter table LINEUP_WAYPOINT add column HEAD_WAYPOINT_ID varchar(32) ;
alter table LINEUP_WAYPOINT add column CREW_CHANGE_ID varchar(32) ;
alter table LINEUP_WAYPOINT add column OPERATED_BY_ID varchar(32) ;
alter table LINEUP_WAYPOINT add column MODE_OF_TRANSFER_ID varchar(32) ;
alter table LINEUP_WAYPOINT add column CRAFT_TYPE_ID varchar(32) ;
alter table LINEUP_WAYPOINT add column VERSION integer ^
update LINEUP_WAYPOINT set VERSION = 0 where VERSION is null ;
alter table LINEUP_WAYPOINT modify column VERSION integer not null ;
alter table LINEUP_WAYPOINT add column CREATE_TS datetime(3) ;
alter table LINEUP_WAYPOINT add column CREATED_BY varchar(50) ;
alter table LINEUP_WAYPOINT add column UPDATE_TS datetime(3) ;
alter table LINEUP_WAYPOINT add column UPDATED_BY varchar(50) ;
alter table LINEUP_WAYPOINT add column DELETE_TS datetime(3) ;
alter table LINEUP_WAYPOINT add column DELETED_BY varchar(50) ;
alter table LINEUP_WAYPOINT add column DTYPE varchar(100) ;
