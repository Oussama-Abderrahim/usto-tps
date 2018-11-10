import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Oussama on 09/11/2018.
 */
public class SocketPeerConnection
{
    private static final int PORT = 6789;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private String serverIP;
    private Socket connection;
    private String name;
    private String message;

    public SocketPeerConnection(String host)
    {
        this(host, "Anonymous");
    }

    public SocketPeerConnection(String host, String name)
    {
        this.serverIP = host;
        this.name = name;

        try
        {
            connectToServer();
            setupStreams();
        } catch (EOFException eofException)
        {
            System.err.println("\n" + this.name + " has terminated the connection");

        } catch (IOException ioException)
        {
            ioException.printStackTrace();
        } finally
        {
            closeEverything();
        }
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
        inputStream = new ObjectInputStream(connection.getInputStream()); //to receive messages
        System.out.println("\nYour streams are good to go\n");
    }

    /**
     * Start a loop in a new thread to listen for socket messages
     *
     * @throws IOException
     */
    public void startListening(MessageHandler messageHandler)
    {
        Thread listenThread = new Thread(() ->
        {
            do
            {
                try
                {
                    //wait for messages from others to be received and displayed on the screen
                    message = (String) inputStream.readObject(); //read the object they sent
                    messageHandler.handleMessage(message);

                } catch (ClassNotFoundException classNotfoundException)
                {
                    System.err.println("\nReceived Unknown Object type"); //receiving an object we don't know the type of it

                } catch (IOException e)
                {
                    System.err.println("\nError receiving message " + e.getMessage());
                }
            } while (!message.equals("/END"));


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
