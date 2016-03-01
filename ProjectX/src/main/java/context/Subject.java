package context;

import java.util.ArrayList;
import java.util.List;

import models.StageBean;

public abstract class Subject {
	
	protected List<Observer> observers;
	
	public Subject(){
		System.out.println("Sono in subject");
		observers = new ArrayList<Observer>();
	}
	
	public Subject(Subject subject){
		System.out.println("Sono in subject con parametro");
		this.observers = subject.observers;
	}
	
	public void attach(Observer observer){
		System.out.println("Sono attach");
		if(observers != null){			
			System.out.println("Mi sono attaccato");

			observers.add(observer);
		}
	}
	public void dettach(Observer observer){
		System.out.println("Sono attach");
		if(observer != null){
			System.out.println("Mi sono staccato");

			observers.remove(observer);
		}
	}
	
	public void notifyObservers(){
		System.out.println("Sono in notyfyAll");
		System.out.println(observers);

		for (Observer observer : observers) {
			System.out.println(observer);

			System.out.println("Sono nel for di notyfyAll");
			System.out.println(observer);
			observer.update();
		}
	}
	
	public abstract List<StageBean> getStages();
}
