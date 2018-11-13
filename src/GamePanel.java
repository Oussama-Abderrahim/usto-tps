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
    private boolean gameStarted = false;

    public GamePanel()
    {
        super();

        this.setLayout(new BorderLayout(5, 1));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        this.paintPanel = new PaintPanel(this.socketPeerConnection);
        this.add(paintPanel, BorderLayout.CENTER);

        SPanel gameControls = new SPanel();
        gameControls.setLayout(new FlowLayout());

        SButton startGameButton = new SButton("Start Game");
        SButton joinGameButton = new SButton("Join Game");

        startGameButton.addActionListener(e ->
        {
            startGame();
            startGameButton.setEnabled(false);
            joinGameButton.setEnabled(false);
        });

        joinGameButton.addActionListener(e ->
        {
            joinGame();
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

    private void startGame()
    {
        this.socketPeerConnection = new SocketPeerConnection();
        ///TODO : show here a waiting message

        new Thread(() -> this.socketPeerConnection.startServer(() ->
        {
            if (!gameStarted)
            {
                this.chatWindow.start(this.socketPeerConnection);
                this.paintPanel.start(this.socketPeerConnection);
                this.socketPeerConnection.startListening(this::handleMessage);
            }
            else
            {
            }
        })).start();
    }

    private void joinGame()
    {
        this.socketPeerConnection = new SocketPeerConnection();
        socketPeerConnection.startClient();
        this.chatWindow.start(this.socketPeerConnection);
//        this.paintPanel.start(this.socketPeerConnection);
        this.socketPeerConnection.startListening(this::handleMessage);
    }


    private void handleMessage(Object message)
    {
        if(message instanceof String)
        {
            this.chatWindow.showMessage((String) message);
        }
        else if(message instanceof PaintEvent){
            PaintEvent ev = (PaintEvent) message;
            switch (ev.type)
            {
                case PRESSED:
                    this.paintPanel.onPress(ev.point);
                    break;
                case DRAG:
                    this.paintPanel.onDrag(ev.point);
                    break;
                case RELEASED:
                    this.paintPanel.onRelease(ev.point);
                    break;
                case CLEAR:
                    this.paintPanel.clear();
                    break;
            }
        } else
        {
            System.err.println("Unknow message object " + message);
        }
    }
}
