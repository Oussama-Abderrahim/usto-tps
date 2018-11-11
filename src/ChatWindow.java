/**
 * Created by pc on 05/11/2018.
 */
//responsible of making the GUI and connecting stuffs

import theme.STextField;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ChatWindow extends JPanel
{
    private STextField userText; //where typing
    private JTextArea chatWindow; //chatWindow where messages gonna be displayed

    private String name;

    private SocketPeerConnection socketConnection;


    //constructor
    public ChatWindow(SocketPeerConnection socketConnection, String name)
    { //passing the ip address connecting to
        super();

        this.socketConnection = socketConnection;
        this.name = name;

        setLayout(new BorderLayout());

        /* Add text field */
        userText = new STextField();
        userText.setPlaceHolder("Enter your message");
        userText.setEditable(false); //not allowed before conntecting to anyone
        userText.addActionListener(
                event ->
                {
                    sendMessage(event.getActionCommand()); //take what is in text area and send it to next person
                    userText.setText("");
                }
        );
        add(userText, BorderLayout.NORTH);

        /* Add Text Area to display messages */
        chatWindow = new JTextArea();
        chatWindow.setEditable(false);
        add(new JScrollPane(chatWindow), BorderLayout.CENTER); // make it scrollable
    }

    //send messages to server
    private void sendMessage(String message)
    {
        try
        {
            this.socketConnection.send(message);
        } catch (IOException ioException)
        {
            chatWindow.append("\n Something messed up sending message");
        }
    }

    //change or update chatWindow
    public void showMessage(final String message)
    {
        SwingUtilities.invokeLater(() ->
                {
                    chatWindow.append(message); //so it can appear at the end of the conversation
                }
        );
    }

    //give user permission to type into the textbox
    private void ableToType(final boolean tof)
    { //true or false
        SwingUtilities.invokeLater(() ->
                {
                    userText.setEditable(tof); //make it editable or not
                }
        );
    }

    public void start() {
        ableToType(true);
    }
}
