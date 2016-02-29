package controllers.utils;


import org.junit.Test;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import controllers.utils.CalculateWeights;
import models.StageBean;
import models.TaskBean;

public class CalculateWeightsTest {

	@Test
	public void calculateWeights() throws ParseException {

		List<StageBean> stages = new ArrayList<>();
		List<TaskBean> tasks = new ArrayList<>();

		StageBean stage1 = new StageBean();
		stage1.setIdStage(1);
		stage1.setStartDay("2016-01-01");
		stage1.setFinishDay("2016-01-31");

		StageBean stage2 = new StageBean();
		stage2.setIdStage(2);
		stage2.setStartDay("2016-01-01");
		stage2.setFinishDay("2016-01-10");

		StageBean stage3 = new StageBean();
		stage3.setIdStage(3);
		stage3.setStartDay("2016-02-01");
		stage3.setFinishDay("2016-02-28");

		StageBean stage4 = new StageBean();
		stage4.setIdStage(4);
		stage4.setStartDay("2016-03-01");
		stage4.setFinishDay("2016-03-20");

		stages.add(stage1);
		stages.add(stage2);
		stages.add(stage3);
		stages.add(stage4);

		TaskBean task1 = new TaskBean();
		task1.setIdTask(1);
		task1.setStartDay("2016-01-01");
		task1.setFinishDay("2016-01-07");

		TaskBean task2 = new TaskBean();
		task2.setIdTask(2);
		task2.setStartDay("2016-01-08");
		task2.setFinishDay("2016-01-15");

		TaskBean task3 = new TaskBean();
		task3.setIdTask(3);
		task3.setStartDay("2016-01-15");
		task3.setFinishDay("2016-01-20");

		TaskBean task4 = new TaskBean();
		task4.setIdTask(4);
		task4.setStartDay("2016-01-21");
		task4.setFinishDay("2016-01-31");

		tasks.add(task1);
		tasks.add(task2);
		tasks.add(task3);
		tasks.add(task4);

		CalculateWeights.computeStagesWeight(stages);
		CalculateWeights.computeTasksWeight(tasks);

		assertEquals(34.831463, stages.get(0).getRelativeWeight(), 0.001);
		assertEquals(11.235955, stages.get(1).getRelativeWeight(), 0.001);
		assertEquals(31.460676, stages.get(2).getRelativeWeight(), 0.001);
		assertEquals(22.47191, stages.get(3).getRelativeWeight(), 0.001);

		assertEquals(21.875, tasks.get(0).getRelativeWeight(), 0.001);
		assertEquals(25.000, tasks.get(1).getRelativeWeight(), 0.001);
		assertEquals(18.750, tasks.get(2).getRelativeWeight(), 0.001);
		assertEquals(34.375, tasks.get(3).getRelativeWeight(), 0.001);
	}

}
