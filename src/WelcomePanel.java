import theme.SButton;
import theme.SPanel;
import theme.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePanel extends SPanel
{

    public WelcomePanel()
    {
        setLayout(null);

        JLabel welcomeText = new JLabel("<html>" +
                "<center>" +
                "{NOM CONGRES}<br>" +
                "~~citation~~" +
                "</center>" +
                "<html>");
        welcomeText.setBounds(370, 100, Theme.WINDOW_WIDTH, 100);
        welcomeText.setFont(Theme.FONT_DEFAULT_BIG);
        welcomeText.setForeground(Theme.FONT_DEFAULT_COLOR);

        add(welcomeText);

        SButton openAppButton = new SButton("Open App");
        openAppButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MainFrame.getInstance().switchToPanel(new PublicMainMenu());
            }
        });

        openAppButton.setBounds(470,300,Theme.BTN_DEFAULT_WIDTH,Theme.BTN_DEFAULT_HEIGHT);
        add(openAppButton);
    }
}
