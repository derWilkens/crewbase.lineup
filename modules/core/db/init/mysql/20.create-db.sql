-- begin LINEUP_APP_USER
alter table LINEUP_APP_USER add constraint FK_LINEUP_APP_USER_COMPANY foreign key (COMPANY_ID) references LINEUP_COMPANY(ID)^
alter table LINEUP_APP_USER add constraint FK_LINEUP_APP_USER_DEPARTMENT foreign key (DEPARTMENT_ID) references LINEUP_DEPARTMENT(ID)^
create index IDX_LINEUP_APP_USER_COMPANY on LINEUP_APP_USER (COMPANY_ID)^
create index IDX_LINEUP_APP_USER_DEPARTMENT on LINEUP_APP_USER (DEPARTMENT_ID)^
-- end LINEUP_APP_USER

-- begin LINEUP_SITE
alter table LINEUP_SITE add constraint FK_LINEUP_SITE_ON_SITE_CATEGORY foreign key (SITE_CATEGORY_ID) references LINEUP_SITE_CATEGORY(ID)^
alter table LINEUP_SITE add constraint FK_LINEUP_SITE_ON_PARENT_SITE foreign key (PARENT_SITE_ID) references LINEUP_SITE(ID)^
alter table LINEUP_SITE add constraint FK_LINEUP_SITE_ON_SITE_TYPE foreign key (SITE_TYPE_ID) references LINEUP_SITE_TYPE(ID)^
create index IDX_LINEUP_SITE_ON_SITE_CATEGORY on LINEUP_SITE (SITE_CATEGORY_ID)^
create index IDX_LINEUP_SITE_ON_PARENT_SITE on LINEUP_SITE (PARENT_SITE_ID)^
create index IDX_LINEUP_SITE_ON_SITE_TYPE on LINEUP_SITE (SITE_TYPE_ID)^
-- end LINEUP_SITE

-- begin LINEUP_CRAFT_TYPE
alter table LINEUP_CRAFT_TYPE add constraint FK_LINEUP_CRAFT_TYPE_ON_MODE_OF_TRANSFER foreign key (MODE_OF_TRANSFER_ID) references LINEUP_MODE_OF_TRANSFER(ID)^
create index IDX_LINEUP_CRAFT_TYPE_ON_MODE_OF_TRANSFER on LINEUP_CRAFT_TYPE (MODE_OF_TRANSFER_ID)^
-- end LINEUP_CRAFT_TYPE
-- begin LINEUP_PAYLOAD
alter table LINEUP_PAYLOAD add constraint FK_LINEUP_PAYLOAD_ON_CRAFT_TYPE foreign key (CRAFT_TYPE_ID) references LINEUP_CRAFT_TYPE(ID)^
alter table LINEUP_PAYLOAD add constraint FK_LINEUP_PAYLOAD_ON_SITE_A foreign key (SITE_A_ID) references LINEUP_SITE(ID)^
alter table LINEUP_PAYLOAD add constraint FK_LINEUP_PAYLOAD_ON_SITE_B foreign key (SITE_B_ID) references LINEUP_SITE(ID)^
create index IDX_LINEUP_PAYLOAD_ON_CRAFT_TYPE on LINEUP_PAYLOAD (CRAFT_TYPE_ID)^
create index IDX_LINEUP_PAYLOAD_ON_SITE_A on LINEUP_PAYLOAD (SITE_A_ID)^
create index IDX_LINEUP_PAYLOAD_ON_SITE_B on LINEUP_PAYLOAD (SITE_B_ID)^
-- end LINEUP_PAYLOAD
-- begin LINEUP_SITE_TYPE
alter table LINEUP_SITE_TYPE add constraint FK_LINEUP_SITE_TYPE_ON_PARENT_TYPE foreign key (PARENT_TYPE_ID) references LINEUP_SITE_TYPE(ID)^
create index IDX_LINEUP_SITE_TYPE_ON_PARENT_TYPE on LINEUP_SITE_TYPE (PARENT_TYPE_ID)^
-- end LINEUP_SITE_TYPE

