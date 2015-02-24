import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TextArea;
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

import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class Main extends JFrame {
	static Main frame;
	static TrayIcon icon;
	static String chan;
	public Main() {
		setTitle("SmiBot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setFont(new Font("Aharoni", Font.PLAIN, 12));
		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(-1, 0, 306, 254);
		getContentPane().add(tabbedPane);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		tabbedPane.addTab("Settings", null, panel, null);
		panel.setLayout(null);

		txtIrcquakenetorg = new JTextField();
		txtIrcquakenetorg.setText("irc.quakenet.org");
		txtIrcquakenetorg.setBounds(7, 35, 114, 20);
		txtIrcquakenetorg.setColumns(10);
		panel.add(txtIrcquakenetorg);

		JLabel label = new JLabel("Network:");
		label.setBounds(10, 7, 111, 17);
		label.setForeground(Color.GREEN);
		panel.add(label);

		txtkukko = new JTextField();
		txtkukko.setText("#kukko");
		txtkukko.setBounds(7, 124, 114, 20);
		txtkukko.setColumns(10);
		panel.add(txtkukko);

		JLabel label_1 = new JLabel("Channel:");
		label_1.setBounds(7, 96, 114, 17);
		label_1.setForeground(Color.GREEN);
		panel.add(label_1);

		JLabel label_2 = new JLabel("Bot nick:");
		label_2.setBounds(168, 5, 86, 20);
		label_2.setForeground(Color.GREEN);
		panel.add(label_2);

		txtSmibot = new JTextField();
		txtSmibot.setText("SmiBot");
		txtSmibot.setBounds(168, 35, 86, 20);
		txtSmibot.setColumns(10);
		panel.add(txtSmibot);

		JButton button = new JButton("Start");
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                    try {
                            startBot(txtIrcquakenetorg.getText(), txtSmibot.getText(),
                                            txtkukko.getText());
                            txtIrcquakenetorg.setEditable(false);
                            txtkukko.setEditable(false);
                            txtSmibot.setEditable(false);
                    } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                    }

            }
    });
		button.setBounds(7, 180, 57, 23);
		panel.add(button);

		JButton button_1 = new JButton("Stop");
        button_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    stopBot();
                    txtIrcquakenetorg.setEditable(true);
                    txtkukko.setEditable(true);
                    txtSmibot.setEditable(true);
            }
    });
		button_1.setBounds(226, 180, 55, 23);
		panel.add(button_1);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.BLACK);
		tabbedPane.addTab("Talk", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblTextInput = new JLabel("Text input:");
		lblTextInput.setForeground(Color.GREEN);
		lblTextInput.setBounds(10, 11, 85, 14);
		panel_1.add(lblTextInput);
		
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBounds(10, 36, 270, 120);
		panel_1.add(textArea);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try{
				sendPanelMessage(textArea.getText());
				} catch (Exception e){
					
				}
				textArea.setText("");
			}
		});
		btnSubmit.setBounds(10, 167, 89, 23);
		panel_1.add(btnSubmit);
		
		JLabel lblPleaseUseAppropriate = new JLabel("Please use appropriate\r\n language!");
		lblPleaseUseAppropriate.setForeground(Color.GREEN);
		lblPleaseUseAppropriate.setBounds(10, 192, 270, 23);
		panel_1.add(lblPleaseUseAppropriate);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.BLACK);
		tabbedPane.addTab("About", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel lblThisBotWas = new JLabel("<html>This bot was created by Smiche.\r\nIt was especially created for #kukko@irc.quakenet.org<br>\r\nSpecial thanks to KevytMaito for continuous running\r\nand moral backup.<br>\r\nSource & contact can be found at:<br>\r\n</html>");
		lblThisBotWas.setForeground(Color.GREEN);
		lblThisBotWas.setVerticalAlignment(SwingConstants.TOP);
		lblThisBotWas.setBounds(10, 11, 281, 98);
		panel_2.add(lblThisBotWas);
		
		JButton btnNewButton = new JButton("https://code.google.com/p/smicheircbot/");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					open(new URI("https://code.google.com/p/smicheircbot/"));
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(0, 120, 303, 23);
		panel_2.add(btnNewButton);
	}

	protected void sendPanelMessage(String text) {
		bot.sendMsg(chan, text);
	}
	  private static void open(URI uri) {
		    if (Desktop.isDesktopSupported()) {
		      try {
		        Desktop.getDesktop().browse(uri);
		      } catch (IOException e) { /* TODO: error handling */ }
		    } else { /* TODO: error handling */ }
		  }
	public static SmicheBot bot;
	private JTextField txtIrcquakenetorg;
	private JTextField txtkukko;
	private JTextField txtSmibot;
	private JTextArea textArea;

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");

		frame = new Main();

		icon = new TrayIcon(getIcon(),

		"SmiBot", createPopupMenu());

		icon.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				frame.setVisible(true);
			}

		});

		SystemTray.getSystemTray().add(icon);

		frame.setBounds(310, 280, 310, 280);
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
			img = ImageIO.read(Main.class.getResource("/resources/icon.png"));
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		return img;

	}

	private static PopupMenu createPopupMenu() throws

	HeadlessException {

		PopupMenu menu = new PopupMenu();

		MenuItem exit = new MenuItem("Stop & Exit");

		exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				stopBot();
				System.exit(0);

			}

		});

		menu.add(exit);

		return menu;

	}

	static void startBot(String network, String nick, String channel)
			throws Exception { // smooth
		chan = channel;
		bot = new SmicheBot(nick, channel);
		bot.setVerbose(true);
		bot.connect(network);
		bot.joinChannel(channel);
		bot.joinChannel("#uutiset");
	}

	static void stopBot() {
		try {
			bot.disconnect();
			bot.dispose();
			bot = null;
		} catch (NullPointerException e) {

		}
	}
}
