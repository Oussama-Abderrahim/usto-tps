

import theme.STextField;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame{

    private STextField userText; //area to type your message before you send it
    private JTextArea chatWindow;
    private ObjectOutputStream output; //to send stuffs away
    private ObjectInputStream input;
    private ServerSocket server;
    private Socket connection;

    //constructor

    public Server(){
        super("Chatroom");
        userText = new STextField();
        userText.setPlaceHolder("Enter your message here");
        userText.setEditable(false); //not able to send anything as long as there isn't anyone else connected

        // see how we add le OnClick f Java :

        userText.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        sendMessage(event.getActionCommand()); //the message typed inside the Textfield
                        userText.setText("");

                    }
                }
        );
        add(userText, BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow));
        setSize(500,500);
        setVisible(true);

    }

    //set up and run the server
    public void startRunning(){
        try{
            server = new ServerSocket(33000,10); //first random port number, 2nd limited number of people that can connect
            while(true){
                try{
                    waitForConnection();
                    setupStreams(); //once connect set up a connection /pathway of communication
                    whileChatting(); //allow us to change and receive to each other
                } catch (EOFException eofException){
                    showMessage("\n 'The artist' ended connection! ");
                } finally {
                    closeEverything();
                }
            }
        } catch(IOException ioException){
            ioException.printStackTrace();
        }
    }

    //wait for connection, then once you're connected display connection information

    private void waitForConnection()throws IOException{
        showMessage("Waiting for 'The guesser' to connect...");
        connection = server.accept(); //once someone ask for connection => accept the connection
        showMessage("Now connected to "+connection.getInetAddress().getHostAddress());
    }

    //get stream to send and receive data
    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream()); //creating a path way to connect
        //with another computer
        output.flush(); //in case some stuffs get left behind after sending messages
        input = new ObjectInputStream(connection.getInputStream());
        showMessage("\n Streams are now setup!\n");
    }

    // code runnning during the chat conversation
    private void whileChatting()throws IOException{
        String message = "You are now coonected! ";
        sendMessage(message);
        ableToType(true);
        do{
            try{
                message = (String) input.readObject();
                showMessage("\n"+message);

            } catch (ClassNotFoundException classNotFoundException){
                showMessage("\n Can't understand");
            }

            //have a conversation
        }while(!message.equals("The guesser - END")); //stop if client sends "END"

    }

    //close streams and sockets after done chatting
    private void closeEverything(){
        showMessage("\n Closing connections...\n");
        ableToType(false);
        try {
            output.close();
            input.close();
            connection.close();
        } catch(IOException ioException){
            ioException.printStackTrace();
        }
    }

    //send message to client
    private void sendMessage(String message){
        try{ //////////////////Usually here nickname of client
            output.writeObject("The artist - "+ message); // write then send the message through
            // the output stream to the client
            output.flush(); //in case somehting happens (left over stuffs), clean that
            showMessage("\nThe artist - "+message);
        } catch(IOException ioException){
            chatWindow.append("\n ERROR: can't send the message"); //if unable to send the message
        }
    }

    //update chatWindow (update a part of the GUI)
    private void showMessage(final String text){
        SwingUtilities.invokeLater( //thread that update part of the GUI
                new Runnable() {
                    @Override
                    public void run() {
                        chatWindow.append(text); //adding a new line of text at the bottom of the window
                    }
                }
        );
    }

    //let the user type into their box
    private void ableToType(final boolean tof){ //true(can type) or false
        SwingUtilities.invokeLater( //thread that update part of the GUI
                new Runnable() {
                    @Override
                    public void run() {
                        userText.setEditable(tof);
                    }
                }
        );
    }

    public static void main(String[] args)
    {
        Server server = new Server();

        server.startRunning();
    }
}

