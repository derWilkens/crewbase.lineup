package eu.crewbase.lineup.service;

import eu.crewbase.lineup.entity.coredata.AppUser;

public interface UserImportService {
    String NAME = "lineup_UserImportService";
    int parseCsv(String rawUsers);
	AppUser getUserByEmail(String email);
	int createOrUpdateUser();
	void clearStageTable();
}