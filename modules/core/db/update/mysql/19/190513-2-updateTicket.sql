alter table LINEUP_TICKET change column DESTINATION_SITE_ID DESTINATION_SITE_ID__U68512 varchar(32)^
alter table LINEUP_TICKET modify column DESTINATION_SITE_ID__U68512 varchar(32) null ;
drop index IDX_LINEUP_TICKET_ON_DESTINATION_SITE on LINEUP_TICKET ;
alter table LINEUP_TICKET drop foreign key FK_LINEUP_TICKET_ON_DESTINATION_SITE;
alter table LINEUP_TICKET change column START_SITE_ID START_SITE_ID__U48845 varchar(32)^
alter table LINEUP_TICKET modify column START_SITE_ID__U48845 varchar(32) null ;
drop index IDX_LINEUP_TICKET_ON_START_SITE on LINEUP_TICKET ;
alter table LINEUP_TICKET drop foreign key FK_LINEUP_TICKET_ON_START_SITE;
alter table LINEUP_TICKET add column START_WAYPOINT_ID varchar(32) ;
alter table LINEUP_TICKET add column DESTINATION_WAYPOINT_ID varchar(32) ;
