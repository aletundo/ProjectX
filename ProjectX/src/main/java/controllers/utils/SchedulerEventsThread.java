package controllers.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import context.CriticalStagesObserver;
import context.StagesNonCriticalObserver;
import context.Subject;
import models.StageBean;

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
        stages = getStages();
        long criticalDate;

        for (StageBean stage : stages) {
            try {
                criticalDate = getDifferenceDays(format.parse(stage.getFinishDay()),
                        format.parse(UtilityFunctions.GetCurrentDateTime()));
                if (criticalDate < 0 && stage.getRateWorkCompleted() < 100) {
                    StagesNonCriticalObserver.update();
                } else if (criticalDate < 0 && stage.getRateWorkCompleted() < 99
                        && "True".equals(stage.getCritical())) {
                    CriticalStagesObserver.update();
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

    public List<StageBean> getStages() {
        List<StageBean> stages;
        stages = models.StageDAO.getInstance().getStagesByIdProject(this.getIdProject());
        return stages;
    }
}
