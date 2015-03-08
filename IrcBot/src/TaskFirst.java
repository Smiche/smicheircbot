import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class TaskFirst implements Job{
	public void execute(JobExecutionContext context) throws JobExecutionException {
	    // Say Hello to the World and display the date/time
	   	Main.bot.sendMessage(Main.bot.channel, "eka :)");
	}
}
