create table LINEUP_OUTAGE_PERIOD (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    START_DATE datetime(3),
    END_DATE datetime(3),
    REMARK varchar(255),
    SITE_ID varchar(32),
    --
    CAMPAIGN_NUMBER varchar(10),
    --
    primary key (ID)
);