-- begin LINEUP_FUNCTION_CATEGORY
alter table LINEUP_FUNCTION_CATEGORY add constraint FK_LINEUP_FUNCTION_CATEGORY_ON_PARENT_TYPE foreign key (PARENT_TYPE_ID) references LINEUP_FUNCTION_CATEGORY(ID)^
create index IDX_LINEUP_FUNCTION_CATEGORY_ON_PARENT_TYPE on LINEUP_FUNCTION_CATEGORY (PARENT_TYPE_ID)^
-- end LINEUP_FUNCTION_CATEGORY
-- begin LINEUP_DEPARTMENT
alter table LINEUP_DEPARTMENT add constraint FK_LINEUP_DEPARTMENT_ON_PARENT_DEPARTMENT foreign key (PARENT_DEPARTMENT_ID) references LINEUP_DEPARTMENT(ID)^
alter table LINEUP_DEPARTMENT add constraint FK_LINEUP_DEPARTMENT_ON_LEADER foreign key (LEADER_ID) references LINEUP_APP_USER(ID)^
alter table LINEUP_DEPARTMENT add constraint FK_LINEUP_DEPARTMENT_ON_DEPUTY_LEADER foreign key (DEPUTY_LEADER_ID) references LINEUP_APP_USER(ID)^
create index IDX_LINEUP_DEPARTMENT_ON_PARENT_DEPARTMENT on LINEUP_DEPARTMENT (PARENT_DEPARTMENT_ID)^
create index IDX_LINEUP_DEPARTMENT_ON_LEADER on LINEUP_DEPARTMENT (LEADER_ID)^
create index IDX_LINEUP_DEPARTMENT_ON_DEPUTY_LEADER on LINEUP_DEPARTMENT (DEPUTY_LEADER_ID)^
-- end LINEUP_DEPARTMENT
-- begin LINEUP_CERTIFICATE
alter table LINEUP_CERTIFICATE add constraint FK_LINEUP_CERTIFICATE_ON_VERFIED_BY foreign key (VERFIED_BY_ID) references SEC_USER(ID)^
alter table LINEUP_CERTIFICATE add constraint FK_LINEUP_CERTIFICATE_ON_QUALIFICATION_TYPE foreign key (QUALIFICATION_TYPE_ID) references LINEUP_QUALIFICATION_TYPE(ID)^
alter table LINEUP_CERTIFICATE add constraint FK_LINEUP_CERTIFICATE_ON_APP_USER foreign key (APP_USER_ID) references LINEUP_APP_USER(ID)^
alter table LINEUP_CERTIFICATE add constraint FK_LINEUP_CERTIFICATE_ON_FILE_DATA foreign key (FILE_DATA_ID) references SYS_FILE(ID)^
alter table LINEUP_CERTIFICATE add constraint FK_LINEUP_CERTIFICATE_ON_CERTIFICATE_TYPE foreign key (CERTIFICATE_TYPE_ID) references LINEUP_QUALIFICATION_TYPE(ID)^
create index IDX_LINEUP_CERTIFICATE_ON_VERFIED_BY on LINEUP_CERTIFICATE (VERFIED_BY_ID)^
create index IDX_LINEUP_CERTIFICATE_ON_QUALIFICATION_TYPE on LINEUP_CERTIFICATE (QUALIFICATION_TYPE_ID)^
create index IDX_LINEUP_CERTIFICATE_ON_APP_USER on LINEUP_CERTIFICATE (APP_USER_ID)^
create index IDX_LINEUP_CERTIFICATE_ON_FILE_DATA on LINEUP_CERTIFICATE (FILE_DATA_ID)^
create index IDX_LINEUP_CERTIFICATE_ON_CERTIFICATE_TYPE on LINEUP_CERTIFICATE (CERTIFICATE_TYPE_ID)^
-- end LINEUP_CERTIFICATE

