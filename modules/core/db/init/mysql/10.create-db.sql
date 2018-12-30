-- begin LINEUP_APP_USER
create table LINEUP_APP_USER (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    DTYPE varchar(100),
    --
    CLIENT integer,
    LASTNAME varchar(50),
    FIRSTNAME varchar(50),
    EMAIL varchar(100),
    COMPANY_ID varchar(32),
    DEPARTMENT_ID varchar(32),
    --
    -- from lineup$OffshoreUser
    WEIGHT integer,
    WEIGHT_CHANGE_DATE date,
    --
    primary key (ID)
)^
-- end LINEUP_APP_USER
-- begin LINEUP_COMPANY
create table LINEUP_COMPANY (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    COMPANY_NAME varchar(100) not null,
    CONTACT_PERSON varchar(100),
    EMAIL varchar(100) not null,
    --
    primary key (ID)
)^
-- end LINEUP_COMPANY

-- begin LINEUP_CREW_CHANGE
create table LINEUP_CREW_CHANGE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    --
    START_DATE date not null,
    --
    primary key (ID)
)^
-- end LINEUP_CREW_CHANGE
-- begin LINEUP_SITE
create table LINEUP_SITE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    SITE_NAME varchar(50) not null,
    LATITUDE double precision,
    LONGITUDE double precision,
    ITEM_DESIGNATION varchar(7),
    PARENT_SITE_ID varchar(32),
    SHORT_ITEM_DESIGNATION varchar(4),
    CATEGORY_ID varchar(32),
    SITE_TYPE_ID varchar(32),
    --
    primary key (ID)
)^
-- end LINEUP_SITE

-- begin LINEUP_CRAFT_TYPE
create table LINEUP_CRAFT_TYPE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    --
    NAME varchar(50) not null,
    SEATS integer not null,
    MAX_RANGE integer,
    PAYLOAD_OUTBOUND integer,
    PAYLOAD_INBOUND integer,
    MODE_OF_TRANSFER_ID varchar(32),
    --
    primary key (ID)
)^
-- end LINEUP_CRAFT_TYPE
-- begin LINEUP_PAYLOAD
create table LINEUP_PAYLOAD (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    --
    CRAFT_TYPE_ID varchar(32) not null,
    SITE_A_ID varchar(32) not null,
    SITE_B_ID varchar(32) not null,
    PAYLOAD integer not null,
    --
    primary key (ID)
)^
-- end LINEUP_PAYLOAD
-- begin LINEUP_MODE_OF_TRANSFER
create table LINEUP_MODE_OF_TRANSFER (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    MODE_ varchar(50),
    --
    primary key (ID)
)^
-- end LINEUP_MODE_OF_TRANSFER
-- begin LINEUP_SITE_TYPE
create table LINEUP_SITE_TYPE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    --
    TYPE_ varchar(50),
    PARENT_TYPE_ID varchar(32),
    --
    primary key (ID)
)^
-- end LINEUP_SITE_TYPE

-- begin LINEUP_USER_PREFERENCE
create table LINEUP_USER_PREFERENCE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    --
    USER_ID varchar(32) not null,
    ENTITY_UUID varchar(32),
    USER_VALUE varchar(255),
    CONTEXT_ID integer,
    --
    primary key (ID)
)^
-- end LINEUP_USER_PREFERENCE
-- begin LINEUP_PERIOD_TYPE
create table LINEUP_PERIOD_TYPE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    --
    TYPE_NAME varchar(20),
    PARENT_TYPE varchar(50),
    --
    primary key (ID)
)^
-- end LINEUP_PERIOD_TYPE
-- begin LINEUP_FUNCTION_CATEGORY
create table LINEUP_FUNCTION_CATEGORY (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    --
    CATEGORY_NAME varchar(50),
    PARENT_TYPE_ID varchar(32),
    --
    primary key (ID)
)^
-- end LINEUP_FUNCTION_CATEGORY
-- begin LINEUP_DEPARTMENT
create table LINEUP_DEPARTMENT (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    --
    NAME varchar(100),
    PARENT_DEPARTMENT_ID varchar(32),
    LEADER_ID varchar(32),
    DEPUTY_LEADER_ID varchar(32),
    ACRONYM varchar(15),
    --
    primary key (ID)
)^
-- end LINEUP_DEPARTMENT
-- begin LINEUP_PERIOD_IMPORT_STAGE
create table LINEUP_PERIOD_IMPORT_STAGE (
    ID varchar(32),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    DTYPE varchar(31),
    --
    ITEM_DESIGNATION varchar(10),
    CAMPAIGN_NUMBER varchar(10),
    START_DATE date,
    END_DATE date,
    SHUTDOWN_ boolean,
    IMPORT_LOG longtext,
    --
    primary key (ID)
)^
-- end LINEUP_PERIOD_IMPORT_STAGE
-- begin LINEUP_QUALIFICATION_TYPE
create table LINEUP_QUALIFICATION_TYPE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    --
    NAME varchar(255),
    VALIDITY integer,
    --
    primary key (ID)
)^
-- end LINEUP_QUALIFICATION_TYPE
-- begin LINEUP_ROLE
create table LINEUP_ROLE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    --
    NAME varchar(30),
    --
    primary key (ID)
)^
-- end LINEUP_ROLE
-- begin LINEUP_JOBFUNCTION
create table LINEUP_JOBFUNCTION (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    NAME varchar(30),
    --
    primary key (ID)
)^
-- end LINEUP_JOBFUNCTION
-- begin LINEUP_CERTIFICATE
create table LINEUP_CERTIFICATE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    --
    ISSUING_DATE date,
    EXPIRATION_DATE date,
    VERFIED_BY_ID varchar(32),
    QUALIFICATION_TYPE_ID varchar(32),
    APP_USER_ID varchar(32),
    FILE_DATA_ID varchar(32),
    CERTIFICATE_TYPE_ID varchar(32),
    STATE varchar(255),
    --
    primary key (ID)
)^
-- end LINEUP_CERTIFICATE
-- begin LINEUP_USER_IMPORT_STAGE
create table LINEUP_USER_IMPORT_STAGE (
    ID varchar(32),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    DTYPE varchar(31),
    --
    FIRST_NAME varchar(20),
    LAST_NAME varchar(50),
    EMAIL varchar(50),
    DEPARTMENT_ACRONYM varchar(20),
    JOBTITLE varchar(50),
    IMPORT_LOG longtext,
    --
    primary key (ID)
)^
-- end LINEUP_USER_IMPORT_STAGE

