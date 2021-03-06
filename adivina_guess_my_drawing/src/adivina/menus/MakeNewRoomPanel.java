package adivina.menus;

import adivina.MainWindow;
import adivina.SocketPeerConnection;
import adivina.theme.SButton;
import adivina.theme.SPanel;
import adivina.theme.STextField;
import adivina.theme.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by pc on 12/11/2018.
 */
public class MakeNewRoomPanel extends SPanel
{

    public MakeNewRoomPanel()
    {
        this.setBackground(adivina.theme.Theme.DARKER_MIDNIGHT_BLUE);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JLabel enterUsername = new JLabel("Enter your username");
        enterUsername.setHorizontalTextPosition(SwingConstants.CENTER);
        enterUsername.setForeground(Color.WHITE);
        enterUsername.setFont(Theme.FONT_DEFAULT_LARGE);

        STextField username = new STextField();
        username.setFont(Theme.FONT_DEFAULT_MEDIUM);
        SPanel usernamePanel = new SPanel();

        usernamePanel.setBackground(Theme.DARKER_MIDNIGHT_BLUE);
        usernamePanel.setLayout(new BorderLayout());

        SButton addNewPlayerButton = new SButton("Add");

        this.add(enterUsername);
        this.add(usernamePanel);
        usernamePanel.add(username, BorderLayout.NORTH);
        this.add(addNewPlayerButton);

        addNewPlayerButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MainWindow.getInstance().startNewGame(username.getText(), SocketPeerConnection.DEFAULT_HOST, SocketPeerConnection.DEFAULT_PORT);
            }
        });
    }
}
