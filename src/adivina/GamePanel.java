package adivina;

import adivina.events.GameEvent;
import adivina.events.PaintEvent;
import adivina.theme.SLabel;
import adivina.theme.SPanel;
import adivina.theme.Theme;

import javax.swing.*;
import java.awt.*;
import java.util.Random;


/**
 * Created by Oussama on 09/11/2018.
 */
public class GamePanel extends SPanel
{
    //dictionary of random words to draw/guess
    private static final String[] dictionaryLevelEasy = {"sun", "tree", "cup", "banana", "apple", "eye", "bed", "cheese", "pen", "car", "house", "clock"};
    private String name = "";
    public String wordToGuessString;
    private boolean gameStarted = false;

    private PaintPanel paintPanel;
    private ChatWindow chatWindow;

    private SocketPeerConnection socketPeerConnection;
    private SLabel wordToGuess;

    public GamePanel(String name)
    {
        super();
        this.name = name;

        this.setLayout(new BorderLayout(5, 1));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        this.paintPanel = new PaintPanel(this.socketPeerConnection);

        chatWindow = new ChatWindow(name);

        SPanel topBar = new SPanel();
        wordToGuess = new SLabel();
        wordToGuess.setFont(Theme.FONT_DEFAULT_MEDIUM);
        wordToGuess.setText("Waiting for players to connect");
        topBar.add(wordToGuess);
        this.add(topBar, BorderLayout.NORTH);

        this.add(paintPanel, BorderLayout.CENTER);
        this.add(chatWindow, BorderLayout.SOUTH);

    }

    public void startGame(String host, int port)
    {//open server
        this.socketPeerConnection = new SocketPeerConnection(this.name, host, port);
        this.socketPeerConnection.setMessageHandler(this::handleMessage);
        this.socketPeerConnection.startServer(() ->
        {
            // When client connect :
            if (!gameStarted)
            {
                this.chatWindow.start(this.socketPeerConnection);
                this.paintPanel.start(this.socketPeerConnection);
                startPlaying();
            }
        });
    }

    public void startPlaying()
    {
        wordToGuessString = randomWord(); //get a random word from the dictionary and put it in the top bar
        wordToGuess.setText(wordToGuessString);
    }

    public String randomWord()
    { //return random word from the dictionary
        Random easyWord = new Random();
        return dictionaryLevelEasy[easyWord.nextInt(dictionaryLevelEasy.length + 1)];
    }

    public void joinGame(String host, int port)
    {
        this.socketPeerConnection = new SocketPeerConnection(this.name, host, port);
        this.socketPeerConnection.setMessageHandler(this::handleMessage);
        socketPeerConnection.startClient();
        this.chatWindow.start(this.socketPeerConnection);

        wordToGuess.setText("Good luck guessing the word!");
    }

    public void displayWinner(String winner)
    {
        wordToGuess.setText("Good job " + winner + ". The word was " + wordToGuessString);
    }


    private void handleMessage(Object message)
    {
        if (message instanceof String)
        {
            this.chatWindow.showMessage((String) message); //display the message on the chatroom

            String[] messageSplit = ((String) message).toLowerCase().split(" : "); //get the message and devise in two, one for the username, the other for the guessedWord

            if (messageSplit[1].equals(wordToGuessString))
            {
                //if the guessWord = wordToGuess
                displayWinner(messageSplit[0]);//the winning message got displayed for the server
                gameStarted = false;

                this.socketPeerConnection.send(
                        new GameEvent(
                                GameEvent.GameEventType.PLAYER_WON,
                                messageSplit[0],
                                messageSplit[1]
                        )
                );
            }
        }
        else if (message instanceof PaintEvent)
        {
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
        }
        else if (message instanceof GameEvent)
        {
            GameEvent event = (GameEvent) message;
            switch (event.type)
            {
                case GAME_END:
                    break;
                case PLAYER_WON:
                    wordToGuessString = event.word;
                    displayWinner(event.name);
                    break;
            }
        }
        else
        {
            System.err.println("Unknown message object " + message);
        }
    }
}
