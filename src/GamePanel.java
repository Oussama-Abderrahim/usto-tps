import theme.SButton;
import theme.SPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Oussama on 09/11/2018.
 */
public class GamePanel extends SPanel
{
    private PaintPanel paintPanel;

    public GamePanel()
    {
        super();
        this.setLayout(new BorderLayout(5, 1));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5,5));

        this.paintPanel = new PaintPanel();
        this.add(paintPanel, BorderLayout.CENTER);

        JPanel toolbox = new SPanel();
        toolbox.setLayout(new FlowLayout());

        JButton clearButton = new SButton("clear");
        clearButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                paintPanel.clear();
            }
        });
        toolbox.add(clearButton);

        this.add(toolbox, BorderLayout.NORTH);

//        ChatWindow chatWindow = new ChatWindow("localhost");
//        this.add(chatWindow, BorderLayout.WEST);
    }
}
