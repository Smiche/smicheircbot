package com.smi.irc;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.quartz.JobDetail;
import org.quartz.Scheduler; 
import org.quartz.SchedulerException; 
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory; 

import static org.quartz.JobBuilder.*; 
import static org.quartz.TriggerBuilder.*; 
import static org.quartz.SimpleScheduleBuilder.*;

public class DailyTask{
  
  public static void setTask() throws SchedulerException{
	  SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory(); 
	  Scheduler sched = schedFact.getScheduler(); 
	  sched.start(); 
	  // define the job and tie it to our HelloJob class 
	  JobDetail job = newJob(Task420.class) 
	      .withIdentity("myJob", "group1") 
	      .build(); 
	  // Trigger the job to run now, and then every 40 seconds 
	  Date date = new Date();
	  date.setHours(4);
	  date.setMinutes(20);
	  date.setSeconds(0);
	  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	  System.out.println(dateFormat.format(date));
	  Trigger trigger = newTrigger() 
	      .withIdentity("myTrigger", "group1") 
	      .startAt(date)
	      .withSchedule(simpleSchedule() 
	          .withIntervalInHours(12)
	          .repeatForever()) 
	      .build(); 
	  // Tell quartz to schedule the job using our trigger 
	  sched.scheduleJob(job, trigger);
  }
  public static void setTask2() throws SchedulerException{
	  SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory(); 
	  Scheduler sched = schedFact.getScheduler(); 
	  sched.start(); 
	  // define the job and tie it to our HelloJob class 
	  JobDetail job = newJob(TaskFirst.class) 
	      .withIdentity("myJob", "group2") 
	      .build(); 
	  // Trigger the job to run now, and then every 40 seconds 
	  Date date = new Date();
	  date.setHours(12);
	  date.setMinutes(0);
	  date.setSeconds(0);
	  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	  System.out.println(dateFormat.format(date));
	  Trigger trigger = newTrigger() 
	      .withIdentity("myTrigger", "group2") 
	      .startAt(date)
	      .withSchedule(simpleSchedule() 
	          .withIntervalInHours(12)
	          .repeatForever()) 
	      .build(); 
	  // Tell quartz to schedule the job using our trigger 
	  sched.scheduleJob(job, trigger);
  }
}