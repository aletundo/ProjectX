package context;

import java.util.ArrayList;
import java.util.List;

import models.StageBean;

public abstract class Subject {

    protected List<Observer> observers;

    public Subject() {
        observers = new ArrayList<>();
    }

    public Subject(Subject subject) {
        this.observers = subject.observers;
    }

    public void attach(Observer observer) {
        if (observers != null) {
            observers.add(observer);
        }
    }

    public void dettach(Observer observer) {
        if (observer != null) {
            observers.remove(observer);
        }
    }

    // public void notifyObservers() {
    // for (Observer observer : observers) {
    // observer.update();
    // }
    // }

    public abstract List<StageBean> getStagesNonCritical();

    public abstract List<StageBean> getStagesCritical();

}
