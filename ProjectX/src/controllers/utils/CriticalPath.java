package controllers.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import models.StageBean;
import utils.DbConnection;

public class CriticalPath {

	private static final CriticalPath INSTANCE = new CriticalPath();

	private CriticalPath() {

	}

	public static CriticalPath getInstance() {

		return INSTANCE;
	}

	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	private static Map<Integer, Integer> mapES = new HashMap<Integer, Integer>();

	private static List<StageBean> criticalTasks = new ArrayList<StageBean>();

	public static void computeCriticalTasks(Map<Integer, List<StageBean>> mapPrecedences) {
		while (!mapPrecedences.isEmpty()) {
			Iterator<Map.Entry<Integer, List<StageBean>>> mapIterator = mapPrecedences.entrySet().iterator();
			while (mapIterator.hasNext()) {
				Map.Entry<Integer, List<StageBean>> pair = (Map.Entry<Integer, List<StageBean>>) mapIterator.next();
				if (pair.getValue() == null) {
					mapES.put(pair.getKey(), 0);
					mapPrecedences.remove(pair.getKey());
				} else {
					if (precedencesHasES(pair)) {
						try {
							int ES = (int) computeES(pair);
							mapES.put(pair.getKey(), (Integer) ES);

							mapPrecedences.remove(pair.getKey());

						} catch (ParseException e) {
							// TODO handle with a logger
						}
					}
				}
			}
		}
	}

	public static boolean precedencesHasES(Map.Entry<Integer, List<StageBean>> pair) {
		List<StageBean> precedences = pair.getValue();
		Iterator<StageBean> arrayListIterator = precedences.iterator();
		while (arrayListIterator.hasNext()) {
			StageBean precedence = arrayListIterator.next();
			if (precedence != null && mapES.get(precedence) == null) {
				arrayListIterator.remove();
				return false;
			}
		}
		return true;
	}

	public static long computeES(Map.Entry<Integer, List<StageBean>> pair) throws ParseException {
		long max = 0;
		StageBean critical = null;
		long result = 0;

		for (StageBean precedence : pair.getValue()) {
			long duration = 8 * CalculateAvailableUsers.getDifferenceDays(format.parse(precedence.getStartDay()),
					format.parse(precedence.getFinishDay()));
			long ES = mapES.get(precedence.getIdStage());
			result = ES + duration;

			if (result > max) {
				max = result;
				critical = precedence;
			}
		}
		if (critical != null) {
			criticalTasks.add(critical);
		}
		return result;
	}
}