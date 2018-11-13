/**
 * Created by pc on 12/11/2018.
 */
import theme.SLabel;
import theme.Theme;
import theme.SButton;
import theme.SPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainMenuPanel extends SPanel{

    public MainMenuPanel(){
        super();
        this.setLayout(new BorderLayout(5, 1));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5,5));

        SPanel leftSide = new SPanel();
        leftSide.setBackground(Theme.DARKER_MIDNIGHT_BLUE);
        leftSide.setLayout(new BoxLayout(leftSide, BoxLayout.PAGE_AXIS));

        SLabel adivana = new SLabel("<html>"+"<center>"+"ADIVANA"+"</center>"+"<html>");
        adivana.setForeground(Color.WHITE);
        adivana.setFont(new Font("Serif", Font.PLAIN, 40));

        SLabel optionText = new SLabel("<html>"+"<center>"+"Choose your option"+"</center>"+"<html>");
        optionText.setForeground(Color.WHITE);
        optionText.setFont(new Font("Serif", Font.PLAIN, 20));
        optionText.setHorizontalTextPosition(SwingConstants.CENTER);
        optionText.setHorizontalAlignment(SwingConstants.CENTER);


        SButton newRoomButton = new SButton("Make new room");
        SButton joinRoomButton = new SButton("Join a room");

        MakeNewRoomPanel newRoomPanel = new MakeNewRoomPanel();
        JoinRoomPanel joinRoomPanel = new JoinRoomPanel();

        //using a panel just to have more top space
        SPanel space = new SPanel();
        space.setBackground(Theme.DARKER_MIDNIGHT_BLUE);

        leftSide.add(adivana);
        leftSide.add(optionText);
        leftSide.add(space);
        leftSide.add(newRoomButton);
        leftSide.add(joinRoomButton);

        //newRoomButton -> change panel to MakeNewRoomPanel
        newRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    remove(leftSide);
                    add(newRoomPanel,BorderLayout.WEST);
                    revalidate();
                    repaint();
            }
        });

        //joinRoomButton -> change panel to JoinRoomPanel
        joinRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(leftSide);
                add(joinRoomPanel,BorderLayout.WEST);
                revalidate();
                repaint();
            }
        });

        this.add(leftSide,BorderLayout.WEST);
    }
}
