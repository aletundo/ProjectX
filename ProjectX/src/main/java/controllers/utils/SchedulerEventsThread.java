package controllers.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import context.CriticalStagesObserver;
import context.StagesNonCriticalObserver;
import context.StartStagesObserver;
import models.StageBean;
import models.StageDAO;

public class SchedulerEventsThread implements Runnable {
    private int idProject;
    static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    String host = "localhost";
    String port = "8080"; /* TODO check port */
    final String pw = "bla"; /* TODO check password */
    String toAddress = "asd@mail.com";
    private static final Logger LOGGER = Logger.getLogger(SchedulerEventsThread.class.getName());

    public SchedulerEventsThread(int idProject) {
        this.idProject = idProject;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    // public static void main(String[] args) {
    // final ScheduledExecutorService service =
    // Executors.newSingleThreadScheduledExecutor();
    // SchedulerEventsThread scheduler = new SchedulerEventsThread(74);
    // scheduler.attach(new StagesObserver(scheduler));
    // service.scheduleAtFixedRate(scheduler, 0, 24, TimeUnit.HOURS);
    // }

    @Override
    public void run() {
        List<StageBean> stages;
        stages = models.StageDAO.getInstance().getStagesByIdProject(this.getIdProject());
        long criticalDate;
        long startDate;

        for (StageBean stage : stages) {
            try {
                criticalDate = getDifferenceDays(format.parse(stage.getFinishDay()),
                        format.parse(UtilityFunctions.GetCurrentDateTime()));
                startDate = getDifferenceDays(format.parse(stage.getStartDay()),
                        format.parse(UtilityFunctions.GetCurrentDateTime()));
                if (criticalDate < 0 && stage.getRateWorkCompleted() < 100 && "False".equals(stage.getCritical())
                        && !"Delay".equals(stage.getStatus())) {
                    StageDAO.setDelayStatusStage(stage.getIdStage());
                    StagesNonCriticalObserver.update(stage.getIdSupervisor());
                } else if (criticalDate < 0 && stage.getRateWorkCompleted() < 100 && "True".equals(stage.getCritical())
                        && !"CriticalDelay".equals(stage.getStatus())) {
                    StageDAO.setCriticalDelayStatusStage(stage.getIdStage());
                    CriticalStagesObserver.update(stage.getIdSupervisor(), stage.getIdProject());
                } else if (startDate < 0 && !"Started".equals(stage.getStatus())) {
                    StageDAO.setStartStatusStage(stage.getIdStage());
                    StartStagesObserver.update(stage.getIdSupervisor());
                }
            } catch (ParseException e) {
                LOGGER.log(Level.SEVERE, "Something went wrong during parsing a date", e);
            }
        }
    }

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d1.getTime() - d2.getTime();
        if (diff < 0) {
            return (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)) - 1;
        }
        if (diff > 0) {
            return (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)) + 1;
        }
        return (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
    }
}
