package models;

public class ProjectBean {
	
	private int idProject;
	private String name;
	private int idProjectManager;
	private double budget;
	private int idClient;
	private String goals;
	private String requirements;
	private String subjectAreas;
	private String start;
	private double estimatedCosts;
	private String deadline;
	private String pmName;
	private String clientName;
	private float rateWorkCompleted;
	
public ProjectBean(){
		
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

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public float getRateWorkCompleted() {
		return rateWorkCompleted;
	}

	public void setRateWorkCompleted(float rateWorkCompleted) {
		this.rateWorkCompleted = rateWorkCompleted;
	}

	@Override
	public String toString() {
		return "ProjectBean [idProject=" + idProject + ", name=" + name + ", idProjectManager=" + idProjectManager
				+ ", budget=" + budget + ", idClient=" + idClient + ", goals=" + goals + ", requirements="
				+ requirements + ", subjectAreas=" + subjectAreas + ", start=" + start + ", estimatedCosts="
				+ estimatedCosts + ", deadline=" + deadline + ", pmName=" + pmName + ", clientName=" + clientName
				+ ", rateWorkCompleted=" + rateWorkCompleted + "]";
	}
}
