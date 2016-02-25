package test;
import org.junit.Test;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import controllers.utils.CriticalPath;
import models.StageBean;

public class CriticalPathMethodTest {
	@Test
	public void CriticalPath() throws ParseException{
		Map<StageBean, List<StageBean>> map = new HashMap<StageBean, List<StageBean>>();
		List<StageBean> precedencesA = null;
		List<StageBean> precedencesB = new ArrayList<StageBean>();
		List<StageBean> precedencesC = new ArrayList<StageBean>();
		List<StageBean> precedencesD = new ArrayList<StageBean>();
		
		StageBean a = new StageBean();
		StageBean b = new StageBean();
		StageBean c = new StageBean();
		StageBean d = new StageBean();
		
		a.setIdStage(1);
		b.setIdStage(2);
		c.setIdStage(3);
		d.setIdStage(4);
		
		
		a.setStartDay("2016-01-01");
		b.setStartDay("2016-01-06");
		c.setStartDay("2016-01-07");
		d.setStartDay("2016-01-12");
		
		a.setFinishDay("2016-01-05");
		b.setFinishDay("2016-01-11");
		c.setFinishDay("2016-01-08");
		d.setFinishDay("2016-01-14");
		
		
		 precedencesB.add(a);
		precedencesC.add(a);
		precedencesD.add(b);
		precedencesD.add(c);
		
		map.put(a, precedencesA);
		map.put(b, precedencesB);
		map.put(c, precedencesC);
		map.put(d, precedencesD);
		
		
		List<StageBean> criticalStages = CriticalPath.computeCriticalStages(map);
		

		
		for(StageBean critical : criticalStages){
			
			System.out.println("critico " + critical.getIdStage());
			
		
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
//		Map<Integer, Integer> mapES = new HashMap<Integer, Integer>();
//		List<StageBean> criticalTasks = new ArrayList<StageBean>();
//		Iterator<Map.Entry<Integer, List<StageBean>>> mapIterator = mapProva.entrySet().iterator();
//
//		Map.Entry<Integer, List<StageBean>> pair = (Map.Entry<Integer, List<StageBean>>) mapIterator.next();
		
		
		 
		
		/*StageBean stage1 = new StageBean();
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
		stage3.setFinishDay("2016-02-28");*/
		
		
	//	mapProva.put(2, stages);
	//	mapProva.put(3, stages);
		
	/*	stages.add(stage1);
		stages.add(stage2);
		stages.add(stage3);
		
		mapProva.put(1, stages);
		*/
		
		

		//	mapIterator.forEachRemaining(action);
	//	System.out.println(pair.getKey());
	//	pair.setValue(stages);
	//	assert(pair.getValue() == null);
	//List<StageBean> criticalStages = controllers.utils.CriticalPath.computeCriticalStages(mapProva);
	//	CriticalPath.getInstance().precedencesHasES(pair);
	/*	System.out.println(mapProva.get(1));
		System.out.println("ciao");
	for(StageBean critical : criticalStages){
		System.out.println(critical);
	}*/
	}
}
	
