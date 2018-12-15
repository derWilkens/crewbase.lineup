rename table lineup_transfer to LINEUP_TRANSFER__U54652 ;
alter table lineup_anchor_waypoint drop foreign key FK_LINEUP_ANCHOR_WAYPOINT_ON_TRANSFER;
alter table lineup_standstill drop foreign key FK_LINEUP_STANDSTILL_ON_TRANSFER;
alter table lineup_transfer drop foreign key FK_LINEUP_TRANSFER_ON_TRANSFER;
alter table lineup_waypoint drop foreign key FK_LINEUP_WAYPOINT_ON_TRANSFER;
