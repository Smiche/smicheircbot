import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class DailyTask extends TimerTask {
  Thread myThreadObj;
  DailyTask (Thread t){
   this.myThreadObj=t;
  }
  public void run() {
   myThreadObj.start();
  }
  
  public static void setTask(){
	  Timer timer = new Timer();
	  Thread myThread= new Thread(new Runnable(){

		@Override
		public void run() {
			Main.bot.sendMsg("#kukko", "420blazeit");
			return;
		}
		  
	  });// Your thread
	  Calendar date = Calendar.getInstance();
	  date.set(
	    Calendar.DAY_OF_WEEK,Calendar.AM_PM
	   // Calendar.
	  );
	  date.set(Calendar.HOUR,4);
	  date.set(Calendar.MINUTE, 20);
	  date.set(Calendar.SECOND, 0);
	  date.set(Calendar.MILLISECOND, 0);
	  // Schedule to run every Sunday in midnight
	  timer.schedule(
	    new DailyTask (myThread),
	    date.getTime(),
	    1000 * 60 * 60 * 24 *7
	  );
  }
}