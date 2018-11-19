package adivina; /**
 * Created by pc on 05/11/2018.
 */
//responsible of making the GUI and connecting stuffs

import adivina.theme.STextField;
import adivina.theme.Theme;

import javax.swing.*;
import java.awt.*;

public class ChatWindow extends JPanel
{
    private STextField userText; //where typing
    private JTextArea chatWindow; //chatWindow where messages gonna be displayed

    private String name;

    private SocketPeerConnection socketConnection;


    //constructor
    public ChatWindow(String name)
    { //passing the ip address connecting to
        super();

        this.name = name;

        setLayout(new BorderLayout());

        /* Add text field */
        userText = new STextField();
        userText.setFontColor(Color.BLACK);
        userText.setPlaceHolder("Enter your message");
        userText.setFont(Theme.FONT_DEFAULT_MEDIUM);
        userText.setEnabled(false);
        userText.setEditable(false); //not allowed before connecting to anyone
        userText.addActionListener(
                event ->
                {
                    sendMessage(event.getActionCommand()); //take what is in text area and send it to next person
                    userText.setText("");
                }
        );

        /* Add Text Area to display messages */
        chatWindow = new JTextArea();
        chatWindow.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatWindow);
        scrollPane.setPreferredSize(new Dimension((int) scrollPane.getPreferredSize().getWidth(), 150));
        add(scrollPane, BorderLayout.CENTER); // make it scrollable

        add(userText, BorderLayout.SOUTH);
    }

    //send messages to server
    private void sendMessage(String message)
    {
        message = name + " : " + message;
        this.socketConnection.send(message);
        showMessage(message);
    }

    //change or update chatWindow
    public void showMessage(final String message)
    {
        SwingUtilities.invokeLater(() ->
                {
                    chatWindow.append(message + "\n"); //so it can appear at the end of the conversation
                }
        );
    }

    private void ableToType(final boolean tof) //give user permission to type into the textbox (tof:true or false)
    {
        SwingUtilities.invokeLater(() ->
                {
                    userText.setEditable(tof); //make it editable or not
                }
        );
    }

    public void start(SocketPeerConnection socketPeerConnection)
    {
        this.socketConnection = socketPeerConnection;
        userText.setEnabled(true);
        ableToType(true);
    }
}
