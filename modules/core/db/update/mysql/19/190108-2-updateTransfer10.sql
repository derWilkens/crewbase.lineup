alter table LINEUP_TRANSFER add constraint FK_LINEUP_TRANSFER_ON_SITE foreign key (SITE_ID) references LINEUP_SITE(ID);
