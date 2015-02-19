import com.gtranslate.Translator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import main.java.com.google.code.chatterbotapi.ChatterBot;
import main.java.com.google.code.chatterbotapi.ChatterBotFactory;
import main.java.com.google.code.chatterbotapi.ChatterBotSession;
import main.java.com.google.code.chatterbotapi.ChatterBotType;
import org.jibble.pircbot.PircBot;

public class SmicheBot extends PircBot {
	ChatterBot bot1;
	ChatterBotSession botSession;
	Translator translate;
	ArrayList<Message> log;
	int logLines;

	public SmicheBot(String name) {
		new Thread(new Runnable() {
			ArrayList<TwitchStream> streams;

			public void run() {
				streams = new ArrayList();
				streams.add(new TwitchStream("smichess"));
				streams.add(new TwitchStream("budheim"));
				streams.add(new TwitchStream("dmamlol"));
				streams.add(new TwitchStream("verikukko0123"));
				streams.add(new TwitchStream("kevytmaito"));
				streams.add(new TwitchStream("lkt287"));
				streams.add(new TwitchStream("timothylol"));
				streams.add(new TwitchStream("mosttoxiceu"));
				streams.add(new TwitchStream("kermalese"));
				streams.add(new TwitchStream("dissutus"));
				streams.add(new TwitchStream("pate5"));
				streams.add(new TwitchStream("binaere"));
				streams.add(new TwitchStream("evilwalruz"));
				streams.add(new TwitchStream("harshmouse"));
				streams.add(new TwitchStream("jushiu"));
				streams.add(new TwitchStream("Huikeemasaj"));
				streams.add(new TwitchStream("exovaldo"));
				streams.add(new TwitchStream("Aiz0r"));
				try {
					for (;;) {
						Thread.sleep(45000L);
						for (int i = 0; i < streams.size(); i++) {
							sendGet((streams.get(i)).channel, i);
						}
						Thread.sleep(45000L);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			private void sendGet(String stream, int num) throws Exception {
				String url = "https://api.twitch.tv/kraken/streams/" + stream;
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj
						.openConnection();

				con.setRequestMethod("GET");

				con.setRequestProperty("User-Agent", "Mozilla/5.0");

				int responseCode = con.getResponseCode();
				System.out.println("\nSending 'GET' request to URL : " + url);

				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));

				StringBuffer response = new StringBuffer();
				if (in.readLine().contains("\"stream\":null")) {
					(streams.get(num)).isOnline = false;
				} else if (!(streams.get(num)).isOnline) {
					sendMessage("#kukko", stream + "'s stream is up!"
							+ " The stream link: " + "http://www.twitch.tv/"
							+ streams.get(num).channel);
					(streams.get(num)).isOnline = true;
				}
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			}
		}) {
		}.start();
		countLines();
		log = new ArrayList();
		setName(name);
		ChatterBotFactory factory = new ChatterBotFactory();
		translate = Translator.getInstance();
		bot1 = null;
		try {
			bot1 = factory.create(ChatterBotType.CLEVERBOT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		botSession = bot1.createSession();
		try {
			System.out.println(botSession.think("hi"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onMessage(String channel, String sender, String login,
			String hostname, String message) {
		if (!message.startsWith("<")) {
			if (log.size() < 120) {
				log.add(new Message(sender, message));
			} else {
				log.remove(0);
				log.remove(0);
				log.remove(0);
				log.remove(0);
				log.remove(0);
			}
		}
		if (message.equalsIgnoreCase("<time")) {
			String time = new Date().toString();
			sendMessage(channel, sender + ": The time is now " + time);
		}
		if (message.startsWith("<talk")) {
			String s = message.replaceAll("<talk ", "");
			String response = "not reachable!!!";
			try {
				response = botSession.think(s);
			} catch (Exception e) {
				e.printStackTrace();
			}
			sendMessage(channel, response);
		}
		if (message.equalsIgnoreCase("<help")) {
			sendMessage(channel, "Supported commands are: time, talk, translate, squote, pquote, getlog");
		}
		if (message.startsWith("<squote")) {
			String name = message.replaceAll("<squote ", "");
			try{
			String[] msg = message.split(",");
			int goal = Integer.parseInt(msg[1]);
			int atIndex = 0;
			for (int i = log.size() - 1; i > 0; i--) {
				if ((log.get(i)).sender.equalsIgnoreCase(name)) {
					atIndex++;
					if(atIndex==goal){
					writeLog(log.get(i));
					sendMessage(channel, "Quote saved with number: " + logLines);
					break;
					}
					
				}
			}
			} catch (Exception e){
				
			}
		}
		if (message.startsWith("<pquote")) {
			try {
				String text = message.replaceAll("<pquote ", "");
				int num = Integer.parseInt(text);
				sendMessage(channel, getLine(num));
			} catch (Exception localException1) {
			}
		}
		if ((message.startsWith("<translate"))
				|| (message.equalsIgnoreCase("<translate"))) {
			String txt = message.replaceAll("<translate ", "");
			if(txt.equalsIgnoreCase("fi")){
				try {
					String msg = (log.get(log.size() - 1)).message;
					System.out.println("message is:" + msg);
					String text = translate.translate(msg, "en", "fi");
					sendMessage(channel, (log.get(log.size() - 1)).sender + ": "
							+ text);
				} catch (Exception localException2) {
				}
			} else if(txt.equalsIgnoreCase("en")){
				try {
					String msg = (log.get(log.size() - 1)).message;
					System.out.println("message is:" + msg);
					String text = translate.translate(msg, "fi", "en");
					sendMessage(channel, (log.get(log.size() - 1)).sender + ": "
							+ text);
				} catch (Exception localException2) {
				}
			} else {
			
			try {
				String msg = (log.get(log.size() - 1)).message;
				System.out.println("message is:" + msg);
				String text = translate.translate(msg, "fi", "en");
				sendMessage(channel, (log.get(log.size() - 1)).sender + ": "
						+ text);
			} catch (Exception localException2) {
			}
		}
		}
		if (message.startsWith("<getlog")) {
			String integer = message.replaceAll("<getlog ", "");
			try {
				int num = Integer.parseInt(integer);
				sendMessage(channel,
						(log.get(num)).sender + ": " + (log.get(num)).message);
			} catch (Exception localException3) {
			}
		}
	}

	public void writeLog(Message message) {
		PrintWriter pw = null;
		try {
			File file = new File("quotes.txt");
			FileWriter fw = new FileWriter(file, true);
			pw = new PrintWriter(fw);
			pw.println("<" + message.timeString + ">" + message.sender + ": "
					+ message.message);
			logLines += 1;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	public void countLines() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					"quotes.txt"));
			int lines = 0;
			while (reader.readLine() != null) {
				lines++;
			}
			reader.close();
			logLines = lines;
		} catch (Exception localException) {
		}
	}

	public String getLine(int line) {
		try {
			FileReader fr = new FileReader("quotes.txt");
			BufferedReader br = new BufferedReader(fr);
			LineNumberReader lnr = new LineNumberReader(br);
			String out;
			while ((out = lnr.readLine()) != null) {
				if (lnr.getLineNumber() == line) {
					return out;
				}
			}
		} catch (Exception localException) {
		}
		return "not found";
	}
}
