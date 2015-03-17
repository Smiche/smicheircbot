package com.smi.irc;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class Task420 implements Job{
	public void execute(JobExecutionContext context) throws JobExecutionException {
	   	Main.bot.sendMessage(Main.bot.channel, "420blazeit");
	}
}