-- begin LINEUP_ROLE_QUALIFICATION_TYPE
alter table LINEUP_ROLE_QUALIFICATION_TYPE add constraint FK_LINEUP_ROLE_QUALIFICATION_TYPE_ON_ROLE foreign key (ROLE_ID) references LINEUP_ROLE(ID)^
alter table LINEUP_ROLE_QUALIFICATION_TYPE add constraint FK_LINEUP_ROLE_QUALIFICATION_TYPE_ON_QUALIFICATION_TYPE foreign key (QUALIFICATION_TYPE_ID) references LINEUP_QUALIFICATION_TYPE(ID)^
create index IDX_LINEUP_ROLE_QUALIFICATION_TYPE_ON_ROLE on LINEUP_ROLE_QUALIFICATION_TYPE (ROLE_ID)^
create index IDX_LINEUP_ROLE_QUALIFICATION_TYPE_ON_QUALIFICATION_TYPE on LINEUP_ROLE_QUALIFICATION_TYPE (QUALIFICATION_TYPE_ID)^
-- end LINEUP_ROLE_QUALIFICATION_TYPE
-- begin LINEUP_SITE_ROLE_RULE
alter table LINEUP_SITE_ROLE_RULE add constraint FK_LINEUP_SITE_ROLE_RULE_ON_SITE foreign key (SITE_ID) references LINEUP_SITE(ID)^
alter table LINEUP_SITE_ROLE_RULE add constraint FK_LINEUP_SITE_ROLE_RULE_ON_ROLE foreign key (ROLE_ID) references LINEUP_ROLE(ID)^
alter table LINEUP_SITE_ROLE_RULE add constraint FK_LINEUP_SITE_ROLE_RULE_ON_FUNCTION_CATEGORY foreign key (FUNCTION_CATEGORY_ID) references LINEUP_FUNCTION_CATEGORY(ID)^
create index IDX_LINEUP_SITE_ROLE_RULE_ON_SITE on LINEUP_SITE_ROLE_RULE (SITE_ID)^
create index IDX_LINEUP_SITE_ROLE_RULE_ON_ROLE on LINEUP_SITE_ROLE_RULE (ROLE_ID)^
create index IDX_LINEUP_SITE_ROLE_RULE_ON_FUNCTION_CATEGORY on LINEUP_SITE_ROLE_RULE (FUNCTION_CATEGORY_ID)^
-- end LINEUP_SITE_ROLE_RULE
-- begin LINEUP_NUMBER_RANGE_RULE
alter table LINEUP_NUMBER_RANGE_RULE add constraint FK_LINEUP_NUMBER_RANGE_RULE_ON_SITE_ROLE_RULE foreign key (SITE_ROLE_RULE_ID) references LINEUP_SITE_ROLE_RULE(ID)^
create index IDX_LINEUP_NUMBER_RANGE_RULE_ON_SITE_ROLE_RULE on LINEUP_NUMBER_RANGE_RULE (SITE_ROLE_RULE_ID)^
-- end LINEUP_NUMBER_RANGE_RULE

-- begin LINEUP_ATTENDENCE_PERIOD
alter table LINEUP_ATTENDENCE_PERIOD add constraint FK_LINEUP_ATTENDENCE_PERIOD_ON_PERSON_ON_DUTY foreign key (PERSON_ON_DUTY_ID) references LINEUP_APP_USER(ID)^
alter table LINEUP_ATTENDENCE_PERIOD add constraint FK_LINEUP_ATTENDENCE_PERIOD_ON_OPERATION_PERIOD foreign key (OPERATION_PERIOD_ID) references LINEUP_OPERATION_PERIOD(ID)^
create index IDX_LINEUP_ATTENDENCE_PERIOD_ON_PERSON_ON_DUTY on LINEUP_ATTENDENCE_PERIOD (PERSON_ON_DUTY_ID)^
create index IDX_LINEUP_ATTENDENCE_PERIOD_ON_OPERATION_PERIOD on LINEUP_ATTENDENCE_PERIOD (OPERATION_PERIOD_ID)^
-- end LINEUP_ATTENDENCE_PERIOD

