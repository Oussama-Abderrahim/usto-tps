import theme.SButton;
import theme.SLabel;
import theme.SPanel;
import theme.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePanel extends SPanel
{
    static final String WELCOME_TEXT = "<html><center>" +
            "CONGRESS NAME 2018\n" +
            "<h1>~Une belle citation courte~</h1>\n" +
            "</center></html>";

    public WelcomePanel()
    {
        setLayout(new GridLayout(2,1, 30, 0));
        setBorder(new EmptyBorder(50, 100, 50, 100));

        SPanel textPanel = new SPanel();
        textPanel.setLayout(new FlowLayout());
        SLabel welcomeText = new SLabel(WELCOME_TEXT);
        welcomeText.setFont(Theme.FONT_DEFAULT_BIG);

        textPanel.add(welcomeText);
        add(textPanel);

        SPanel btnPanel = new SPanel();
        btnPanel.setLayout(new FlowLayout());
        SButton openAppButton = new SButton("Open App");
        openAppButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MainFrame.getInstance().switchToPanel(new PublicMainMenu());
            }
        });
        openAppButton.setSize(Theme.BTN_DEFAULT_WIDTH,Theme.BTN_DEFAULT_HEIGHT);

        btnPanel.add(openAppButton);
        add(btnPanel);
    }
}
