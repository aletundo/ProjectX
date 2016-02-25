package controllers.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import models.StageBean;


public class CriticalPath {

	private CriticalPath() {

	}


	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	private static Map<Integer, Integer> mapES = new HashMap<Integer, Integer>();

	private static List<StageBean> criticalTasks = new ArrayList<StageBean>();

	public static List<StageBean> computeCriticalStages(Map<StageBean, List<StageBean>> mapPrecedences) {
		setLastCritical(mapPrecedences);
		//Iterator<Map.Entry<Integer, List<StageBean>>> mapIterator;
		while (!mapPrecedences.isEmpty()) {
			//mapIterator = mapPrecedences.entrySet().iterator();
			List<StageBean> toRemove = new ArrayList<StageBean>();
			for(Map.Entry<StageBean, List<StageBean>> pair : mapPrecedences.entrySet()) {
				//Map.Entry<Integer, List<StageBean>> pair = (Map.Entry<Integer, List<StageBean>>) mapIterator.next();
				System.out.println(pair);
				if (pair.getValue() == null) {
					System.out.println("non ho precedenti");
					mapES.put(pair.getKey().getIdStage(), 0);
					toRemove.add(pair.getKey());
					//mapPrecedences.remove(pair.getKey());
					//mapIterator.remove();
				} else if (precedencesHasES(pair)) {
						System.out.println("ho precedenti con ES");
						try {
							int ES = (int)computeES(pair.getValue());
							mapES.put(pair.getKey().getIdStage(), (Integer) ES);
							toRemove.add(pair.getKey());

							//mapPrecedences.remove(pair.getKey());
							//mapIterator.remove();

						} catch (ParseException e) {
							// TODO handle with a logger
						}
					}
			}
			
			
			for(StageBean removable : toRemove){
				System.out.println("rimouovo gli elementi che hanno ES");
				System.out.println(removable);
				mapPrecedences.remove(removable);
			}
		}
		for(Map.Entry<Integer,Integer> p : mapES.entrySet()){
			System.out.println("mapES " + p.getValue());
		}
		return criticalTasks;
	}

	public static void setLastCritical(Map<StageBean, List<StageBean>> mapPrecedences){
		for(Map.Entry<StageBean, List<StageBean>> pairKey : mapPrecedences.entrySet()){
			boolean b = true;
			for(Map.Entry<StageBean, List<StageBean>> pairValue : mapPrecedences.entrySet()){
				if(pairValue.getValue() != null && pairValue.getValue().contains(pairKey.getKey())){
					b = false;
				}
			}
			System.out.println(b);
			if(b){
				criticalTasks.add(pairKey.getKey());
			}
		}
	}
	
	public static boolean precedencesHasES(Map.Entry<StageBean, List<StageBean>> pair) {
		//System.out.println("sono in precedencesHasES");
		List<StageBean> precedences = pair.getValue();
		//Iterator<StageBean> arrayListIterator = precedences.iterator();
		for (StageBean precedence : precedences) {
			if (precedence != null && mapES.get(precedence.getIdStage()) == null) {
				//arrayListIterator.remove();
				return false;
			}
			//arrayListIterator.remove();
		}
		return true;
	}

	public static long computeES(List<StageBean> precedences) throws ParseException {
		System.out.println("sono in computeES");
		long max = 0;
		long result = 0;
		List<Long> results = new ArrayList<Long>();
		System.out.println(precedences);
		for (StageBean precedence : precedences) {
			System.out.println("sono nel for");
			long duration = 8 * CalculateAvailableUsers.getDifferenceDays(format.parse(precedence.getStartDay()),
					format.parse(precedence.getFinishDay()));
			long ES = mapES.get(precedence.getIdStage());
			result = ES + duration;
			results.add(result);
			
			System.out.println("result: " + result);
			System.out.println("duration: " + duration);
			if (result > max)  {
				max = result;
			}
		}
	
		for(int i = 0; i<results.size(); ++i){
			if(results.get(i) == max && !criticalTasks.contains(precedences.get(i)) ){
				
				criticalTasks.add(precedences.get(i));
			}
		}
		/*if (critical != null) {
			criticalTasks.add(critical);
		}*/
		return max;
	}
}