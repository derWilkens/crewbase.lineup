alter table LINEUP_AIRPORT change column SITE_TYPE_ID SITE_TYPE_ID__U10105 varchar(32)^
drop index IDX_LINEUP_AIRPORT_ON_SITE_TYPE on LINEUP_AIRPORT ;
alter table LINEUP_AIRPORT drop foreign key FK_LINEUP_AIRPORT_ON_SITE_TYPE;
alter table LINEUP_AIRPORT change column SHORT_ITEM_DESIGNATION SHORT_ITEM_DESIGNATION__U37857 varchar(4)^
alter table LINEUP_AIRPORT change column PARENT_SITE_ID PARENT_SITE_ID__U02855 varchar(32)^
drop index IDX_LINEUP_AIRPORT_ON_PARENT_SITE on LINEUP_AIRPORT ;
alter table LINEUP_AIRPORT drop foreign key FK_LINEUP_AIRPORT_ON_PARENT_SITE;
alter table LINEUP_AIRPORT change column ITEM_DESIGNATION ITEM_DESIGNATION__U77497 varchar(7)^
alter table LINEUP_AIRPORT change column SITE_CATEGORY_ID SITE_CATEGORY_ID__U36237 varchar(32)^
drop index IDX_LINEUP_AIRPORT_ON_SITE_CATEGORY on LINEUP_AIRPORT ;
alter table LINEUP_AIRPORT drop foreign key FK_LINEUP_AIRPORT_ON_SITE_CATEGORY;
alter table LINEUP_AIRPORT change column SITE_NAME SITE_NAME__U02377 varchar(50)^
alter table LINEUP_AIRPORT modify column SITE_NAME__U02377 varchar(50) null ;
alter table LINEUP_AIRPORT add column LOCATION_NAME varchar(50) ;
alter table LINEUP_AIRPORT add column CATEGORY_ID varchar(32) ;