-- begin LINEUP_ROLE_QUALIFICATION_TYPE
create table LINEUP_ROLE_QUALIFICATION_TYPE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    --
    ROLE_ID varchar(32),
    QUALIFICATION_TYPE_ID varchar(32) not null,
    MANDATORY boolean,
    --
    primary key (ID)
)^
-- end LINEUP_ROLE_QUALIFICATION_TYPE
-- begin LINEUP_SITE_CATEGORY
create table LINEUP_SITE_CATEGORY (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    NAME varchar(50),
    --
    primary key (ID)
)^
-- end LINEUP_SITE_CATEGORY
-- begin LINEUP_SITE_ROLE_RULE
create table LINEUP_SITE_ROLE_RULE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    SITE_ID varchar(32),
    ROLE_ID varchar(32),
    FUNCTION_CATEGORY_ID varchar(32),
    --
    primary key (ID)
)^
-- end LINEUP_SITE_ROLE_RULE
-- begin LINEUP_NUMBER_RANGE_RULE
create table LINEUP_NUMBER_RANGE_RULE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    AMOUNT_FROM integer,
    AMOUNT_TO integer,
    REQUIRED_NUMBER integer,
    SITE_ROLE_RULE_ID varchar(32),
    --
    primary key (ID)
)^
-- end LINEUP_NUMBER_RANGE_RULE

