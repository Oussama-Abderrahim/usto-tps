package adivina.menus;

import java.awt.*;
import java.awt.event.ActionListener;

import adivina.MainWindow;
import adivina.SocketPeerConnection;
import adivina.theme.*;

/**
 * Created by pc on 12/11/2018.
 */
public class JoinRoomPanel extends adivina.theme.SPanel
{
    public JoinRoomPanel()
    {
        this.setBackground(adivina.theme.Theme.DARKER_MIDNIGHT_BLUE);
        this.setLayout(new GridLayout(8, 1));

        SLabel enterUsernameLabel = new SLabel("Enter your username");
        SLabel enterPortLabel = new SLabel("Enter Port");
        SLabel enterHostLabel = new SLabel("Enter Host");

        STextField usernameText = new STextField();
        STextField hostText = new STextField();
        STextField portText = new STextField();

        hostText.setText("127.0.0.1");
        portText.setText("" + SocketPeerConnection.DEFAULT_PORT);

        usernameText.setFont(Theme.FONT_DEFAULT_MEDIUM);
        hostText.setFont(Theme.FONT_DEFAULT_MEDIUM);
        portText.setFont(Theme.FONT_DEFAULT_MEDIUM);

        SPanel usernamePanel = new SPanel();
        usernamePanel.setLayout(new BorderLayout());
        usernamePanel.add(usernameText, BorderLayout.NORTH);

        SPanel hostPanel = new SPanel();
        hostPanel.setLayout(new BorderLayout());
        hostPanel.add(hostText, BorderLayout.NORTH);

        SPanel portPanel = new SPanel();
        portPanel.add(portText, BorderLayout.NORTH);

        SButton joinButton = new SButton("Join Game");
        ActionListener joinGameAction = e ->
        {
            int port = Integer.parseInt(portText.getText());
            String host = hostText.getText();
            MainWindow.getInstance().joinGame(usernameText.getText(), host, port);
        };

        joinButton.addActionListener(joinGameAction);
        usernameText.addActionListener(joinGameAction);
        portText.addActionListener(joinGameAction);
        hostText.addActionListener(joinGameAction);

        this.add(enterUsernameLabel);
        this.add(usernamePanel);
        this.add(enterHostLabel);
        this.add(hostPanel);
        this.add(enterPortLabel);
        this.add(portPanel);
        this.add(new SPanel());
        this.add(joinButton);
    }
}
