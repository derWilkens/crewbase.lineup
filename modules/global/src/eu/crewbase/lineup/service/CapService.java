package eu.crewbase.lineup.service;

import java.util.List;

import eu.crewbase.lineup.entity.cap.coredata.Role;
import eu.crewbase.lineup.entity.period.AttendencePeriod;

public interface CapService {
    String NAME = "lineup_CapService";
    
    List<Role> getAvailableUserRoles(AttendencePeriod dutyPeriod);
}