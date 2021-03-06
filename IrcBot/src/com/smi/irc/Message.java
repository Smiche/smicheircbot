package com.smi.irc;
import java.sql.Timestamp;
import java.util.Date;

public class Message {
	String sender;
	String message;
	Timestamp timestamp;
	String timeString;
	String fullLog;
	public Message(String from, String text) {
		this.sender = from;
		this.message = text;
		Date date = new Date();
		this.timestamp = new Timestamp(date.getTime());
		this.timeString = this.timestamp.toString();
		fullLog = "<" + timeString+ ">"+ sender+": "+message;
	}
}
