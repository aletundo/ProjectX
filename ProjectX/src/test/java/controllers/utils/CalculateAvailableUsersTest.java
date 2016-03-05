package controllers.utils;

import static org.junit.Assert.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import controllers.utils.CalculateAvailableUsers;
import models.StageBean;
import models.TaskBean;
import models.UserBean;
import models.UserDAO;

public class CalculateAvailableUsersTest {

    @Test
    public void calculateTest() throws ParseException {
        //Instantiation of the users 
        UserBean user1 = new UserBean();
        user1.setIdUser(16);
        
        UserBean user2 = new UserBean();
        user2.setIdUser(11);
        
        //Instantiation of the works to assign 
        StageBean newStage = new StageBean();
        newStage.setStartDay("2016-03-27");
        newStage.setFinishDay("2016-03-30");

        TaskBean newTask = new TaskBean(); 
        newTask.setStartDay("2016-01-01");
        newTask.setFinishDay("2016-01-07");
        
        Map<Integer, List<Object>> workMap1 = UserDAO.getInstance().getCandidateSupervisors();
        Map<Integer, List<Object>> workMap2 = UserDAO.getInstance().getCandidateDevelopers();

        List<UserBean> availableForStage = new ArrayList<>();
        List<UserBean> availableForTask= new ArrayList<>();

        availableForStage = CalculateAvailableUsers.calculate(workMap1, newStage);
        availableForTask = CalculateAvailableUsers.calculate(workMap2, newTask);

        
        assertEquals(user1.getIdUser(), availableForStage.get(0).getIdUser());
        assertEquals(user2.getIdUser(), availableForStage.get(1).getIdUser()); 
        
        assertEquals(user1.getIdUser(), availableForTask.get(0).getIdUser());
        assertNotEquals(user1.getIdUser(), availableForTask.get(1).getIdUser()); 
        
        
    }

}
