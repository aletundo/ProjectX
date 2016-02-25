package controllers.utils;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchedulerEvents
{ 
  public static void main(String[] args)
  {
    final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    service.scheduleAtFixedRate(new Runnable()
      {
        @Override
        public void run()
        {
          System.out.println(new Date());
          /*TODO CHANGING TO SEND EMAIL*/
        }
      }
    , 0, 24, TimeUnit.HOURS);
  }
}