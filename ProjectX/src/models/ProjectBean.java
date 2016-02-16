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
	private double estimatedCosts;
	private String deadline;
	private int estimatedDuration;
	private String pmName;
	private String clientName;
	
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

	public ProjectBean(){
		
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

	public int getEstimatedDuration() {
		return estimatedDuration;
	}

	public void setEstimatedDuration(int estimatedDuration) {
		this.estimatedDuration = estimatedDuration;
	}
	
	
	

}
