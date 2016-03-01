package context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import models.StageBean;
import context.ObservedObject;
import controllers.utils.UtilityFunctions;

public class ObserverVero implements Observer {

	public ObserverVero() {
	}

	public static void main(String[] args) throws ParseException {
		// create watched and watcher objects
		ObservedObject watched = new ObservedObject("Original Value");
		// watcher object listens to object change
		ObserverVero watcher = new ObserverVero();
		// add observer to the watched object
		watched.addObserver(watcher);

		long criticalDate = 0;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		date = format.parse(UtilityFunctions.GetCurrentDateTime());

		StageBean a = new StageBean();
		StageBean b = new StageBean();
		StageBean c = new StageBean();
		StageBean d = new StageBean();

		a.setIdStage(1);
		b.setIdStage(2);
		c.setIdStage(3);
		d.setIdStage(4);

		a.setCritical("True");
		b.setCritical("False");
		c.setCritical("True");
		d.setCritical("False");

		a.setStartDay("2016-02-29");
		b.setStartDay("2016-01-06");
		c.setStartDay("2016-01-07");
		d.setStartDay("2016-01-12");

		a.setFinishDay("2016-01-05");
		b.setFinishDay("2016-01-11");
		c.setFinishDay("2016-01-08");
		d.setFinishDay("2016-01-14");

		List<StageBean> stages = new ArrayList<>();
		stages.add(a);
		stages.add(b);
		stages.add(c);
		stages.add(d);
		for (StageBean stage : stages) {

			try {
				criticalDate = getDifferenceDays(format.parse(stage.getFinishDay()),
						format.parse(UtilityFunctions.GetCurrentDateTime()));
				System.out.println(criticalDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (criticalDate < 0 && stage.getRateWorkCompleted() < 100 && "False".equalsIgnoreCase(
					stage.getCritical())/*
										 * && !"RitardoNonCritico". equals(stage
										 * .getStatus)
										 */) {
				System.out.println("Sono in ritardoNonCritico");

				watched.setValue("RitardoNonCritico");
			} else if (criticalDate < 0 && stage.getRateWorkCompleted() < 100 && "True".equalsIgnoreCase(stage
					.getCritical()) /* && !"Ritardo".equals(stage.getStatus) */) {

				System.out.println("Sono in ritardo");

				watched.setValue("Ritardo");
			} else if (date == (format.parse(stage.getFinishDay()
			/* && !"Inizio".equals(stage.getStatus) */))) {

				System.out.println("Sono in inizio");

				watched.setValue("Inizio");
			}
			// }
		}

		// check if value has changed
		if (watched.hasChanged())
			System.out.println("Value changed");
		else
			System.out.println("Value not changed");

	}

	public void update1(Observable obj, Object arg) {
		System.out.println("Update called");
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	public static long getDifferenceDays(Date d1, Date d2) {
		long diff = d1.getTime() - d2.getTime();
		if (diff < 0) {
			return (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)) - 1;
		}
		if (diff > 0) {
			return (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)) + 1;
		}
		return (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
	}
}