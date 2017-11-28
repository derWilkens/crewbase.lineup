alter table LINEUP_USER_IMPORT_STAGE add column FIRST_NAME varchar(20) ;
alter table LINEUP_USER_IMPORT_STAGE add column LAST_NAME varchar(50) ;
alter table LINEUP_USER_IMPORT_STAGE add column DEPARTMENT_ACRONYM varchar(20) ;
alter table LINEUP_USER_IMPORT_STAGE add column JOBTITLE varchar(50) ;
alter table LINEUP_USER_IMPORT_STAGE drop column FIRSTNAME cascade ;
alter table LINEUP_USER_IMPORT_STAGE drop column LASTNAME cascade ;
alter table LINEUP_USER_IMPORT_STAGE drop column DEPARTMENT cascade ;
alter table LINEUP_USER_IMPORT_STAGE drop column POSITION_ cascade ;
