-- update LINEUP_TRANSFER set CRAFT_TYPE_ID = <default_value> where CRAFT_TYPE_ID is null ;
alter table LINEUP_TRANSFER modify column CRAFT_TYPE_ID varchar(32) not null ;
