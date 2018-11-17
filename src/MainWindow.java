import theme.SPanel;
import theme.Theme;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Oussama on 06/11/2018.
 */
public class MainWindow extends JFrame
{
    private static MainWindow instance = null;

    public static final int WINDOW_WIDTH = Theme.WINDOW_WIDTH;
    public static final int WINDOW_HEIGHT = Theme.WINDOW_HEIGHT;

    public static MainWindow getInstance()
    {
        if (MainWindow.instance == null)
            MainWindow.instance = new MainWindow();

        return MainWindow.instance;
    }

    public MainWindow()
    {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setLocationRelativeTo(null); // center in screen

        SPanel contentPane = new SPanel();
        this.setContentPane(contentPane);
        this.setResizable(false);

        contentPane.setLayout(new BorderLayout());

        // Here we add panels to show
        showPanel(new MainMenuPanel());

        //background image
        String bg = "bg.jpg";
        File file = new File("res/" + bg);
        try
        {
            BufferedImage image = ImageIO.read(file);
            contentPane.setBackground(image);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void showPanel(JPanel panel)
    {
        this.getContentPane().removeAll();
        this.getContentPane().add(panel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    public static void main(String[] args)
    {
        Runnable r = () ->
        {
            MainWindow window = MainWindow.getInstance();

            window.setVisible(true);
        };
        SwingUtilities.invokeLater(r);
    }

    public void startNewGame(String playerName, String host, int port)
    {
        GamePanel gamePanel = new GamePanel(playerName);

        this.showPanel(gamePanel);
        gamePanel.startGame(host, port);
    }

    public void joinGame(String playerName, String host, int port)
    {
        GamePanel gamePanel = new GamePanel(playerName);

        this.showPanel(gamePanel);
        gamePanel.joinGame(host, port);
    }
}
