import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Oussama on 09/11/2018.
 */
public class SocketPeerConnection
{
    public static final int DEFAULT_PORT = 6789;
    public static final String DEFAULT_HOST = "127.0.0.1";
    private int port = DEFAULT_PORT;
    /**
     *
     */
    private ObjectOutputStream outputStream;

    /**
     * True if the SocketConnection is a server (accepts other connections)
     */
    private Boolean isInitiator;

    /**
     * MessageHandler
     */
    private MessageHandler messageHandler;

    private String serverIP;
    private String name;
    private String message;

    private Socket connection;
    private ServerSocket server;
    private ArrayList<ObjectInputStream> inputStreams;
    private ArrayList<ObjectOutputStream> outputStreams;

    public SocketPeerConnection()
    {
        this("Anonymous", DEFAULT_HOST, DEFAULT_PORT);
    }

    public SocketPeerConnection(String name, String host, int port)
    {
        outputStreams = new ArrayList<>();
        inputStreams = new ArrayList<>();
        this.serverIP = host;
        this.name = name;
        this.port = port;
    }

    public void startServer(Runnable onClientConnection)
    {
        this.isInitiator = true;
        new Thread(() ->
        {
            try
            {
                server = new ServerSocket(DEFAULT_PORT, 10); //first random port number, 2nd limited number of people that can connect
                while (true)
                {
                    try
                    {
                        Socket socket = waitForConnection();
                        setupStreams(socket); //once connect set up a connection /pathway of communication
                        new Thread(() ->
                        {
                            onClientConnection.run();
                            this.startListening(socket);
                        }).start();
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
        }).start();
    }

    public void startClient()
    {
        this.isInitiator = false;
        try
        {
            this.connection = connectToServer();
            setupStreams(this.connection);
            new Thread(() -> this.startListening(this.connection)).start();
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
     * /once someone ask for connection
     * => accept the connection
     * => create a new socket for that connection
     *
     * @throws IOException
     */
    private Socket waitForConnection() throws IOException
    {
        this.connection = server.accept();
        return this.connection;
    }

    /**
     * Connect to server
     *
     * @throws IOException
     */
    private Socket connectToServer() throws IOException
    {
        System.out.println("Attempting connection...\n");
        connection = new Socket(InetAddress.getByName(this.serverIP), this.port); //once connected, a socket will get made
        System.out.println("Connected to:" + connection.getInetAddress().getHostName());
        return connection;
    }

    /**
     * set up streams to send and receive messages
     *
     * @throws IOException
     */
    private void setupStreams(Socket connection) throws IOException
    {
        outputStream = new ObjectOutputStream(connection.getOutputStream()); //the stream that flies from client to server
        outputStream.flush();
        outputStreams.add(outputStream);
        System.out.println("\nYour streams are good to go\n");
    }

    /**
     * Start a loop in a new thread to listen for socket messages
     *
     * @throws IOException
     */
    public void startListening(Socket clientSocket)
    {
        Thread listenThread = new Thread(() ->
        {
            System.out.println("Listen thread started");
            Object message = "";
            ObjectInputStream inputStream = null;
            try
            {
                inputStream = new ObjectInputStream(clientSocket.getInputStream()); //to receive messages
                inputStreams.add(inputStream);
            } catch (IOException e)
            {
                e.printStackTrace();
                return;
            }
            do
            {
                try
                {
                    //wait for messages from others to be received and displayed on the screen
                    message = inputStream.readObject(); //read the object they sent
                    messageHandler.handleMessage(message);

                    /// TODO: send message to others


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
            for (ObjectOutputStream objectOutputStream : outputStreams)
            {
                objectOutputStream.close();
            }
            for (ObjectInputStream inputStream : inputStreams)
            {
                inputStream.close();
            }
            connection.close();
        } catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    /**
     * @param message (Object)
     * @throws IOException if message couldn't be sent
     */
    public void send(Object message)
    {
        try
        {
            // If server, send to all client
            if (isInitiator)
            {
                for (ObjectOutputStream outputStream : outputStreams)
                {
                    outputStream.writeObject(message);
                    outputStream.flush();
                }
            }
            else // if client, send only to server
            {
                outputStream.writeObject(message);
                outputStream.flush();
            }
        } catch (IOException e)
        {
            System.err.println("error sending " + e.getMessage());
        }
    }


    public void setMessageHandler(MessageHandler messageHandler)
    {
        this.messageHandler = messageHandler;
    }
}
