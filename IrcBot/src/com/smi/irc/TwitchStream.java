package com.smi.irc;
public class TwitchStream {
	boolean isOnline = false;
	String channel;

	public TwitchStream(String chan) {
		this.channel = chan;
	}
	
	public void setOffline(){
		isOnline = false;
	}
	
	public void setOnline(){
		isOnline = true;
	}
}
