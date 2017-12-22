package eu.crewbase.lineup.service;

import java.text.ParseException;
import java.util.Collection;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;

import eu.crewbase.lineup.entity.UserPreferencesContext;
import eu.crewbase.lineup.entity.coredata.AppUser;
import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.dto.PeriodDTO;
import eu.crewbase.lineup.entity.period.AbsencePeriod;
import eu.crewbase.lineup.entity.period.AttendencePeriod;
import eu.crewbase.lineup.entity.period.Period;

@Service(RotaplanService.NAME)
public class RotaplanServiceBean extends PreferencesService implements RotaplanService {

	@Inject
	private Persistence persistence;
	@Inject
	private Metadata metadata;
	@Inject
	private EntityService entityService;

	@Override
	public Site getSiteByItemDesignation(String itemDesignation) {
		Site result = null;
		try (Transaction tx = persistence.createTransaction()) {

			String queryString = "select e from lineup$Site e where e.itemDesignation = :itemDesignation";

			TypedQuery<Site> query = persistence.getEntityManager().createQuery(queryString, Site.class);
			query.setParameter("itemDesignation", itemDesignation);
			result = query.getFirstResult();
			tx.commit();
		}
		return result;
	}

	public Collection<Site> getPreferredSites() {
		return super.getPreferredSites(persistence.getEntityManager(), UserPreferencesContext.Rotaplan);
	}

	@Override
	public Period createPeriod(PeriodDTO dto) throws ClassNotFoundException, ParseException {
		Period result = null;
		try (Transaction tx = persistence.createTransaction()) {
			setPersonOnDuty(dto);
			setSite(dto);
			result = (Period) metadata.create(Class.forName(dto.getClazzName()));
			result.readDto(dto);
			persistence.getEntityManager().persist(result);
			tx.commit();
		}
		return result;
	}

	private void setPersonOnDuty(PeriodDTO dto) {
		if (dto.getUserId() != null) {
			dto.setPersonOnDuty(persistence.getEntityManager().find(AppUser.class, UUID.fromString(dto.getUserId())));
		}
	}

	private void setSite(PeriodDTO dto) {
		if (dto.getSiteId() != null) {
			dto.setSite(persistence.getEntityManager().find(Site.class, UUID.fromString(dto.getSiteId())));
		}

	}

	@Override
	public Period updatePeriod(PeriodDTO dto) {
		Period period = null;
		try (Transaction tx = persistence.createTransaction()) {
			if (dto.getClazzName().equals(AbsencePeriod.class.getName())) {
				period = persistence.getEntityManager().find(AbsencePeriod.class, UUID.fromString(dto.getEntityId()));
			}
			if (dto.getClazzName().equals(AttendencePeriod.class.getName())) {
				period = persistence.getEntityManager().find(AttendencePeriod.class, UUID.fromString(dto.getEntityId()));
			}
			setPersonOnDuty(dto);
			setSite(dto);
			period.readDto(dto);
			persistence.getEntityManager().persist(period);
			tx.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return period;
	}

	@Override
	public void deletePeriod(String entityId) {
		// TODO Auto-generated method stub

	}

}