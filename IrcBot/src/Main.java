import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main
{
  public static void main(String[] args)
    throws Exception
  {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    frame.add(panel);
    frame.setBounds(100, 100, 100, 100);
    frame.setDefaultCloseOperation(3);
    frame.setVisible(true);
    
    SmicheBot bot = new SmicheBot();
    

    bot.setVerbose(true);
    
    bot.connect("irc.quakenet.org");
    

    bot.joinChannel("#kukko");
  }
}