-- begin LINEUP_FUNCTION_ROLE_LINK
alter table LINEUP_FUNCTION_ROLE_LINK add constraint FK_FUNROL_JOBFUNCTION foreign key (FUNCTION_ID) references LINEUP_JOBFUNCTION(ID)^
alter table LINEUP_FUNCTION_ROLE_LINK add constraint FK_FUNROL_ROLE foreign key (ROLE_ID) references LINEUP_ROLE(ID)^
-- end LINEUP_FUNCTION_ROLE_LINK
-- begin LINEUP_APP_USER_JOBFUNCTION_LINK
alter table LINEUP_APP_USER_JOBFUNCTION_LINK add constraint FK_APPUSEJOB_JOBFUNCTION foreign key (JOBFUNCTION_ID) references LINEUP_JOBFUNCTION(ID)^
alter table LINEUP_APP_USER_JOBFUNCTION_LINK add constraint FK_APPUSEJOB_APP_USER foreign key (APP_USER_ID) references LINEUP_APP_USER(ID)^
-- end LINEUP_APP_USER_JOBFUNCTION_LINK
-- begin LINEUP_SITE_PERIOD
alter table LINEUP_SITE_PERIOD add constraint FK_LINEUP_SITE_PERIOD_ON_SITE foreign key (SITE_ID) references LINEUP_SITE(ID)^
create index IDX_LINEUP_SITE_PERIOD_ON_SITE on LINEUP_SITE_PERIOD (SITE_ID)^
-- end LINEUP_SITE_PERIOD
-- begin LINEUP_PERIOD_TEMPLATE
alter table LINEUP_PERIOD_TEMPLATE add constraint FK_LINEUP_PERIOD_TEMPLATE_ON_SITE foreign key (SITE_ID) references LINEUP_SITE(ID)^
alter table LINEUP_PERIOD_TEMPLATE add constraint FK_LINEUP_PERIOD_TEMPLATE_ON_USER foreign key (USER_ID) references SEC_USER(ID)^
create index IDX_LINEUP_PERIOD_TEMPLATE_ON_SITE on LINEUP_PERIOD_TEMPLATE (SITE_ID)^
create index IDX_LINEUP_PERIOD_TEMPLATE_ON_USER on LINEUP_PERIOD_TEMPLATE (USER_ID)^
-- end LINEUP_PERIOD_TEMPLATE
-- begin LINEUP_SHIFT_PERIOD
alter table LINEUP_SHIFT_PERIOD add constraint FK_LINEUP_SHIFT_PERIOD_ON_PERSON_ON_DUTY foreign key (PERSON_ON_DUTY_ID) references LINEUP_APP_USER(ID)^
create index IDX_LINEUP_SHIFT_PERIOD_ON_PERSON_ON_DUTY on LINEUP_SHIFT_PERIOD (PERSON_ON_DUTY_ID)^
-- end LINEUP_SHIFT_PERIOD
-- begin LINEUP_MAINTENANCE_CAMPAIGN
alter table LINEUP_MAINTENANCE_CAMPAIGN add constraint FK_LINEUP_MAINTENANCE_CAMPAIGN_SITE foreign key (SITE_ID) references LINEUP_SITE(ID)^
create index IDX_LINEUP_MAINTENANCE_CAMPAIGN_SITE on LINEUP_MAINTENANCE_CAMPAIGN (SITE_ID)^
-- end LINEUP_MAINTENANCE_CAMPAIGN
-- begin LINEUP_OPERATION_PERIOD
alter table LINEUP_OPERATION_PERIOD add constraint FK_LINEUP_OPERATION_PERIOD_ON_SITE foreign key (SITE_ID) references LINEUP_SITE(ID)^
alter table LINEUP_OPERATION_PERIOD add constraint FK_LINEUP_OPERATION_PERIOD_ON_PARENT_PERIOD foreign key (PARENT_PERIOD_ID) references LINEUP_OPERATION_PERIOD(ID)^
create index IDX_LINEUP_OPERATION_PERIOD_ON_SITE on LINEUP_OPERATION_PERIOD (SITE_ID)^
create index IDX_LINEUP_OPERATION_PERIOD_ON_PARENT_PERIOD on LINEUP_OPERATION_PERIOD (PARENT_PERIOD_ID)^
-- end LINEUP_OPERATION_PERIOD
-- begin LINEUP_ABSENCE_PERIOD
alter table LINEUP_ABSENCE_PERIOD add constraint FK_LINEUP_ABSENCE_PERIOD_PERSON_ON_DUTY foreign key (PERSON_ON_DUTY_ID) references LINEUP_APP_USER(ID)^
create index IDX_LINEUP_ABSENCE_PERIOD_PERSON_ON_DUTY on LINEUP_ABSENCE_PERIOD (PERSON_ON_DUTY_ID)^
-- end LINEUP_ABSENCE_PERIOD
-- begin LINEUP_OUTAGE_PERIOD
alter table LINEUP_OUTAGE_PERIOD add constraint FK_LINEUP_OUTAGE_PERIOD_SITE foreign key (SITE_ID) references LINEUP_SITE(ID)^
create index IDX_LINEUP_OUTAGE_PERIOD_SITE on LINEUP_OUTAGE_PERIOD (SITE_ID)^
-- end LINEUP_OUTAGE_PERIOD
-- begin LINEUP_SECOND_CLASS
alter table LINEUP_SECOND_CLASS add constraint FK_LINEUP_SECOND_CLASS_ON_FIRST_CLASS foreign key (FIRST_CLASS_ID) references LINEUP_FIRST_CLASS(ID)^
create index IDX_LINEUP_SECOND_CLASS_ON_FIRST_CLASS on LINEUP_SECOND_CLASS (FIRST_CLASS_ID)^
-- end LINEUP_SECOND_CLASS
-- begin LINEUP_THIRD_CLASS
alter table LINEUP_THIRD_CLASS add constraint FK_LINEUP_THIRD_CLASS_ON_SECOND_CLASS foreign key (SECOND_CLASS_ID) references LINEUP_SECOND_CLASS(ID)^
create index IDX_LINEUP_THIRD_CLASS_ON_SECOND_CLASS on LINEUP_THIRD_CLASS (SECOND_CLASS_ID)^
-- end LINEUP_THIRD_CLASS
-- begin LINEUP_TRANSFER
alter table LINEUP_TRANSFER add constraint FK_LINEUP_TRANSFER_ON_CREW_CHANGE foreign key (CREW_CHANGE_ID) references LINEUP_CREW_CHANGE(ID)^
alter table LINEUP_TRANSFER add constraint FK_LINEUP_TRANSFER_ON_OPERATED_BY foreign key (OPERATED_BY_ID) references LINEUP_COMPANY(ID)^
alter table LINEUP_TRANSFER add constraint FK_LINEUP_TRANSFER_ON_MODE_OF_TRANSFER foreign key (MODE_OF_TRANSFER_ID) references LINEUP_MODE_OF_TRANSFER(ID)^
alter table LINEUP_TRANSFER add constraint FK_LINEUP_TRANSFER_ON_CRAFT_TYPE foreign key (CRAFT_TYPE_ID) references LINEUP_CRAFT_TYPE(ID)^
create index IDX_LINEUP_TRANSFER_ON_CREW_CHANGE on LINEUP_TRANSFER (CREW_CHANGE_ID)^
create index IDX_LINEUP_TRANSFER_ON_OPERATED_BY on LINEUP_TRANSFER (OPERATED_BY_ID)^
create index IDX_LINEUP_TRANSFER_ON_MODE_OF_TRANSFER on LINEUP_TRANSFER (MODE_OF_TRANSFER_ID)^
create index IDX_LINEUP_TRANSFER_ON_CRAFT_TYPE on LINEUP_TRANSFER (CRAFT_TYPE_ID)^
-- end LINEUP_TRANSFER
-- begin LINEUP_WAYPOINT
alter table LINEUP_WAYPOINT add constraint FK_LINEUP_WAYPOINT_ON_SITE foreign key (SITE_ID) references LINEUP_SITE(ID)^
alter table LINEUP_WAYPOINT add constraint FK_LINEUP_WAYPOINT_ON_TRANSFER foreign key (TRANSFER_ID) references LINEUP_TRANSFER(ID)^
create index IDX_LINEUP_WAYPOINT_ON_SITE on LINEUP_WAYPOINT (SITE_ID)^
create index IDX_LINEUP_WAYPOINT_ON_TRANSFER on LINEUP_WAYPOINT (TRANSFER_ID)^
-- end LINEUP_WAYPOINT

