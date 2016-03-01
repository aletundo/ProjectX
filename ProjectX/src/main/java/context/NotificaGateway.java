package context;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import models.StageBean;

public class NotificaGateway {
	
	List<StageBean> stages = new ArrayList<>();

	/**
	 * Cerca tutti i clienti che non acquistano da più di 2 anni, infine li elimina se non comprano da più di 3 anni
	 * @return Una lista contenente gli oggetti ClienteRegistrato che sono considerati "infedeli"
	 */
	public List<StageBean> getStages(){
		System.out.println("sono in getStages");
		StageBean a = new StageBean();
		stages.add(a);
		return stages;
	}
}