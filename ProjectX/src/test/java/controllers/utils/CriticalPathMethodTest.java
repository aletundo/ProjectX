package controllers.utils;

import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controllers.utils.CriticalPath;
import models.StageBean;

public class CriticalPathMethodTest {
    @Test
    public void CriticalPath() throws ParseException {
        Map<StageBean, List<StageBean>> map = new HashMap<>();
        List<StageBean> precedencesA = new ArrayList<>();
        List<StageBean> precedencesB = new ArrayList<>();
        List<StageBean> precedencesC = new ArrayList<>();
        List<StageBean> precedencesD = new ArrayList<>();

        StageBean a = new StageBean();
        StageBean b = new StageBean();
        StageBean c = new StageBean();
        StageBean d = new StageBean();

        a.setIdStage(1);
        b.setIdStage(2);
        c.setIdStage(3);
        d.setIdStage(4);

        a.setStartDay("2016-01-01");
        b.setStartDay("2016-01-06");
        c.setStartDay("2016-01-07");
        d.setStartDay("2016-01-12");

        a.setFinishDay("2016-01-05");
        b.setFinishDay("2016-01-11");
        c.setFinishDay("2016-01-08");
        d.setFinishDay("2016-01-14");

        precedencesB.add(a);
        precedencesC.add(a);
        precedencesD.add(b);
        precedencesD.add(c);

        map.put(a, precedencesA);
        map.put(b, precedencesB);
        map.put(c, precedencesC);
        map.put(d, precedencesD);

        CriticalPath.computeCriticalStages("60");

    }
}
