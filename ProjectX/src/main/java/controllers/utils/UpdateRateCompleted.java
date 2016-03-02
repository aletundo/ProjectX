package controllers.utils;

import java.util.List;

import models.ProjectDAO;
import models.StageBean;
import models.StageDAO;
import models.TaskBean;
import models.TaskDAO;

public class UpdateRateCompleted {

    private UpdateRateCompleted() {

    }

    public static void checkTaskCompleted(int idTask) {
        List<String> workCompletedState = TaskDAO.getInstance().checkDevelopersWork(idTask);
        boolean isTaskCompleted = true;
        for (String isCompleted : workCompletedState) {
            if ("False".equals(isCompleted))
                isTaskCompleted = false;
        }

        if (isTaskCompleted) {
            TaskDAO.getInstance().setTaskCompleted(idTask);
            updateStageRateCompleted(idTask);
        }
    }

    private static void updateStageRateCompleted(int idTask) {
        TaskBean task = TaskDAO.getInstance().getRelativeWeight(idTask);
        float rateWorkCompletedToUpdate = StageDAO.getInstance().getRateWorkCompleted(task.getIdStage())
                + task.getRelativeWeight();
        if (rateWorkCompletedToUpdate > 99) {
            rateWorkCompletedToUpdate = 100;
        }
        StageDAO.getInstance().setRateWorkCompleted(task.getIdStage(), rateWorkCompletedToUpdate);
        updateProjectRatecompleted(task.getIdStage());
    }

    private static void updateProjectRatecompleted(int idStage) {
        StageBean stage = StageDAO.getInstance().getRelativeWeight(idStage);
        float stageRateWorkCompleted = StageDAO.getInstance().getRateWorkCompleted(idStage);
        float rateStageToProjectCompleted = (stageRateWorkCompleted * stage.getRelativeWeight()) / 100;
        float rateProjectCompletedToUpdate = ProjectDAO.getInstance().getRateWorkCompleted(stage.getIdProject())
                + rateStageToProjectCompleted;
        if (rateProjectCompletedToUpdate > 99) {
            rateProjectCompletedToUpdate = 100;
        }
        ProjectDAO.getInstance().setRateWorkCompleted(stage.getIdProject(), rateProjectCompletedToUpdate);

    }

}
