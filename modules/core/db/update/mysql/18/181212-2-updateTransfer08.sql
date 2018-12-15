alter table LINEUP_TRANSFER change column TRANSFER_ID TRANSFER_ID__U86261 varchar(32)^
drop index IDX_LINEUP_TRANSFER_ON_TRANSFER on LINEUP_TRANSFER ;
alter table LINEUP_TRANSFER drop foreign key FK_LINEUP_TRANSFER_ON_TRANSFER;
alter table LINEUP_TRANSFER change column SITE_ID SITE_ID__U52002 varchar(32)^
drop index IDX_LINEUP_TRANSFER_ON_SITE on LINEUP_TRANSFER ;
alter table LINEUP_TRANSFER drop foreign key FK_LINEUP_TRANSFER_ON_SITE;
alter table LINEUP_TRANSFER change column DELETED_BY DELETED_BY__U63562 varchar(50)^
alter table LINEUP_TRANSFER change column DELETE_TS DELETE_TS__U44031 datetime^
alter table LINEUP_TRANSFER change column UPDATED_BY UPDATED_BY__U55659 varchar(50)^
alter table LINEUP_TRANSFER change column UPDATE_TS UPDATE_TS__U64533 datetime^
alter table LINEUP_TRANSFER change column CREATED_BY CREATED_BY__U27183 varchar(50)^
alter table LINEUP_TRANSFER change column CREATE_TS CREATE_TS__U46895 datetime^
alter table LINEUP_TRANSFER change column VERSION VERSION__U49347 int^
alter table LINEUP_TRANSFER modify column VERSION__U49347 int null ;
alter table LINEUP_TRANSFER add column ID varchar(32) ^
update LINEUP_TRANSFER set ID = newid() where ID is null ;
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
