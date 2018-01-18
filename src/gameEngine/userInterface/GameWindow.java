package gameEngine.userInterface;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;

public class GameWindow extends JFrame {

	public static ImagePanel contentPane;
	public static StartingImagePanel panelMap;
	public static JLabel lblHealth;
	public static JLabel lblScore;
	private JPanel container;

	/**
	 * Create the frame.
	 */
	public GameWindow() {

		play();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

		setBounds(300, 0, 900, 687);
		contentPane = new ImagePanel(new ImageIcon(System.getProperty("user.dir")
					+ "/src/assets/start/background.jpg").getImage(),this);
		Image img = new ImageIcon(System.getProperty("user.dir")
				+ "/src/assets/start/textureBackground.jpg").getImage();
		StartingImagePanel panel2 = new StartingImagePanel(img);
		panel2.setBackground(new Color(0, 51, 0));
		panel2.setBounds(660, 670, 240, 670);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
		GridBagLayout gbl_panel2 = new GridBagLayout();
		gbl_panel2.columnWidths = new int[]{69, 0};
		gbl_panel2.rowHeights = new int[]{213, 213, 213, 0};
		gbl_panel2.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel2.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel2.setLayout(gbl_panel2);

		lblScore = new JLabel("Your Score : 0");
		lblScore.setFont(new Font("Times New Roman", Font.BOLD, 18));
		//lblScore.setBorder(new Border());
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setBorder(border);
		lblScore.setForeground(Color.BLACK);
		GridBagConstraints gbc_lblScore = new GridBagConstraints();
		gbc_lblScore.fill = GridBagConstraints.BOTH;
		gbc_lblScore.insets = new Insets(0, 0, 5, 0);
		gbc_lblScore.gridx = 0;
		gbc_lblScore.gridy = 0;
		panel2.add(lblScore, gbc_lblScore);
		lblHealth = new JLabel("Your Health : 100");
		lblHealth.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblHealth.setHorizontalAlignment(SwingConstants.CENTER);
		lblHealth.setForeground(Color.BLACK);
		lblHealth.setBorder(border);
		GridBagConstraints gbc_lblHealth = new GridBagConstraints();
		gbc_lblHealth.fill = GridBagConstraints.BOTH;
		gbc_lblHealth.insets = new Insets(0, 0, 5, 0);
		gbc_lblHealth.gridx = 0;
		gbc_lblHealth.gridy = 1;
		panel2.add(lblHealth, gbc_lblHealth);
//		panel2.add(lblMap);
		container.add(contentPane);
		container.add(panel2);

		panelMap = new StartingImagePanel(img);
		panelMap.setLayout(null);
		GridBagConstraints gbc_panelMap = new GridBagConstraints();
		gbc_panelMap.fill = GridBagConstraints.BOTH;
		gbc_panelMap.gridx = 0;
		gbc_panelMap.gridy = 2;
		panel2.add(panelMap, gbc_panelMap);
		panelMap.setBounds(0, 214, 220, 220);
		setContentPane(container);

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameWindow frame = new GameWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void play() {

		  try {
		   File file = new File(System.getProperty("user.dir") + "\\src\\assets\\Audio\\sounds\\victory.wav");
		   Clip clip = AudioSystem.getClip();
		   clip.open(AudioSystem.getAudioInputStream(file));
		   clip.start();
		   //Thread.sleep(clip.getMicrosecondLength());
		  } catch (Exception e) {
		   System.err.println(e.getMessage());
		  }
		 
	}
	
}


