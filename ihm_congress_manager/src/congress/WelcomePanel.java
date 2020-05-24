package congress;

import congress.theme.SButton;
import congress.theme.SLabel;
import congress.theme.SPanel;
import congress.theme.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WelcomePanel extends SPanel
{
    static  String WELCOME_TEXT = "<html><center>" +
            "CONGRESS NAME 2018\n" +
            "<h1>~Une belle citation courte~</h1>\n" +
            "</center></html>";

    public WelcomePanel()
    {
        setLayout(new GridLayout(2,1, 30, 0));
        setBorder(new EmptyBorder(55, 0, 50, 0));

        ResultSet congres = DatabaseManager.getInstance().fetchCongressData();

        try
        {
            WELCOME_TEXT = "<html><center>" +
                    "<img width='"+Theme.BTN_ICON_SIZE+"' height='"+Theme.BTN_ICON_SIZE+"' src=\""+getClass().getResource("/logo.png")+"\">" +
                    congres.getString("Nom") + "\n" +
                    "<h1>"+congres.getString("citation")+"</h1>\n" +
                    "</center></html>";
        } catch (SQLException ignored) {}

        SPanel textPanel = new SPanel();
        textPanel.setLayout(new FlowLayout());
        SLabel welcomeText = new SLabel(WELCOME_TEXT, Theme.FONT_DEFAULT_BIG);

        textPanel.add(welcomeText);
        add(textPanel);

        SPanel btnPanel = new SPanel();
        btnPanel.setLayout(new FlowLayout());
        SButton openAppButton = new SButton("Open App");
        openAppButton.setSize(Theme.BTN_DEFAULT_WIDTH*2, Theme.BTN_DEFAULT_HEIGHT*2);
        openAppButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MainFrame.getInstance().switchToPanel(new PublicMainMenu(), "Menu Principal");
            }
        });

        btnPanel.add(openAppButton);
        add(btnPanel);
    }
}
