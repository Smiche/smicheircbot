import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

public class Main extends JFrame
{
	static SmicheBot bot;
	
  public static void main(String[] args)
    throws Exception
  {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    frame.add(panel);
    frame.setBounds(400, 300, 100, 100);
    frame.setDefaultCloseOperation(3);
    frame.setVisible(true);
    
    

  }
  
  static void startBot(String network, String nick ,String channel) throws Exception{ //smooth
	    bot = new SmicheBot(nick);
	    bot.setVerbose(true);
	    bot.connect(network);
	    bot.joinChannel(channel);
  }
}
