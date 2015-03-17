package com.smi.irc;
import org.nikki.omegle.Omegle;
import org.nikki.omegle.core.OmegleException;
import org.nikki.omegle.core.OmegleMode;
import org.nikki.omegle.core.OmegleSession;
import org.nikki.omegle.event.OmegleEventAdaptor;


public class OmBot {
	Omegle omegle;
	OmegleSession session;
	String channel;
	boolean stopped = true;
	public OmBot(String channel){
		omegle = new Omegle();
		this.channel = channel;
	}
	
	public void newConversation() throws OmegleException{
		stopped = false;
		session = omegle.openSession(OmegleMode.NORMAL, new OmegleEventAdaptor() {
			@Override
			public void chatWaiting(OmegleSession session) {
				System.out.println("Waiting for chat...");
			}

			@Override
			public void chatConnected(OmegleSession session) {
				Main.bot.sendMsg(channel, "You are now talking to a random stranger!");
			}

			@Override
			public void chatMessage(OmegleSession session, String message) {
				Main.bot.sendMsg(channel,"Stranger: " + message);
			}

			@Override
			public void messageSent(OmegleSession session, String string) {
				System.out.println("You: " + string);
			}

			@Override
			public void strangerDisconnected(OmegleSession session) {
				Main.bot.sendMsg(channel,"Stranger disconnected, goodbye!");
			}

			@Override
			public void omegleError(OmegleSession session, String string) {
				Main.bot.sendMsg(channel, "ERROR! " + string);
			}
		});
	}
	
	public void stop(){
		stopped = true;
		session = null;
		
	}
	public void sendOm(String msg) throws OmegleException{
		if(!stopped)
		session.send(msg, true);
	}
}
