package controllers.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.StageBean;
import models.StageDAO;

public class CriticalPath {

	private CriticalPath() {

	}

	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	private static Map<Integer, Integer> mapES = new HashMap<>();

	private static List<StageBean> criticalStages = new ArrayList<>();

	private static final Logger LOGGER = Logger.getLogger(CriticalPath.class.getName());
			
	public static void computeCriticalStages(String idProjectString) {
		System.out.println(idProjectString);
		int idProject = Integer.parseInt(idProjectString);
		Map<StageBean, List<StageBean>> mapPrecedences = StageDAO.getInstance().getPrecedences(idProject);
		System.out.println(mapPrecedences);
		setLastCritical(mapPrecedences);
		while (!mapPrecedences.isEmpty()) {
			List<StageBean> toRemove = new ArrayList<>();
			for (Map.Entry<StageBean, List<StageBean>> pair : mapPrecedences.entrySet()) {
				System.out.println(pair);
				if (pair.getValue().isEmpty()) {
					System.out.println("non ho precedenti");
					mapES.put(pair.getKey().getIdStage(), 0);
					toRemove.add(pair.getKey());
				} else if (precedencesHasES(pair)) {
					System.out.println("ho precedenti con ES");
					try {
						int ES = (int) computeES(pair.getValue());
						mapES.put(pair.getKey().getIdStage(), (Integer) ES);
						toRemove.add(pair.getKey());
					} catch (ParseException e) {
						LOGGER.log(Level.SEVERE, "Something went wrong during parsing a date", e);
					}
				}
			}
			for (StageBean removable : toRemove) {
				System.out.println("rimouovo gli elementi che hanno ES");
				System.out.println(removable);
				mapPrecedences.remove(removable);
			}
		}
		for (Map.Entry<Integer, Integer> p : mapES.entrySet()) {
			System.out.println("mapES " + p.getValue());
		}
		StageDAO.getInstance().setCriticalStages(criticalStages);
	}

	public static void setLastCritical(Map<StageBean, List<StageBean>> mapPrecedences) {
		for (Map.Entry<StageBean, List<StageBean>> pairKey : mapPrecedences.entrySet()) {
			boolean b = true;
			for (Map.Entry<StageBean, List<StageBean>> pairValue : mapPrecedences.entrySet()) {
				for(StageBean precedence : pairValue.getValue()){
					if (pairValue.getValue() != null && precedence.getIdStage() == pairKey.getKey().getIdStage()) {
						b = false;
					}	
				}	
			}
			if (b) {
				criticalStages.add(pairKey.getKey());
			}
		}
	}

	public static boolean precedencesHasES(Map.Entry<StageBean, List<StageBean>> pair) {
		List<StageBean> precedences = pair.getValue();
		for (StageBean precedence : precedences) {
			if (precedence != null && mapES.get(precedence.getIdStage()) == null) {
				return false;
			}
		}
		return true;
	}

	public static long computeES(List<StageBean> precedences) throws ParseException {
		System.out.println("sono in computeES");
		long max = 0;
		long result;
		List<Long> results = new ArrayList<>();
		System.out.println(precedences);
		for (StageBean precedence : precedences) {
			System.out.println("sono nel for");
			long duration = 8 * UtilityFunctions.getDifferenceDays(format.parse(precedence.getStartDay()),
					format.parse(precedence.getFinishDay()));
			long ES = mapES.get(precedence.getIdStage());
			result = ES + duration;
			results.add(result);

			System.out.println("result: " + result);
			System.out.println("duration: " + duration);
			if (result > max) {
				max = result;
			}
		}
		for (int i = 0; i < results.size(); ++i) {
			if (results.get(i) == max && !criticalStages.contains(precedences.get(i))) {
				criticalStages.add(precedences.get(i));
			}
		}
		
		return max;
	}
}