-- begin LINEUP_TICKET
alter table LINEUP_TICKET add constraint FK_LINEUP_TICKET_ON_TRANSFER foreign key (TRANSFER_ID) references LINEUP_TRANSFER(ID)^
alter table LINEUP_TICKET add constraint FK_LINEUP_TICKET_ON_START_SITE foreign key (START_SITE_ID) references LINEUP_SITE(ID)^
alter table LINEUP_TICKET add constraint FK_LINEUP_TICKET_ON_DESTINATION_SITE foreign key (DESTINATION_SITE_ID) references LINEUP_SITE(ID)^
alter table LINEUP_TICKET add constraint FK_LINEUP_TICKET_ON_PASSENGER foreign key (PASSENGER_ID) references LINEUP_APP_USER(ID)^
alter table LINEUP_TICKET add constraint FK_LINEUP_TICKET_ON_TRAVEL_OPTION foreign key (TRAVEL_OPTION_ID) references LINEUP_TRAVEL_OPTION(ID)^
create index IDX_LINEUP_TICKET_ON_TRANSFER on LINEUP_TICKET (TRANSFER_ID)^
create index IDX_LINEUP_TICKET_ON_START_SITE on LINEUP_TICKET (START_SITE_ID)^
create index IDX_LINEUP_TICKET_ON_DESTINATION_SITE on LINEUP_TICKET (DESTINATION_SITE_ID)^
create index IDX_LINEUP_TICKET_ON_PASSENGER on LINEUP_TICKET (PASSENGER_ID)^
create index IDX_LINEUP_TICKET_ON_TRAVEL_OPTION on LINEUP_TICKET (TRAVEL_OPTION_ID)^
-- end LINEUP_TICKET

