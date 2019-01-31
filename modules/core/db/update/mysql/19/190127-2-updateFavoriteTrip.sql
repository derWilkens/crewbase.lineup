alter table LINEUP_FAVORITE_TRIP add column CLIENT integer ^
update LINEUP_FAVORITE_TRIP set CLIENT = 0 where CLIENT is null ;
alter table LINEUP_FAVORITE_TRIP modify column CLIENT integer not null ;
