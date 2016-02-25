package controllers.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import models.StageBean;
import models.StageDAO;
import models.TaskBean;
import models.TaskDAO;
import java.lang.Math;

public class CalculateWeights {

	private CalculateWeights() {

	}

	//Called when a supervisor add the last task
	public static void computeTasksWeight(List<TaskBean> tasks) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		float durationSum = 0;
		Map<Integer, Float> durationMap = new HashMap<Integer, Float>();
		for (TaskBean task : tasks) {
			try {
				Date start = sdf.parse(task.getStartDay());
				Date finish = sdf.parse(task.getFinishDay());
				float duration = 8 * getDifferenceDays(start, finish);
				durationMap.put(task.getIdTask(), duration);
				durationSum += duration;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(TaskBean task : tasks) {
			Float weight = (durationMap.get(task.getIdTask())/ durationSum) * 100;
			task.setRelativeWeight(weight);
		}
		
		TaskDAO.getInstance().setTasksWeight(tasks);
	}
	
	//Called when a project manager set the last stage
	public static void computeStagesWeight(List<StageBean> stages) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		float durationSum = 0;
		Map<Integer, Float> durationMap = new HashMap<Integer, Float>();
		for (StageBean stage : stages) {
			try {
				Date start = sdf.parse(stage.getStartDay());
				Date finish = sdf.parse(stage.getFinishDay());
				float duration = 8 * getDifferenceDays(start, finish);
				durationMap.put(stage.getIdStage(), duration);
				durationSum += duration;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(StageBean stage : stages) {
			float weight = (durationMap.get(stage.getIdStage())/ durationSum) * 100;
			stage.setRelativeWeight(weight);
		}
		
		StageDAO.getInstance().setStagesWeight(stages);
	}

	public static long getDifferenceDaysExclusive(Date start, Date finish) {
		long diff = finish.getTime() - start.getTime();
		return Math.abs(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
	}

	public static long getDifferenceDays(Date start, Date finish) {
		long diff = getDifferenceDaysExclusive(start, finish);
		if (diff == 0) {
			return diff;
		}
		return 1 + diff;
	}
}
