package congress;

import congress.theme.SButton;
import congress.theme.SLabel;
import congress.theme.SPanel;
import congress.theme.Theme;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class MainFrame extends JFrame
{
    public static MainFrame instance = null;

    private DatabaseManager dbManager;

    /*Objets pour gerer les differentes panel*/
    private Stack<String> panelStack;
    private SButton backButton;
    private CardLayout cardLayout;
    private SPanel mainWrapper;

    private SPanel contentPane;
    private SPanel topBarPanel;
    private SPanel footerPanel;
    private SPanel welcomePanel;
    private SLabel panelTitle;

    public static MainFrame getInstance()
    {
        if(instance == null)
            instance = new MainFrame();
        return instance;
    }

    private MainFrame()
    {
        initWindow();
        dbManager = DatabaseManager.getInstance();

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        contentPane.setLayout(new BorderLayout(0,0));

        panelStack = new Stack<>();

        mainWrapper = new SPanel();
        cardLayout = new CardLayout();
        mainWrapper.setLayout(cardLayout);


        topBarPanel = initTopBarPanel();
        footerPanel = initFooterPanel();
        menuBar.add(topBarPanel);
        contentPane.add(footerPanel, BorderLayout.SOUTH);
        contentPane.add(mainWrapper, BorderLayout.CENTER);

        welcomePanel = new WelcomePanel();
        switchToPanel(welcomePanel, "Acceuil");
    }

    private void switchToPrevious()
    {
        if(panelStack.size() > 1)
        {
            panelStack.pop();
            cardLayout.show(mainWrapper, panelStack.peek());
            panelTitle.setText(panelStack.peek());
        }
        if(panelStack.size() <= 1)
        {
            backButton.setEnabled(false);
        }
    }
    public void switchToPanel(SPanel panel, String name)
    {
        panelTitle.setText(name);
        panelStack.push(name);
        mainWrapper.add(panel, panelStack.peek());
        System.out.println("Added : " + panelStack.peek());

        cardLayout.show(mainWrapper, name);

        if(panelStack.size() > 1)
            backButton.setEnabled(true);
        else
            backButton.setEnabled(false);
    }

    private void initWindow()
    {
        setTitle("Application Gestion Congres");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, Theme.WINDOW_WIDTH, Theme.WINDOW_HEIGHT);
        setPreferredSize(new Dimension(Theme.WINDOW_WIDTH, Theme.WINDOW_HEIGHT));
        setLocationRelativeTo(null);

        contentPane = new SPanel();
        contentPane.setOpaque(true);
        contentPane.setLayout(new BorderLayout(5,5));
        setContentPane(contentPane);
    }

    private SPanel initTopBarPanel()
    {
        SPanel topBarPanel = new SPanel();
        topBarPanel.setLayout(new BorderLayout(0,0));

        backButton = new SButton("");
        backButton.makeIntoIconButton(FileManager.loadImage("back-icon", 36,24));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToPrevious();
            }
        });
        topBarPanel.add(backButton,BorderLayout.WEST);

        panelTitle = new SLabel("Menu principal");
        panelTitle.setFont(Theme.FONT_DEFAULT_LARGE);
        topBarPanel.add(panelTitle, BorderLayout.CENTER);

        return topBarPanel;
    }

    private SPanel initFooterPanel()
    {
        SPanel footerPanel = new SPanel();
        footerPanel.setLayout(new BorderLayout(20,0));

        SButton adminButton = new SButton("Admin");
        adminButton.setSize(Theme.BTN_DEFAULT_WIDTH/2, Theme.BTN_DEFAULT_HEIGHT/2);
        adminButton.setFont(Theme.FONT_DEFAULT_SMALL);
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /// TODO : Add admin panel
            }
        });
        footerPanel.add(adminButton, BorderLayout.WEST);

        JLabel copyrightText = new JLabel("Congress 2018 All right reserved");
        copyrightText.setFont(Theme.FONT_DEFAULT_SMALL);
        copyrightText.setForeground(Theme.FONT_DEFAULT_COLOR);
        copyrightText.setHorizontalAlignment(JLabel.CENTER);

        footerPanel.add(copyrightText, BorderLayout.CENTER);

        SPanel socialPanel  = new SPanel();
        socialPanel.setLayout(new GridLayout(0, 3, 5, 0));

        SButton fbButton = new SButton("");
        SButton twitterButton = new SButton("");
        SButton mailButton = new SButton("");
        fbButton.makeIntoIconButton(FileManager.loadImage("fb", Theme.BTN_ICON_SIZE/3, Theme.BTN_ICON_SIZE/3));
        twitterButton.makeIntoIconButton(FileManager.loadImage("twitter", Theme.BTN_ICON_SIZE/3, Theme.BTN_ICON_SIZE/3));
        mailButton.makeIntoIconButton(FileManager.loadImage("mail", Theme.BTN_ICON_SIZE/3, Theme.BTN_ICON_SIZE/3));

        socialPanel.add(fbButton);
        socialPanel.add(twitterButton);
        socialPanel.add(mailButton);

        footerPanel.add(socialPanel, BorderLayout.EAST);
        return footerPanel;
    }

    public static void main(String args[])
    {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                getInstance().setVisible(true);
            }
        });
    }
}
