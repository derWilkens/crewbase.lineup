package eu.crewbase.lineup.web.rotaplan;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractWindow;

import eu.crewbase.lineup.entity.coredata.Site;
import eu.crewbase.lineup.entity.period.AttendencePeriod;
import eu.crewbase.lineup.service.RotaplanService;

public class ShiftPeriodChooser extends AbstractWindow {
	
	@Inject
	public RotaplanService service;
	private Site site;
	private int duration;
	private String clazzName;
	
	public void r1c1(){
		site = service.getSiteByItemDesignation("BWAL");
		duration = 7;
		clazzName = AttendencePeriod.class.getName();
		this.close(CLOSE_ACTION_ID);
	}
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}


	
}