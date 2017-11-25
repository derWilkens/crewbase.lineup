package eu.crewbase.lineup.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;

import eu.crewbase.lineup.entity.cap.Certificate;
import eu.crewbase.lineup.entity.cap.coredata.QualificationType;
import eu.crewbase.lineup.entity.cap.coredata.Role;
import eu.crewbase.lineup.entity.cap.coredata.RoleQualificationType;
import eu.crewbase.lineup.entity.coredata.AppUser;
import eu.crewbase.lineup.entity.period.AttendencePeriod;



@Service(CapService.NAME)
public class CapServiceBean implements CapService {

	@Inject
	private Persistence persistence;

	@Override
	public List<Role> getAvailableUserRoles(AttendencePeriod dutyPeriod) {
		List<Role> validRoles = new ArrayList<Role>();

		try (Transaction tx = persistence.createTransaction()) {

			// Rolle n-m QualificationType
			// Rolle 1-n RoleQualificationType n-1 QualificationType

			// Anhand der vorhandenen QualificationTypes werden die theoretisch
			// möglichen Rollen geholt
			// und dann vorwärts geguckt, ob alle QualificationsTypes für diese
			// Rolle vorhanden sind
			List<Certificate> certificates = getValidUserCertificates(dutyPeriod);
			// alle gültigen QualificationTypes des users holen
			List<QualificationType> validQualificationTypes = new ArrayList<QualificationType>();
			for (Certificate certificate : certificates) {
				validQualificationTypes.add(certificate.getCertificateType());
			}

			for (QualificationType qualificationType : validQualificationTypes) {

				// Liste der Zuordnungen Qualification-Role holen
				List<RoleQualificationType> roleQualificationTypes = qualificationType.getRoleQualificationType();
				for (RoleQualificationType roleQualificationType : roleQualificationTypes) {

					// Rolle nehmen und Liste der RoleQualificationTypes holen
					Role roleInQuestion = roleQualificationType.getRole();
					if (!validRoles.contains(roleInQuestion)) {
						List<RoleQualificationType> roleQualificationsForRole = roleInQuestion
								.getRoleQualificationType();
						int possible = 0;
						// alle notwendigen CertificatTypes holen, die für die
						// Rollen notwendig sind
						for (RoleQualificationType roleQualificationTypeForward : roleQualificationsForRole) {

							// ist diese notwendige QualificationType in den
							// validCert vorhanden?
							if (validQualificationTypes.contains(roleQualificationTypeForward.getQualificationType())) {
								possible++;
							}
						}
						if (possible == roleQualificationsForRole.size()) {
							validRoles.add(roleInQuestion);
						}
					}
				}
			}
			// tx.commit();
			return validRoles;
		}
	}

	// eigentlich nicht notwendig
	@SuppressWarnings("unchecked")
	private List<Certificate> getCertificateForQualificationType(QualificationType qualificationType,
			AttendencePeriod dutyPeriod) {
		return persistence.getEntityManager()
				.createQuery(
						"SELECT c from lineup$Certificate c where c.expirationDate >= :endDate and c.appUser = :appUser and c.qualificationType = :qualificationType")
				.setParameter("endDate", dutyPeriod.getEnd()).setParameter("appUser", dutyPeriod.getPersonOnDuty())
				.setParameter("qualificationType", qualificationType).getResultList();
	}

	@SuppressWarnings("unchecked")
	private List<Certificate> getValidUserCertificates(AttendencePeriod dutyPeriod) {
		return persistence.getEntityManager()
				.createQuery(
						"SELECT c from lineup$Certificate c where c.expirationDate >= :endDate and c.appUser.id = :appUser")
				.setParameter("endDate", dutyPeriod.getEnd()).setParameter("appUser", dutyPeriod.getPersonOnDuty())
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	private List<QualificationType> getValidUserQualificationTypes(AttendencePeriod dutyPeriod) {
		AppUser userOnDuty = dutyPeriod.getPersonOnDuty();
		return persistence.getEntityManager()
				.createQuery("SELECT c.qualificationType from lineup$Certificate c where c.appUser.id = :appUser")
				.setParameter("appUser", userOnDuty).getResultList();
	}
}