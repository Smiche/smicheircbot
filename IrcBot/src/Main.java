import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Main extends JFrame
{	
	static Main frame;
	static TrayIcon icon;
	public Main() {
		setTitle("SmiBot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setFont(new Font("Aharoni", Font.PLAIN, 12));
		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 35, 274, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNetwork = new JLabel("Network:");
		lblNetwork.setForeground(Color.GREEN);
		lblNetwork.setBounds(10, 11, 274, 20);
		getContentPane().add(lblNetwork);
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 97, 274, 20);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblChannel = new JLabel("Channel:");
		lblChannel.setForeground(Color.GREEN);
		lblChannel.setBounds(10, 66, 274, 20);
		getContentPane().add(lblChannel);
		
		JLabel lblBotNick = new JLabel("Bot nick:");
		lblBotNick.setForeground(Color.GREEN);
		lblBotNick.setBounds(10, 128, 274, 20);
		getContentPane().add(lblBotNick);
		
		textField_2 = new JTextField();
		textField_2.setBounds(10, 159, 274, 20);
		getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnNewButton = new JButton("Start");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					startBot(textField.getText(),textField_2.getText(),textField_1.getText());
					textField.setEditable(false);
					textField_1.setEditable(false);
					textField_2.setEditable(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(10, 208, 89, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Stop");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				stopBot();
				textField.setEditable(true);
				textField_1.setEditable(true);
				textField_2.setEditable(true);
			}
		});
		btnNewButton_1.setBounds(195, 208, 89, 23);
		getContentPane().add(btnNewButton_1);
	}
	static SmicheBot bot;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	
  public static void main(String[] args)
    throws Exception
  {
   UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");


   
   frame = new Main();
      
   icon = new TrayIcon(getIcon(),
           
           "SmiBot");

   icon.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
          frame.setVisible(true);
      }

   });
   
   SystemTray.getSystemTray().add(icon);
   
   frame.setBounds(310,280,310,280);
   frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
   frame.setIconImage(getIcon());
   frame.setResizable(false);
   frame.addWindowListener(new WindowListener() {
       public void windowClosed(WindowEvent arg0) {
               frame.setVisible(false);
       }
       public void windowActivated(WindowEvent arg0) {
       }
       public void windowClosing(WindowEvent arg0) {
               frame.setVisible(false);
       }
       public void windowDeactivated(WindowEvent arg0) {
       }
       public void windowDeiconified(WindowEvent arg0) {
       }
       public void windowIconified(WindowEvent arg0) {
       }
       public void windowOpened(WindowEvent arg0) {
       }
   });
   frame.setVisible(true);
   
  }
  
  private static Image getIcon() throws HeadlessException {
      Image img = null;
      try {
              img = ImageIO.read(Main.class.getResource("\\resources\\icon.png"));
      } catch (IOException e2) {
              e2.printStackTrace();
      }
      
      return img;
      
      
}
  
  static void startBot(String network, String nick ,String channel) throws Exception{ //smooth
	    bot = new SmicheBot(nick);
	    bot.setVerbose(true);
	    bot.connect(network);
	    bot.joinChannel(channel);
  }
  static void stopBot(){
	  bot.disconnect();
	  bot.dispose();
	  bot = null;
  }
}
