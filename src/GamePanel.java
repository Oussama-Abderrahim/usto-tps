import theme.SButton;
import theme.SPanel;
import javax.swing.*;
import java.awt.*;

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

        SPanel gameControls = new SPanel();
        gameControls.setLayout(new FlowLayout());

        SButton startGameButton = new SButton("Start Game");
        SButton joinGameButton = new SButton("Join Game");

        startGameButton.addActionListener(e -> {
            startGame(true);
            startGameButton.setEnabled(false);
            joinGameButton.setEnabled(false);
        });

        joinGameButton.addActionListener(e -> {
            startGame(false);
            startGameButton.setEnabled(false);
            joinGameButton.setEnabled(false);
        });

        gameControls.add(startGameButton);
        gameControls.add(joinGameButton);

        JPanel toolbox = new SPanel();
        toolbox.setLayout(new FlowLayout());

        JButton clearButton = new SButton("clear");
        clearButton.addActionListener(e -> paintPanel.clear());
        toolbox.add(clearButton);

        chatWindow = new ChatWindow("localhost");

        this.add(gameControls, BorderLayout.NORTH);
        this.add(toolbox, BorderLayout.EAST);
        this.add(chatWindow, BorderLayout.WEST);
    }

    private void startGame(Boolean isInitiator)
    {
        this.socketPeerConnection = new SocketPeerConnection(isInitiator, message -> this.chatWindow.showMessage(message));
        this.chatWindow.start(this.socketPeerConnection);
        this.paintPanel.setEnabled(true);
        new Thread(() -> {
            this.socketPeerConnection.start();
        }).start();
    }

}
