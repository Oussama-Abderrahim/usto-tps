import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Oussama on 06/11/2018.
 */
public class MainWindow extends JFrame
{
    private static MainWindow instance = null;

    public static final int WINDOW_WIDTH = 640;
    public static final int WINDOW_HEIGHT = 480;

    public static MainWindow getInstance()
    {
        if(MainWindow.instance == null)
            MainWindow.instance = new MainWindow();

        return MainWindow.instance;
    }
    public MainWindow()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setLocationRelativeTo(null); // center in screen
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        contentPane.setLayout(new BorderLayout());

        // Here we add panels to show
//        showPanel(new GamePanel());
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
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
//                    UIManager.setLookAndFeel(
//                            UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    // use default
                }

                MainWindow window = new MainWindow();

                window.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }
}