-- begin LINEUP_FAVORITE_TRIP
alter table LINEUP_FAVORITE_TRIP add constraint FK_LINEUP_FAVORITE_TRIP_ON_START_SITE foreign key (START_SITE_ID) references LINEUP_SITE(ID)^
alter table LINEUP_FAVORITE_TRIP add constraint FK_LINEUP_FAVORITE_TRIP_ON_DESTINATION foreign key (DESTINATION_ID) references LINEUP_SITE(ID)^
create index IDX_LINEUP_FAVORITE_TRIP_ON_START_SITE on LINEUP_FAVORITE_TRIP (START_SITE_ID)^
create index IDX_LINEUP_FAVORITE_TRIP_ON_DESTINATION on LINEUP_FAVORITE_TRIP (DESTINATION_ID)^
-- end LINEUP_FAVORITE_TRIP

-- begin LINEUP_TRAVEL_OPTION
alter table LINEUP_TRAVEL_OPTION add constraint FK_LINEUP_TRAVEL_OPTION_ON_TRANSFER foreign key (TRANSFER_ID) references LINEUP_TRANSFER(ID)^
alter table LINEUP_TRAVEL_OPTION add constraint FK_LINEUP_TRAVEL_OPTION_ON_FAVORITE_TRIP foreign key (FAVORITE_TRIP_ID) references LINEUP_FAVORITE_TRIP(ID)^
create index IDX_LINEUP_TRAVEL_OPTION_ON_TRANSFER on LINEUP_TRAVEL_OPTION (TRANSFER_ID)^
create index IDX_LINEUP_TRAVEL_OPTION_ON_FAVORITE_TRIP on LINEUP_TRAVEL_OPTION (FAVORITE_TRIP_ID)^
-- end LINEUP_TRAVEL_OPTION
