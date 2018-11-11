import sun.applet.Main;
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
    private ChatWindow chatWindow;

    private SocketPeerConnection socketPeerConnection;

    public GamePanel()
    {
        super();

        this.setLayout(new BorderLayout(5, 1));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5,5));

        this.paintPanel = new PaintPanel(this.socketPeerConnection);
        this.add(paintPanel, BorderLayout.CENTER);

        JPanel gameControls = new JPanel();
        gameControls.setLayout(new FlowLayout());
        SButton startGameButton = new SButton("Start Game");

        startGameButton.addActionListener(e -> {
            startGame();
        });

        gameControls.add(startGameButton);

        JPanel toolbox = new SPanel();
        toolbox.setLayout(new FlowLayout());

        JButton clearButton = new SButton("clear");
        clearButton.addActionListener(e -> paintPanel.clear());
        toolbox.add(clearButton);

        chatWindow = new ChatWindow(this.socketPeerConnection, "localhost");

        this.add(gameControls, BorderLayout.NORTH);
        this.add(toolbox, BorderLayout.EAST);
        this.add(chatWindow, BorderLayout.WEST);
    }

    private void startGame()
    {

        this.socketPeerConnection = new SocketPeerConnection();
        this.socketPeerConnection.startListening(message -> this.chatWindow.showMessage(message));
        this.chatWindow.start();
        this.paintPanel.setEnabled(true);
    }

}
