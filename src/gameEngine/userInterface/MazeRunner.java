package gameEngine.userInterface;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import drawables.dynamic_drawables.character.player.Player;
import drawables.dynamic_drawables.character.player.derivatives.Knight;
import gameEngine.controller.Controller;
import grid.Levels.builder.ILevelBuilder;
import grid.Levels.builder.derivatives.EasyLevel;
import grid.generation.initialize_components.GenerateComponent;

public class MazeRunner extends JFrame {

    private static final Logger logger = Logger.getLogger(MazeRunner.class.getName());
    public static ILevelBuilder level = new EasyLevel();
    public static Player player = Knight.getInstance();
    private JPanel contentPane;
    private Image img;

    /**
     * Create the frame.
     */
    public MazeRunner() {
        PropertyConfigurator.configure("src\\log_properties\\log4j.properties");
        setResizable(false);
        img = new ImageIcon(System.getProperty("user.dir")
                + "/src/assets/start/start.jpg").getImage();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 367);
        contentPane = new StartingImagePanel(img);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnSettings = new JButton("");
        btnSettings.setIcon(new ImageIcon(((new ImageIcon(
                "src/assets/start/settings.png").getImage()
                .getScaledInstance(89, 45,
                        java.awt.Image.SCALE_SMOOTH)))));
        btnSettings.setFocusable(false);
        btnSettings.setContentAreaFilled(false);
        btnSettings.setOpaque(false);
        btnSettings.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                MazeSettings settings = new MazeSettings();
                settings.setVisible(true);
                logger.info("Settings opened");
            }
        });
        btnSettings.setBounds(220, 150, 115, 71);
        contentPane.add(btnSettings);

        JButton btnStart = new JButton("");
        btnStart.setContentAreaFilled(false);
        btnStart.setFocusable(false);
        btnStart.setIcon(new ImageIcon(((new ImageIcon(
                "src/assets/start/startBtn.png").getImage()
                .getScaledInstance(89, 45,
                        java.awt.Image.SCALE_SMOOTH)))));
        btnStart.setOpaque(false);
        btnStart.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                dispose();

                GameWindow play = new GameWindow();
                Controller controller = new Controller(play, player);
                Controller.level = level;

                controller.startGame();
                Controller.generateInitialGUIgrid();

                logger.info("initial gui created");

                Controller.generateMap(GameWindow.panelMap);

                logger.info("maze map created");

                GenerateComponent gc = controller.g.getGenerateComponent();
                controller.g.setMonsterMovements(gc);
                play.setVisible(true);

                logger.info("game started");

            }
        });
        btnStart.setBounds(220, 42, 115, 71);
        contentPane.add(btnStart);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
			public void run() {
                try {
                    MazeRunner frame = new MazeRunner();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 10, 30, this.getWidth(), this.getHeight(), null);
    }

}
