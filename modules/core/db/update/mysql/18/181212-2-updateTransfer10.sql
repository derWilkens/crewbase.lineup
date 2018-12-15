alter table LINEUP_TRANSFER add constraint FK_LINEUP_TRANSFER_ON_TRANSFER foreign key (TRANSFER_ID) references LINEUP_TRANSFER(ID);
