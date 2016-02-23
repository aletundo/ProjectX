package test;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import controllers.utils.CalculateAvailableUsers;
import models.ProjectBean;
import models.StageBean;
import models.UserBean;

public class CalculateAvailableUsersTest {
	
	@Test
	public void calculateTest()throws ParseException{
		UserBean user = new UserBean();
		user.setIdUser(1);
		Map<Integer, List<Object>> workMap = new HashMap<Integer, List<Object>>();
		StageBean newStage = new StageBean();
		newStage.setStartDay("2016-01-01");
		newStage.setFinishDay("2016-01-07");
		List<Object> works = new ArrayList<Object>();
		
		ProjectBean project = new ProjectBean();
		project.setStart("2016-01-01");
		project.setDeadline("2016-07-01");
		
		StageBean stage = new StageBean();
		stage.setStartDay("2016-01-05");
		stage.setFinishDay("2016-01-06");
		
		works.add(project);
		works.add(stage);

		workMap.put(user.getIdUser(), works);
		
		List<UserBean> available= new ArrayList<UserBean>();
		available = CalculateAvailableUsers.calculate(workMap, newStage);
		System.out.println(available);
		System.out.println(available.get(0).getIdUser());
		
		assertEquals(1, available.size());
		//System.out.println(x);
	}
	
/*	@Test
	public void testCalculateCriticalWork(){
		StageBean newStage = new StageBean();
		StageBean stage = new StageBean();
		ProjectBean project = new ProjectBean();
		DateFormat format = new SimpleDateFormat("YYYY-mm-DD");
		
		newStage.setStartDay("2016-04-04");
		newStage.setFinishDay("2016-06-06");
		
		stage.setStartDay("2016-02-02");
		stage.setFinishDay("2016-05-05");
		
		project.setStart("2016-02-02");
		project.setDeadline("2016-05-05");
		
		try{
		assertTrue(CalculateAvailableUsers.calculateCriticalWork(stage, newStage,format));
		}catch(ParseException e){
			System.err.println("Caught ParseException: " + e.getMessage());
		}
		
		try{
		assertTrue(CalculateAvailableUsers.calculateCriticalWork(project, newStage,format));
		}catch(ParseException e){
			System.err.println("Caught ParseException: " + e.getMessage());
		}
		
		stage.setFinishDay("2016-03-03");
		
		try{
		assertFalse(CalculateAvailableUsers.calculateCriticalWork(stage, newStage,format));
		}catch(ParseException e){
			System.err.println("Caught ParseException: " + e.getMessage());
		}
		
		
		project.setStart("2016-05-05");
		project.setDeadline("2016-08-08");
		
		try{
		assertTrue(CalculateAvailableUsers.calculateCriticalWork(project, newStage,format));
		}catch(ParseException e){
			System.err.println("Caught ParseException: " + e.getMessage());
		}
		
	}
	
	
	
	/*@Test
	public void calculateAvailabilityTest() throws ParseException{
		List<Object> criticalWorks = new ArrayList<Object>();
		StageBean newStage = new StageBean();
		StageBean stage = new StageBean();
		ProjectBean project = new ProjectBean();
		DateFormat format = new SimpleDateFormat("YYYY-mm-DD");
		
		newStage.setStartDay("2016-02-10");
		newStage.setFinishDay("2016-02-14");
		
		stage.setStartDay("2016-02-08");
		stage.setFinishDay("2016-02-12");
		
		project.setStart("2016-02-13");
		project.setDeadline("2016-02-18");
		
		criticalWorks.add(stage);
		
		assertEquals(38,CalculateAvailableUsers.calculateAvailability(criticalWorks,newStage));
	}
	*/
	
	//@Test
	/*public void getDifferenceDaysTest()throws ParseException{
		Date d1 = new Date();
		Date d2 = new Date();
		DateFormat format = new SimpleDateFormat("YYYY-mm-DD");
		
		String data1 = "2016-02-02";
		String data2 = "2016-03-03";
		
		assertEquals(60000,CalculateAvailableUsers.getDifferenceDays(format.parse(data1), format.parse(data2)));
	}
	
	*/
}
