package models;

public class StageBean {

    private int idStage;
    private int idProject;
    private String name;
    private int idSupervisor;
    private String goals;
    private String requirements;
    private String startDay;
    private String finishDay;
    private String outsourcing;
    private String supervisorFullname;
    private String companyName;
    private String companyMail;
    private float relativeWeight;
    private float rateWorkCompleted;
    private String critical;

    public StageBean() {
        // public constructor
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
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

    public String getOutsourcing() {
        return outsourcing;
    }

    public void setOutsourcing(String outsourcing) {
        this.outsourcing = outsourcing;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyMail() {
        return companyMail;
    }

    public void setCompanyMail(String companyMail) {
        this.companyMail = companyMail;
    }

    public String getSupervisorFullname() {
        return supervisorFullname;
    }

    public void setSupervisorFullname(String supervisorFullname) {
        this.supervisorFullname = supervisorFullname;
    }

    public float getRelativeWeight() {
        return relativeWeight;
    }

    public void setRelativeWeight(float relativeWeight) {
        this.relativeWeight = relativeWeight;
    }

    public float getRateWorkCompleted() {
        return rateWorkCompleted;
    }

    public void setRateWorkCompleted(float rateWorkCompleted) {
        this.rateWorkCompleted = rateWorkCompleted;
    }

    @Override
    public String toString() {
        return "StageBean [idStage=" + idStage + ", idProject=" + idProject + ", name=" + name + ", idSupervisor="
                + idSupervisor + ", goals=" + goals + ", requirements=" + requirements + ", startDay=" + startDay
                + ", finishDay=" + finishDay + ", outsourcing=" + outsourcing + ", supervisorFullname="
                + supervisorFullname + ", companyName=" + companyName + ", companyMail=" + companyMail
                + ", relativeWeight=" + relativeWeight + ", rateWorkCompleted=" + rateWorkCompleted + "]";
    }

}
