package eu.crewbase.lineup.service;

import eu.crewbase.lineup.entity.coredata.AppUser;

public interface UserImportService {
    String NAME = "lineup_UserImportService";
    public int parseCsv(String rawUsers);
	public AppUser getUserByEmail(String email);
	public int createOrUpdateUser();
	public void clearStageTable();
}