package controllers.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import context.CriticalStagesObserver;
import context.DelayStartStagesObserver;
import context.StagesNonCriticalObserver;
import context.StartStagesObserver;
import models.StageBean;
import models.StageDAO;
import models.UserDAO;

public class SchedulerEventsThread implements Runnable {
    private int idProject;
    static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

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

    @Override
    public void run() {
        List<StageBean> stages;
        stages = models.StageDAO.getInstance().getStagesByIdProject(this.getIdProject());
        long criticalDate;
        long startDate;
        Map<StageBean, List<StageBean>> mapPrecedences = models.StageDAO.getInstance()
                .getPrecedences(this.getIdProject());

        for (StageBean stage : stages) {
            try {
                criticalDate = getDifferenceDays(format.parse(stage.getFinishDay()),
                        format.parse(UtilityFunctions.getCurrentDateTime()));
                startDate = getDifferenceDays(format.parse(stage.getStartDay()),
                        format.parse(UtilityFunctions.getCurrentDateTime()));
                if (criticalDate < 0 && stage.getRateWorkCompleted() < 100 && "False".equals(stage.getCritical())
                        && !"Delay".equals(stage.getStatus())) {
                    StageDAO.setStatusStage(stage.getIdStage(), "Delay");
                    StagesNonCriticalObserver.update(stage.getIdSupervisor());
                } else
                    helperRunMethod(criticalDate, startDate, mapPrecedences, stage);
            } catch (ParseException e) {
                LOGGER.log(Level.SEVERE, "Something went wrong during parsing a date", e);
            }
        }
    }

    private void helperRunMethod(long criticalDate, long startDate, Map<StageBean, List<StageBean>> mapPrecedences,
            StageBean stage) {
        if (criticalDate < 0 && stage.getRateWorkCompleted() < 100 && "True".equals(stage.getCritical())
                && !"CriticalDelay".equals(stage.getStatus())) {
            StageDAO.setStatusStage(stage.getIdStage(), "CriticalDelay");
            String projectManagerMail = UserDAO.getInstance().getProjectManagerMailByIdStage(stage.getIdStage());
            CriticalStagesObserver.update(stage.getIdSupervisor(), projectManagerMail);
        } else if (startDate == 0 && !"Started".equals(stage.getStatus())) {
            if (arePrecedencesCompleted(mapPrecedences, stage)) {
                StageDAO.setStatusStage(stage.getIdStage(), "Started");
                StartStagesObserver.update(stage.getIdSupervisor());
            } else {
                DelayStartStagesObserver.update(stage.getIdSupervisor());
            }

        }
    }

    public boolean arePrecedencesCompleted(Map<StageBean, List<StageBean>> mapPrecedences, StageBean stage) {
        List<StageBean> precedences = mapPrecedences.get(stage);
        for (StageBean precedence : precedences) {
            if (precedence.getRateWorkCompleted() < 100) {
                return false;
            }
        }
        return true;
    }

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d1.getTime() - d2.getTime();
        if (diff < 0) {
            return (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)) - 1;
        }
        if (diff > 0) {
            return (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)) + 1;
        }
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}
