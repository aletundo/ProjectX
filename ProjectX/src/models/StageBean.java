package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StageBean {
	
	private int idStage;
	private int idProject;
	private String name;
	private int idSupervisor;
	private String goals;
	private String requirements;
	private Date startDay;
	private Date finishDay;
	private int duration;
	
	public StageBean(){
		
	}
	public int getIdStage() {
		return idStage;
	}
	public void setIdStage(int idStage) {
		this.idStage = idStage;
	}
	public int getIdProject() {
		return idProject;
	}
	public void setIdProject(int idProject) {
		this.idProject = idProject;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIdSupervisor() {
		return idSupervisor;
	}
	public void setIdSupervisor(int idSupervisor) {
		this.idSupervisor = idSupervisor;
	}
	public String getGoals() {
		return goals;
	}
	public void setGoals(String goals) {
		this.goals = goals;
	}
	public String getRequirements() {
		return requirements;
	}
	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}
	public Date getStartDay() {
		return startDay;
	}
	public void setStartDay(String startDay) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = formatter.parse(startDay);
		} catch (ParseException e) {
			// TODO Handle with a Logger
			e.printStackTrace();
		}
		this.startDay = date;
	}
	public Date getFinishDay() {
		return finishDay;
	}
	public void setFinishDay(String finishDay) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = formatter.parse(finishDay);
		} catch (ParseException e) {
			// TODO Handle with a Logger
			e.printStackTrace();
		}
		this.finishDay = date;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	

}
