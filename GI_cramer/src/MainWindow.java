import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Created by Oussama on 27/06/2019.
 */
public class MainWindow
{

    public static void main(String[]args)
    {
        new MainWindow();
    }

    public MainWindow()
    {
        JFrame frame = new JFrame("test");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();

        contentPane.setLayout(null);

        JLabel test = new JLabel("PC 1");
        test.setSize(50, 50);
        test.setBorder(BorderFactory.createLineBorder(Color.black));
        test.setBackground(Color.RED);
        test.setLocation(new Point(50, 50));
        contentPane.add(test);

        test.addMouseMotionListener(new MouseMotionListener()
        {
            @Override
            public void mouseDragged(MouseEvent mouseEvent)
            {
                int x = mouseEvent.getX()-50;
                int y = mouseEvent.getY()-50;
                test.setLocation(x, y);
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent)
            {

            }
        });

        frame.setContentPane(contentPane);

        frame.setBounds(new Rectangle(50, 50, 300, 300));

        frame.setVisible(true);

    }

}
