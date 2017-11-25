package eu.crewbase.lineup.entity.coredata;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import eu.crewbase.lineup.entity.cap.coredata.Role;

@NamePattern("%s %s|site,role")
@Table(name = "LINEUP_SITE_ROLE_RULE")
@Entity(name = "lineup$SiteRoleRule")
public class SiteRoleRule extends StandardEntity {
	private static final long serialVersionUID = -8195290062635403814L;

	@Lookup(type = LookupType.DROPDOWN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SITE_ID")
	protected Site site;

	@Lookup(type = LookupType.DROPDOWN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID")
	protected Role role;

	@Lookup(type = LookupType.DROPDOWN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FUNCTION_CATEGORY_ID")
	protected FunctionCategory functionCategory;

	@Composition
	@OnDelete(DeletePolicy.CASCADE)
	@OneToMany(mappedBy = "siteRoleRule")
	protected List<NumberRangeRule> rangeRule;

	public void setFunctionCategory(FunctionCategory functionCategory) {
		this.functionCategory = functionCategory;
	}

	public FunctionCategory getFunctionCategory() {
		return functionCategory;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setRangeRule(List<NumberRangeRule> rangeRule) {
		this.rangeRule = rangeRule;
	}

	public List<NumberRangeRule> getRangeRule() {
		return rangeRule;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Site getSite() {
		return site;
	}

	/**
	 * für den definierten Zeitraum in der Zukunft werden sämtliche Dutyperiods
	 * ausgewertet. Daraus ergibt sich tagesscharf die POB Map mit Datum -
	 * Anzahl
	 */
	public int getRequiredNumberOfRoles(int maxPob) {
		int requiredNumberOfRoles = 0;
		boolean ruleHit = false;
		for (NumberRangeRule rangeRule : this.getRangeRule()) {
			if (maxPob > rangeRule.getAmountFrom() && maxPob <= rangeRule.getAmountTo()) {
				requiredNumberOfRoles = rangeRule.getRequiredNumber();
				ruleHit = true;
			}
		}
		if (!ruleHit) {
			throw new RuntimeException("Die Regeln für die Rolle " + this.getRole().getInstanceName() + " liefern für "
					+ maxPob + " POB keinen Treffer. Bitte Regel in den Stammdaten für den Standort "
					+ this.getSite().getInstanceName() + " prüfen");
		}
		return requiredNumberOfRoles;
	}

}