-- begin LINEUP_ATTENDENCE_PERIOD
create table LINEUP_ATTENDENCE_PERIOD (
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
    COLOR varchar(255),
    PERSON_ON_DUTY_ID varchar(32),
    --
    OPERATION_PERIOD_ID varchar(32),
    --
    primary key (ID)
)^
-- end LINEUP_ATTENDENCE_PERIOD
-- begin LINEUP_ABSENCE_PERIOD
create table LINEUP_ABSENCE_PERIOD (
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
    COLOR varchar(255),
    PERSON_ON_DUTY_ID varchar(32),
    --
    primary key (ID)
)^
-- end LINEUP_ABSENCE_PERIOD
-- begin LINEUP_FUNCTION_ROLE_LINK
create table LINEUP_FUNCTION_ROLE_LINK (
    FUNCTION_ID varchar(32),
    ROLE_ID varchar(32),
    primary key (FUNCTION_ID, ROLE_ID)
)^
-- end LINEUP_FUNCTION_ROLE_LINK
-- begin LINEUP_APP_USER_JOBFUNCTION_LINK
create table LINEUP_APP_USER_JOBFUNCTION_LINK (
    JOBFUNCTION_ID varchar(32),
    APP_USER_ID varchar(32),
    primary key (JOBFUNCTION_ID, APP_USER_ID)
)^
-- end LINEUP_APP_USER_JOBFUNCTION_LINK
-- begin LINEUP_SITE_PERIOD
create table LINEUP_SITE_PERIOD (
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
    COLOR varchar(255),
    --
    SITE_ID varchar(32),
    --
    primary key (ID)
)^
-- end LINEUP_SITE_PERIOD
-- begin LINEUP_PERIOD_TEMPLATE
create table LINEUP_PERIOD_TEMPLATE (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    CLIENT integer not null,
    --
    SITE_ID varchar(32),
    REMARK varchar(255),
    USER_ID varchar(32),
    PERIOD_KIND varchar(70),
    DURATION1 integer,
    DURATION2 integer,
    DURATION3 integer,
    COLOR varchar(255),
    --
    primary key (ID)
)^
-- end LINEUP_PERIOD_TEMPLATE
-- begin LINEUP_SHIFT_PERIOD
create table LINEUP_SHIFT_PERIOD (
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
    COLOR varchar(255),
    --
    PERSON_ON_DUTY_ID varchar(32),
    --
    primary key (ID)
)^
-- end LINEUP_SHIFT_PERIOD
-- begin LINEUP_MAINTENANCE_CAMPAIGN
create table LINEUP_MAINTENANCE_CAMPAIGN (
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
    COLOR varchar(255),
    END_DATE datetime(3),
    REMARK varchar(255),
    SITE_ID varchar(32),
    --
    CAMPAIGN_NUMBER varchar(10),
    --
    primary key (ID)
)^
-- end LINEUP_MAINTENANCE_CAMPAIGN
-- begin LINEUP_OPERATION_PERIOD
create table LINEUP_OPERATION_PERIOD (
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
    COLOR varchar(255),
    SITE_ID varchar(32),
    --
    PARENT_PERIOD_ID varchar(32),
    --
    primary key (ID)
)^
-- end LINEUP_OPERATION_PERIOD
-- begin LINEUP_OUTAGE_PERIOD
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
    COLOR varchar(255),
    END_DATE datetime(3),
    REMARK varchar(255),
    SITE_ID varchar(32),
    --
    CAMPAIGN_NUMBER varchar(10),
    --
    primary key (ID)
)^
-- end LINEUP_OUTAGE_PERIOD
-- begin LINEUP_FIRST_CLASS
create table LINEUP_FIRST_CLASS (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    FIRST_ATTR varchar(255),
    --
    primary key (ID)
)^
-- end LINEUP_FIRST_CLASS
-- begin LINEUP_SECOND_CLASS
create table LINEUP_SECOND_CLASS (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    SEC_ATTR varchar(255),
    FIRST_CLASS_ID varchar(32),
    --
    primary key (ID)
)^
-- end LINEUP_SECOND_CLASS
-- begin LINEUP_THIRD_CLASS
create table LINEUP_THIRD_CLASS (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    THIRD_ATTR varchar(255),
    SECOND_CLASS_ID varchar(32),
    --
    primary key (ID)
)^
-- end LINEUP_THIRD_CLASS
-- begin LINEUP_TRANSFER
create table LINEUP_TRANSFER (
    ID varchar(32),
    --
    TRANSFER_ORDER_NO integer not null,
    ANCHOR_WAYPOINT_ID varchar(32),
    CREW_CHANGE_ID varchar(32),
    OPERATED_BY_ID varchar(32),
    MODE_OF_TRANSFER_ID varchar(32),
    CRAFT_TYPE_ID varchar(32),
    --
    primary key (ID)
)^
-- end LINEUP_TRANSFER
-- begin LINEUP_WAYPOINT
create table LINEUP_WAYPOINT (
    ID varchar(32),
    --
    TAKE_OFF time(3),
    PREVIOUS_STANDSTILL_ID varchar(32),
    STOPOVER_TIME integer,
    --
    primary key (ID)
)^
-- end LINEUP_WAYPOINT
-- begin LINEUP_ANCHOR_WAYPOINT
create table LINEUP_ANCHOR_WAYPOINT (
    ID varchar(32),
    --
    START_DATE_TIME datetime(3),
    --
    primary key (ID)
)^
-- end LINEUP_ANCHOR_WAYPOINT
-- begin LINEUP_STANDSTILL
create table LINEUP_STANDSTILL (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    DTYPE varchar(31),
    --
    SITE_ID varchar(32),
    TRANSFER_ID varchar(32),
    NEXT_WAYPOINT_ID varchar(32),
    --
    primary key (ID)
)^
-- end LINEUP_STANDSTILL
-- begin LINEUP_TICKET
create table LINEUP_TICKET (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    TRANSFER_ID varchar(32) not null,
    START_SITE_ID varchar(32) not null,
    DESTINATION_SITE_ID varchar(32) not null,
    PASSENGER_ID varchar(32),
    --
    primary key (ID)
)^
-- end LINEUP_TICKET

-- begin LINEUP_FAVORITE_TRIP
create table LINEUP_FAVORITE_TRIP (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    START_SITE_ID varchar(32) not null,
    EXACT_MATCH boolean,
    DESTINATION_ID varchar(32) not null,
    EMAIL_NOTIFICATION boolean,
    ROUND_TRIP boolean,
    --
    primary key (ID)
)^
-- end LINEUP_FAVORITE_TRIP
-- begin LINEUP_AIRPORT
create table LINEUP_AIRPORT (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    SITE_NAME varchar(50) not null,
    LATITUDE double precision,
    LONGITUDE double precision,
    ITEM_DESIGNATION varchar(7),
    PARENT_SITE_ID varchar(32),
    SHORT_ITEM_DESIGNATION varchar(4),
    CATEGORY_ID varchar(32),
    SITE_TYPE_ID varchar(32),
    --
    ICAO_CODE varchar(4),
    IATA_CODE varchar(4),
    --
    primary key (ID)
)^
-- end LINEUP_AIRPORT
