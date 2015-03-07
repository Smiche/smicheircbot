import com.gtranslate.Translator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import main.java.com.google.code.chatterbotapi.ChatterBot;
import main.java.com.google.code.chatterbotapi.ChatterBotFactory;
import main.java.com.google.code.chatterbotapi.ChatterBotSession;
import main.java.com.google.code.chatterbotapi.ChatterBotType;

import org.jibble.pircbot.PircBot;
import org.nikki.omegle.core.OmegleException;

public class SmicheBot extends PircBot {
	ChatterBot bot1;
	ChatterBotSession botSession;
	Translator translate;
	ArrayList<Message> log;
	int logLines;
	OmBot om;
	static String channel;
	Thread twitchThread;
	ArrayList<String> news;
	ArrayList<TwitchStream> streams;
	Runnable twitchChecker;

	public SmicheBot(String name, String channel) {
		news = new ArrayList<String>();
		om = new OmBot(channel);
		this.channel = channel;
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
		streams.add(new TwitchStream("z3t0r1"));
		streams.add(new TwitchStream("exovaldo"));
		streams.add(new TwitchStream("Aiz0r"));
		twitchChecker = new Runnable() {

			public void run() {
				String chan = SmicheBot.channel;
				while (true)
					try {
						Thread.sleep(45000);
						for (int i = 0; i < streams.size(); i++) {
							sendGet((streams.get(i)).channel, i, chan);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
			}

			private void sendGet(String stream, int num, String chan)
					throws Exception {
				String url = "https://api.twitch.tv/kraken/streams/" + stream;
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj
						.openConnection();

				con.setRequestMethod("GET");

				con.setRequestProperty("User-Agent", "Mozilla/5.0");

				int responseCode = con.getResponseCode();
				System.out.println("\nSending 'GET' request to URL : " + url);
				// txtLog("sending get to url: " + url);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));

				StringBuffer response = new StringBuffer();
				String line;
				if ((line = in.readLine()).contains("\"stream\":null")) {
					// txtLog("isNull!");
					// txtLog(line);
					streams.get(num).setOffline();
				} else if (!streams.get(num).isOnline) {
					// txtLog(line);
					// txtLog("isOnline!");
					sendMessage(chan, stream + "'s stream is up!"
							+ " The stream link: " + "http://www.twitch.tv/"
							+ streams.get(num).channel);
					streams.get(num).setOnline();
				}
				in.close();
				con.disconnect();
			}
		};

		twitchThread = new Thread(twitchChecker);
		twitchThread.start();
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

	public void sendMsg(String channel, String message) {
		sendMessage(channel, message);
	}

	public void onMessage(String channel, String sender, String login,
			String hostname, String message) {
		if (channel.equals("#uutiset")) {
			news.add(message);
			return;
		} else {
			if (!message.startsWith("<")) {
				writeLog(new Message(sender, message));
				if (log.size() < 120) {
					log.add(new Message(sender, message));
				} else {
					log.remove(0);
					log.remove(0);
					log.remove(0);
					log.remove(0);
					log.remove(0);
				}
				return;
			}
			if (message.equalsIgnoreCase("<uutiset")) {
				if (news.size() > 0)
					sendMessage(channel, news.get(news.size() - 1));
			} else if(message.equalsIgnoreCase("<history")){
					sendMessage(channel, PastebinLog.send());
			} else if (message.startsWith("<addstream ")) {
				String msg = message.replaceAll("<addstream ", "");
				if (msg != null) {
					streams.add(new TwitchStream(msg));
				}

			} else if (message.startsWith("<uutiset ")) {
				String numText = message.replaceAll("<uutiset ", "");
				int num = 0;
				try {
					num = Integer.parseInt(numText);
					sendMessage(channel, news.get(num));
				} catch (Exception e) {

				}
			} else if (message.equalsIgnoreCase("<time")) {
				String time = new Date().toString();
				sendMessage(channel, sender + ": The time is now " + time);
				return;
			} else if (message.startsWith("<talk")) {
				String s = message.replaceAll("<talk ", "");
				String response = "not reachable!!!";
				try {
					response = botSession.think(s);
				} catch (Exception e) {
					e.printStackTrace();
				}
				sendMessage(channel, response);
				return;
			} else if (message.equalsIgnoreCase("<help")) {
				sendMessage(
						channel,
						"Supported commands are: time, uutiset, talk, translate, streams, squote, pquote, getlog, 9gag, o new, o, o stop, history, addstream");
				return;
			} else if (message.equalsIgnoreCase("<streams")) {
				sendMessage(channel, getStreams());
			} else if (message.startsWith("<o ")) {
				if (message.equalsIgnoreCase("<o new")) {
					try {
						om.newConversation();
					} catch (OmegleException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (message.equalsIgnoreCase("<o stop")) {
					om.stop();
				} else {
					String msg = message.replaceAll("<o ", "");
					try {
						om.sendOm(msg);
					} catch (OmegleException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return;
			} else if (message.equalsIgnoreCase("<9gag")) {
				try {
					sendMessage(channel, getGag());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (message.startsWith("<squote")) {
				String init = message.replaceAll("<squote ", "");
				try {
					String[] msg = init.split(",");
					String name = msg[0];
					int goal = Integer.parseInt(msg[1]);
					int atIndex = 0;
					for (int i = log.size() - 1; i > 0; i--) {
						if ((log.get(i)).sender.equalsIgnoreCase(name)) {

							if (atIndex == goal) {
								writeQuote(log.get(i));
								sendMessage(channel,
										"Quote saved with number: " + logLines);
								break;
							}
							atIndex++;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			} else if (message.startsWith("<pquote")) {
				try {
					String text = message.replaceAll("<pquote ", "");
					int num = Integer.parseInt(text);
					sendMessage(channel, getLine(num));
				} catch (Exception localException1) {
				}
				return;
			} else if ((message.startsWith("<translate"))
					|| (message.equalsIgnoreCase("<translate"))) {
				String txt = message.replaceAll("<translate ", "");
				if (txt.equalsIgnoreCase("fi")) {
					try {
						String msg = (log.get(log.size() - 1)).message;
						System.out.println("message is:" + msg);
						String text = translate.translate(msg, "en", "fi");
						sendMessage(channel, (log.get(log.size() - 1)).sender
								+ ": " + text);
					} catch (Exception localException2) {
					}
				} else if (txt.equalsIgnoreCase("en")) {
					try {
						String msg = (log.get(log.size() - 1)).message;
						System.out.println("message is:" + msg);
						String text = translate.translate(msg, "fi", "en");
						sendMessage(channel, (log.get(log.size() - 1)).sender
								+ ": " + text);
					} catch (Exception localException2) {
					}
				} else {

					try {
						String msg = (log.get(log.size() - 1)).message;
						System.out.println("message is:" + msg);
						String text = translate.translate(msg, "fi", "en");
						sendMessage(channel, (log.get(log.size() - 1)).sender
								+ ": " + text);
					} catch (Exception localException2) {
					}
				}
				return;
			} else if (message.startsWith("<getlog")) {
				String integer = message.replaceAll("<getlog ", "");
				try {
					int num = Integer.parseInt(integer);
					sendMessage(channel,
							(log.get(num)).sender + ": "
									+ (log.get(num)).message);
				} catch (Exception localException3) {
				}
			}
			return;
		}
	}

	public String getStreams() {
		String a = "Online streams are:";
		for (int i = 0; i < streams.size(); i++) {
			if (streams.get(i).isOnline) {
				a += " " + streams.get(i).channel;
			}
		}

		return a;

	}

	public void writeQuote(Message message) {
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
	
	public void writeLog(Message message) {
		PrintWriter pw = null;
		try {
			File file = new File("log.txt");
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

	public String getGag() throws IOException {
		URLConnection con = new URL("http://www.9gag.com/random")
				.openConnection();
		System.out.println("orignal url: " + con.getURL());
		con.connect();
		System.out.println("connected url: " + con.getURL());
		InputStream is = con.getInputStream();
		String msg = "" + con.getURL();
		is.close();
		return msg;
	}

	public void txtLog(String text) {
		PrintWriter pw = null;
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		try {
			File file = new File("logs.txt");
			FileWriter fw = new FileWriter(file, true);
			pw = new PrintWriter(fw);
			pw.println("<" + timestamp.toString() + ">" + text);
			// logLines += 1;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

}
