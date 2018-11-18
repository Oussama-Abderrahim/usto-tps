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
    private String name = "";

    public GamePanel(String name)
    {
        super();
        this.name = name;

        this.setLayout(new BorderLayout(5, 1));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        this.paintPanel = new PaintPanel(this.socketPeerConnection);

        chatWindow = new ChatWindow(name);

        this.add(paintPanel, BorderLayout.CENTER);
        this.add(chatWindow, BorderLayout.WEST);
    }

    public void startGame(String host, int port)
    {
        this.socketPeerConnection = new SocketPeerConnection(this.name, host, port);
        this.socketPeerConnection.setMessageHandler(this::handleMessage);
        this.socketPeerConnection.startServer(() ->
        {
            // When client connect :
            if (!gameStarted)
            {
                this.chatWindow.start(this.socketPeerConnection);
                this.paintPanel.start(this.socketPeerConnection);
            }
        });
        ///TODO : show here a waiting message
    }

    public void joinGame(String host, int port)
    {
        this.socketPeerConnection = new SocketPeerConnection(this.name, host, port);
        this.socketPeerConnection.setMessageHandler(this::handleMessage);
        socketPeerConnection.startClient();
        this.chatWindow.start(this.socketPeerConnection);
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
                case COLOR_CHANGE:
                    this.paintPanel.setColor(ev.index);
                    break;
            }
        } else
        {
            System.err.println("Unknown message object " + message);
        }
    }
}
