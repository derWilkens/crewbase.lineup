alter table LINEUP_TRANSFER modify column ID varchar(32) not null ;
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
alter table LINEUP_TRANSFER add column TRANSFER_ID varchar(32) ;
alter table LINEUP_TRANSFER add column NEXT_WAYPOINT_ID varchar(32) ;
