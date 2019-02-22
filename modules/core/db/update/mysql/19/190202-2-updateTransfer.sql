alter table LINEUP_TRANSFER add column CLIENT integer ^
update LINEUP_TRANSFER set CLIENT = 0 where CLIENT is null ;
alter table LINEUP_TRANSFER modify column CLIENT integer not null ;
