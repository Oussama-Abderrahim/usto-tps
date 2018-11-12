import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Oussama on 09/11/2018.
 */
public class SocketPeerConnection
{
    private static final int PORT = 33000;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Boolean isInitiator;
    private MessageHandler messageHandler;
    private String serverIP;
    private String name;
    private String message;

    private Socket connection;
    private ServerSocket server;

    public SocketPeerConnection(Boolean isInitiator)
    {
        this(isInitiator, (message) -> System.out.println("got " + message));
    }

    public SocketPeerConnection(Boolean isInitiator, MessageHandler messageHandler)
    {
        this(isInitiator, messageHandler, "127.0.0.1");
    }

    public SocketPeerConnection(Boolean isInitiator, MessageHandler messageHandler, String host)
    {
        this(isInitiator, messageHandler, host, "Anonymous");
    }

    public SocketPeerConnection(Boolean isInitiator, MessageHandler messageHandler, String host, String name)
    {
        this.isInitiator = isInitiator;
        this.messageHandler = messageHandler;
        this.serverIP = host;
        this.name = name;
    }

    public void start()
    {
        if (isInitiator) initServerConnection();
        else initClientConnection();
    }

    private void initServerConnection()
    {
        try
        {
            server = new ServerSocket(PORT, 10); //first random port number, 2nd limited number of people that can connect
            while (true)
            {
                try
                {
                    waitForConnection();
                    setupStreams(); //once connect set up a connection /pathway of communication
                    startListening();
                } catch (EOFException eofException)
                {
                    eofException.printStackTrace();
                    closeEverything();
                }
            }
        } catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    private void initClientConnection()
    {
        try
        {
            connectToServer();
            setupStreams();
            startListening();
        } catch (EOFException eofException)
        {
            System.err.println("\n" + this.name + " has terminated the connection");
            closeEverything();
        } catch (IOException ioException)
        {
            System.err.println(ioException.getMessage());
            ioException.printStackTrace();
            closeEverything();
        }
    }


    /**
     * @throws IOException
     */
    private void waitForConnection() throws IOException
    {
        connection = server.accept(); //once someone ask for connection => accept the connection
    }

    /**
     * Connect to server
     *
     * @throws IOException
     */
    private void connectToServer() throws IOException
    {
        System.out.println("Attempting connection...\n");
        connection = new Socket(InetAddress.getByName(serverIP), PORT); //once connected, a socket will get made
        System.out.println("Connected to:" + connection.getInetAddress().getHostName());
    }

    /**
     * set up streams to send and receive messages
     *
     * @throws IOException
     */
    private void setupStreams() throws IOException
    {
        outputStream = new ObjectOutputStream(connection.getOutputStream()); //the stream that flies from client to server
        outputStream.flush();
        System.out.println("\nYour streams are good to go\n");
    }

    /**
     * Start a loop in a new thread to listen for socket messages
     *
     * @throws IOException
     */
    public void startListening()
    {
        Thread listenThread = new Thread(() ->
        {
            System.out.println("Listen thread started");
            String message = "";
            try
            {
                if (isInitiator) send("1");
                inputStream = new ObjectInputStream(connection.getInputStream()); //to receive messages
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            do
            {
                try
                {
                    //wait for messages from others to be received and displayed on the screen
                    message = (String) inputStream.readObject(); //read the object they sent
                    messageHandler.handleMessage(message);

                } catch (EOFException eofException)
                {
                    System.err.println("EOF");

                } catch (ClassNotFoundException classNotfoundException)
                {
                    System.err.println("\nReceived Unknown Object type"); //receiving an object we don't know the type of it

                } catch (IOException e)
                {
                    System.err.println("\nError receiving message " + e.getMessage());
                    e.printStackTrace();
                    break;
                }
            } while (!message.equals("/END"));
            closeEverything();
        });
        listenThread.start();
    }

    /**
     * close the streams and sockets
     */
    private void closeEverything()
    {
        System.out.println("\n Closing everything down...");
        try
        {
            outputStream.close();
            inputStream.close();
            connection.close();
        } catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    /**
     * @param message (String)
     * @throws IOException if message couldn't be sent
     */
    public void send(String message) throws IOException
    {
        outputStream.writeObject(message);
        outputStream.flush();
    }


}
