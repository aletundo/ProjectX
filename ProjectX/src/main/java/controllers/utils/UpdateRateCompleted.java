package controllers.utils;

import java.util.List;

import models.ProjectDAO;
import models.StageBean;
import models.StageDAO;
import models.TaskBean;
import models.TaskDAO;

/**
 * @author alessandro The aim of the class is to update the percent work
 *         complete of a project and a stage when one of its tasks is completed.
 */
public class UpdateRateCompleted {

	private UpdateRateCompleted() {

	}

	/**
	 * @param idTask
	 *            Check if all developers have completed their work. If it is
	 *            true, set the task as completed and call
	 *            updateStageRateCompleted.
	 */
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

	/**
	 * @param idTask
	 *            Update the rate work completed of a stage and call
	 *            updateProjectRateCompleted.
	 */
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

	/**
	 * @param idStage
	 *            Update the rate work completed of a project.
	 */
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
