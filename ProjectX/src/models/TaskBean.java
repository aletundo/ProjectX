package models;

public class TaskBean {
	
	private int idTask;
	private int idStage;
	private int idDeveloper;
	private String name;
	private String startDay;
	private String finishDay;
	private String completed;
	
	public TaskBean(){
		
	}
	public int getIdTask() {
		return idTask;
	}
	public void setIdTask(int idTask) {
		this.idTask = idTask;
	}
	public int getIdStage() {
		return idStage;
	}
	public void setIdStage(int idProject) {
		this.idStage = idProject;
	}
	public int getIdDeveloper() {
		return idDeveloper;
	}
	public void setIdDeveloper(int idDeveloper) {
		this.idDeveloper = idDeveloper;
	}
	public String getStartDay() {
		return startDay;
	}
	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}
	public String getFinishDay() {
		return finishDay;
	}
	public void setFinishDay(String finishDay) {
		this.finishDay = finishDay;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompleted() {
		return completed;
	}
	public void setCompleted(String completed) {
		this.completed = completed;
	}
	@Override
	public String toString() {
		return "TaskBean [idTask=" + idTask + ", idStage=" + idStage + ", idDeveloper=" + idDeveloper + ", name=" + name
				+ ", startDay=" + startDay + ", finishDay=" + finishDay + ", completed=" + completed + "]";
	}
}