package context;

import java.util.Observable;

class ObservedObject extends Observable {

	@SuppressWarnings("unused")
	private String watchedValue;

	public ObservedObject(String value) {
		watchedValue = value;
	}

	public void setValue(String value) {

		if ("Ritardo".equalsIgnoreCase(value)) {
			watchedValue = value;
			/* TODO salva nel db */
			setChanged();
		} else if ("RitardoNonCritico".equalsIgnoreCase(value)) {
			watchedValue = value;
			/* TODO salva nel db */
			setChanged();
		} else if ("Inizio".equalsIgnoreCase(value)) {
			watchedValue = value;
			/* TODO salva nel db */
			setChanged();
		}
	}

}