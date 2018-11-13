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

        JPanel toolbox = new SPanel();
        toolbox.setLayout(new FlowLayout());

        JButton clearButton = new SButton("clear");
        clearButton.addActionListener(e -> paintPanel.clear());
        toolbox.add(clearButton);

        chatWindow = new ChatWindow(name);

        this.add(toolbox, BorderLayout.NORTH);
        this.add(paintPanel, BorderLayout.CENTER);
        this.add(chatWindow, BorderLayout.WEST);
    }

    public void startGame()
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

    public void joinGame()
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
            System.err.println("Unknown message object " + message);
        }
    }
}
