package context;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import context.NotificaGateway;
import models.StageBean;

public class DatabaseSubject extends Subject implements Runnable {
	
	public static void main(String[] args) {
	final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	DatabaseSubject scheduler = new DatabaseSubject();
	service.scheduleAtFixedRate(scheduler, 0, 5, TimeUnit.SECONDS);
	}
	
	public DatabaseSubject() {
		super();
	}

	private List<StageBean> stages = null;

	@Override
	public void run() {
		System.out.println("ci sono");
		NotificaGateway n = new NotificaGateway();
		System.out.println(stages);
		stages = n.getStages();
		System.out.println(stages);
		notifyObservers();
	}

	@Override
	public List<StageBean> getStages() {
		System.out.println("sono in getStages");
		StageBean a = new StageBean();
		stages.add(a);
		return stages;
	}
}
