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

/**
 * This class computes critical stages of a project
 */
public class CriticalPath {

	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	private static Map<Integer, Integer> mapES = new HashMap<>();

	private static List<StageBean> criticalStages = new ArrayList<>();

	private static final Logger LOGGER = Logger.getLogger(CriticalPath.class.getName());

	private CriticalPath() {

	}

	/**
	 * @param idProjectString
	 *            Computes the critical stages of a project
	 */
	public static void computeCriticalStages(String idProjectString) {
		int idProject = Integer.parseInt(idProjectString);
		Map<StageBean, List<StageBean>> mapPrecedences = StageDAO.getInstance().getPrecedences(idProject);
		setLastCritical(mapPrecedences);
		while (!mapPrecedences.isEmpty()) {
			List<StageBean> toRemove = new ArrayList<>();
			for (Map.Entry<StageBean, List<StageBean>> pair : mapPrecedences.entrySet()) {
				if (pair.getValue().isEmpty()) {
					mapES.put(pair.getKey().getIdStage(), 0);
					toRemove.add(pair.getKey());
				} else if (precedencesHasES(pair)) {
					calculatePrecedenceHasES(toRemove, pair);
				}
			}
			for (StageBean removable : toRemove) {
				mapPrecedences.remove(removable);
			}
		}

		StageDAO.getInstance().setCriticalStages(criticalStages);
	}

	/**
	 * @param toRemove
	 * @param pair
	 *            Calculate if a precedence stage of another has got its ES
	 *            (Earliest Start)
	 */
	private static void calculatePrecedenceHasES(List<StageBean> toRemove, Map.Entry<StageBean, List<StageBean>> pair) {
		try {
			int eS = (int) computeES(pair.getValue());
			mapES.put(pair.getKey().getIdStage(), (Integer) eS);
			toRemove.add(pair.getKey());
		} catch (ParseException e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during parsing a date", e);
		}
	}

	/**
	 * @param mapPrecedences
	 */
	public static void setLastCritical(Map<StageBean, List<StageBean>> mapPrecedences) {
		for (Map.Entry<StageBean, List<StageBean>> pairKey : mapPrecedences.entrySet()) {
			boolean b = true;
			b = calculateSetLastCritical(mapPrecedences, pairKey, b);
			if (b) {
				criticalStages.add(pairKey.getKey());
			}
		}
	}

	/**
	 * @param mapPrecedences
	 * @param pairKey
	 * @param b
	 * @return
	 */
	private static boolean calculateSetLastCritical(Map<StageBean, List<StageBean>> mapPrecedences,
			Map.Entry<StageBean, List<StageBean>> pairKey, boolean b) {
		boolean c = b;
		for (Map.Entry<StageBean, List<StageBean>> pairValue : mapPrecedences.entrySet()) {
			for (StageBean precedence : pairValue.getValue()) {
				if (pairValue.getValue() != null && precedence.getIdStage() == pairKey.getKey().getIdStage()) {
					c = false;
				}
			}
		}
		return c;
	}

	/**
	 * @param pair
	 * @return true if a precedence stages of another has got its ES
	 */
	public static boolean precedencesHasES(Map.Entry<StageBean, List<StageBean>> pair) {
		List<StageBean> precedences = pair.getValue();
		for (StageBean precedence : precedences) {
			if (precedence != null && mapES.get(precedence.getIdStage()) == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param precedences
	 * @return
	 * @throws ParseException
	 *             Computes the Earliest Start of a list of stages
	 */
	public static long computeES(List<StageBean> precedences) throws ParseException {
		long max = 0;
		long result;
		List<Long> results = new ArrayList<>();
		for (StageBean precedence : precedences) {
			long duration = 8 * UtilityFunctions.getDifferenceDays(format.parse(precedence.getStartDay()),
					format.parse(precedence.getFinishDay()));
			long es = mapES.get(precedence.getIdStage());
			result = es + duration;
			results.add(result);
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