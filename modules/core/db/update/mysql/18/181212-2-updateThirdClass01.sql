alter table LINEUP_THIRD_CLASS add constraint FK_LINEUP_THIRD_CLASS_ON_SECOND_CLASS foreign key (SECOND_CLASS_ID) references LINEUP_SECOND_CLASS(ID);
create index IDX_LINEUP_THIRD_CLASS_ON_SECOND_CLASS on LINEUP_THIRD_CLASS (SECOND_CLASS_ID);
