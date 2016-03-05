package controllers.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.StageBean;
import models.StageDAO;
import models.TaskBean;
import models.TaskDAO;
import utils.GetWorkhoursProperties;

/**
 * @author alessandro The class CalculateWeights computes the weight of tasks
 *         and stages of a project in relation to their duration.
 */
public class CalculateWeights {

	private static int HOURPERDAY;
	private static final Logger LOGGER = Logger.getLogger(CalculateWeights.class.getName());

	private CalculateWeights() {

	}

	/**
	 * @param tasks
	 *            Calculate the weight of tasks in relation to their duration.
	 * 
	 */
	public static void computeTasksWeight(List<TaskBean> tasks) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		float durationSum = 0;
		Map<Integer, Float> durationMap = new HashMap<>();

		getHourPerDayProperty();

		for (TaskBean task : tasks) {
			try {
				Date start = sdf.parse(task.getStartDay());
				Date finish = sdf.parse(task.getFinishDay());
				float duration = HOURPERDAY * UtilityFunctions.getDifferenceDays(start, finish);
				durationMap.put(task.getIdTask(), duration);
				durationSum += duration;
			} catch (ParseException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during parsing a date", e);
			}
		}

		for (TaskBean task : tasks) {
			Float weight = (durationMap.get(task.getIdTask()) / durationSum) * 100;
			task.setRelativeWeight(weight);
		}

		TaskDAO.getInstance().setTasksWeight(tasks);
	}

	/**
	 * @param stages
	 *            Calculate the weight of stages in relation to their duration.
	 */
	public static void computeStagesWeight(List<StageBean> stages) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		float durationSum = 0;
		Map<Integer, Float> durationMap = new HashMap<>();

		getHourPerDayProperty();

		for (StageBean stage : stages) {
			try {
				Date start = sdf.parse(stage.getStartDay());
				Date finish = sdf.parse(stage.getFinishDay());
				float duration = HOURPERDAY * UtilityFunctions.getDifferenceDays(start, finish);
				durationMap.put(stage.getIdStage(), duration);
				durationSum += duration;
			} catch (ParseException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during parsing a date", e);
			}
		}

		for (StageBean stage : stages) {
			float weight = (durationMap.get(stage.getIdStage()) / durationSum) * 100;
			stage.setRelativeWeight(weight);
		}

		StageDAO.getInstance().setStagesWeight(stages);
	}

	/**
	 * Get the property HOURPERDAY from a property file
	 */
	private static void getHourPerDayProperty() {
		try {
			Integer[] propertiesValues = GetWorkhoursProperties.getInstance().getPropValues();
			HOURPERDAY = propertiesValues[0];
		} catch (IOException e1) {
			LOGGER.log(Level.SEVERE, "Something went wrong during getting properties values", e1);
		}
	}
}
