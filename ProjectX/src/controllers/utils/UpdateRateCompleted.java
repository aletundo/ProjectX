package controllers.utils;

import java.util.List;

import models.ProjectDAO;
import models.StageBean;
import models.StageDAO;
import models.TaskBean;
import models.TaskDAO;

public class UpdateRateCompleted {
	
	private static final UpdateRateCompleted INSTANCE = new UpdateRateCompleted();

	private UpdateRateCompleted() {

	}

	public static UpdateRateCompleted getInstance() {

		return INSTANCE;
	}
	
	public void checkTaskCompleted(int idTask){
		List<String> workCompletedState = TaskDAO.getInstance().checkDevelopersWork(idTask);
		boolean isTaskCompleted = true;
		for(String isCompleted : workCompletedState ){
			if(isCompleted.equals("False"))
				isTaskCompleted = false;
		}
		
		if(isTaskCompleted){
			TaskDAO.getInstance().setTaskCompleted(idTask);
			updateStageRateCompleted(idTask);
		}
	}
	
	private void updateStageRateCompleted(int idTask){
		TaskBean task = TaskDAO.getInstance().getRelativeWeight(idTask);
		StageDAO.getInstance().setRateWorkCompleted(task.getIdStage(), task.getRelativeWeight());
		updateProjectRatecompleted(task.getIdStage());
	}
	
	private void updateProjectRatecompleted(int idStage){
		StageBean stage = StageDAO.getInstance().getRelativeWeight(idStage);
		float rateWorkCompleted = StageDAO.getInstance().getRateWorkCompleted(idStage);
		float rateProjectCompleted = (rateWorkCompleted * stage.getRelativeWeight()) / 100;
		ProjectDAO.getInstance().setRateWorkCompleted(stage.getIdProject(), rateProjectCompleted);
		
	}

}
