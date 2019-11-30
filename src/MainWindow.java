import mdlaf.MaterialLookAndFeel;
import theme.SPanel;
import theme.Theme;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Oussama on 06/11/2018.
 */
public class MainWindow extends JFrame {
    private static MainWindow instance = null;

    public static final int WINDOW_WIDTH = Theme.WINDOW_WIDTH;
    public static final int WINDOW_HEIGHT = Theme.WINDOW_HEIGHT;

    public static MainWindow getInstance() {
        if (MainWindow.instance == null)
            MainWindow.instance = new MainWindow();

        return MainWindow.instance;
    }

    public MainWindow() {
        setTitle("Image Indexer v0.3");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setLocationRelativeTo(null); // center in screen

        SPanel contentPane = new SPanel();
        this.setContentPane(contentPane);
        this.setResizable(false);

        contentPane.setLayout(new BorderLayout());

        // Here we add panels to show
        showPanel(new MainMenuPanel());

        DatabaseManager.getInstance();
    }

    public void showPanel(SPanel panel) {
        this.getContentPane().removeAll();
        this.getContentPane().add(panel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel (new MaterialLookAndFeel());
        }
        catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace ();
        }

        Runnable r = () ->
        {
            MainWindow window = MainWindow.getInstance();

            window.setVisible(true);
        };
        SwingUtilities.invokeLater(r);
    }
}
