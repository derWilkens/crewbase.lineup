package eu.crewbase.lineup.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;

import eu.crewbase.lineup.entity.batchimport.UserImportStage;
import eu.crewbase.lineup.entity.coredata.AppUser;
import eu.crewbase.lineup.entity.coredata.Department;

@Service(UserImportService.NAME)
public class UserImportServiceBean implements UserImportService {
	@Inject
	private Persistence persistence;

	@Inject
	private Metadata metadata;

	@Override
	public int parseCsv(String rawUsers) {
		int counter = 0;
		clearStageTable();

		String[] lines = rawUsers.split("[\r\n]");

		UserImportStage userImportItem;
		for (String line : lines) {
			Transaction tx = persistence.createTransaction();
			String[] values = line.split("\t");
			String errorMsg = "";
			if (!values[0].contains("Name")) {
				userImportItem = metadata.create(UserImportStage.class);
				if (values.length < 5) {
					errorMsg += "Bitte alle Felder füllen: " + line + "\n";
				} else {
					userImportItem.setLastName(values[0]);
					userImportItem.setFirstName(values[1]);
					if (userImportItem.getFirstName() == null) {
						errorMsg += "Vorname leer \n";
					}
					userImportItem.setJobtitle(values[2]);
					if (userImportItem.getJobtitle() == null) {
						errorMsg += "Funktion/Rolle leer \n";
					}
					userImportItem.setEmail(values[3]);
					if (userImportItem.getEmail() == null) {
						errorMsg += "E-Mail leer \n";
					}
					userImportItem.setDepartmentAcronym(values[4]);
					if (userImportItem.getDepartmentAcronym() == null) {
						errorMsg += "Team leer \n";
					}
				}
				try {
					userImportItem.setImportLog(errorMsg);
					persistence.getEntityManager().persist(userImportItem);
					tx.commit();

				} catch (Exception e) {
					e.printStackTrace();
					tx = persistence.createTransaction();
					userImportItem = metadata.create(UserImportStage.class);
					userImportItem.setImportLog(errorMsg + " " + e.getMessage());
					persistence.getEntityManager().persist(userImportItem);
					tx.commit();
				}
				counter++;
			}
		}

		return counter;
	}

	@Override
	public AppUser getUserByEmail(String email) {
		AppUser user;
		String queryString = "select e from lineup$AppUser e where e.email = :email ";

		TypedQuery<AppUser> query = persistence.getEntityManager().createQuery(queryString, AppUser.class);
		query.setParameter("email", email);

		user = query.getFirstResult();

		return user;
	}

	private Department getDepartmentByAcronym(String acronym) {
		Department department;

		String queryString = "select e from lineup$Department e where e.acronym = :acronym ";

		TypedQuery<Department> query = persistence.getEntityManager().createQuery(queryString, Department.class);
		query.setParameter("acronym", acronym);

		department = query.getFirstResult();

		return department;
	}

	@Override
	public int createOrUpdateUser() {
		int counter = 0;
		try (Transaction tx = persistence.createTransaction()) {

			List<UserImportStage> importItemList = persistence.getEntityManager()
					.createQuery("select e from lineup$UserImportStage e where e.importLog = ''",
							UserImportStage.class)
					.getResultList();

			for (UserImportStage importItem : importItemList) {
				boolean error = false;
				AppUser tmpUser;
				tmpUser = getUserByEmail(importItem.getEmail());

				if (tmpUser == null) {
					tmpUser = metadata.create(AppUser.class);
					tmpUser.setEmail(importItem.getEmail());
				}
				tmpUser.setLastname(importItem.getLastName());
				tmpUser.setFirstname(importItem.getFirstName());
				//tmpUser.setJobfunction(importItem.getJobtitle());
				try {
					tmpUser.setDepartment(getDepartmentByAcronym(importItem.getDepartmentAcronym()));
				} catch (Exception e) {
					e.printStackTrace();
					importItem.setImportLog("Team konnte nicht gefunden werden: " + importItem.getDepartmentAcronym());
					persistence.getEntityManager().persist(importItem);
					error = true;
				}

				if (!error) {
					persistence.getEntityManager().persist(tmpUser);
					persistence.getEntityManager().remove(importItem);
					counter++;
				}

			}

			tx.commit();
		}

		return counter;
	}

	@Override
	public void clearStageTable() {
		try (Transaction tx = persistence.createTransaction()) {
			String queryString = "delete from lineup$UserImportStage e";
			persistence.getEntityManager().createQuery(queryString).executeUpdate();
			tx.commit();
		}

	}

}