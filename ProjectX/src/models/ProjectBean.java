package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProjectBean {

	private int idProject;
	private String name;
	private int idProjectManager;
	private double budget;
	private int idClient;
	private String goals;
	private String requirements;
	private String subjectAreas;
	private Date start;
	private double estimatedCosts;
	private Date deadline;
	private int estimatedDuration;
	private String pmName;
	private String clientName;

	public ProjectBean() {

	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getPmName() {
		return pmName;
	}

	public void setPmName(String pmName) {
		this.pmName = pmName;
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

	public int getIdProjectManager() {
		return idProjectManager;
	}

	public void setIdProjectManager(int idProjectManager) {
		this.idProjectManager = idProjectManager;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
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

	public String getSubjectAreas() {
		return subjectAreas;
	}

	public void setSubjectAreas(String subjectAreas) {
		this.subjectAreas = subjectAreas;
	}

	public double getEstimatedCosts() {
		return estimatedCosts;
	}

	public void setEstimatedCosts(double estimatedCosts) {
		this.estimatedCosts = estimatedCosts;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = formatter.parse(deadline);
		} catch (ParseException e) {
			// TODO Handle with a Logger
			e.printStackTrace();
		}
		this.deadline = date;
	}

	public int getEstimatedDuration() {
		return estimatedDuration;
	}

	public void setEstimatedDuration(int estimatedDuration) {
		this.estimatedDuration = estimatedDuration;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(String start) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = formatter.parse(start);
		} catch (ParseException e) {
			// TODO Handle with a Logger
			e.printStackTrace();
		}
		this.start = date;
	}

}
