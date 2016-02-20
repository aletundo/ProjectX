package models;

public class TaskBean {
	
	private int idTask;
	private int idStage;
	private int idDeveloper;
	private String startDay;
	private String finishDay;
	
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
}