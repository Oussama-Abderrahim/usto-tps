import sun.applet.Main;
import theme.SButton;
import theme.SPanel;
import theme.Theme;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class MainFrame extends JFrame
{
    public static MainFrame instance = null;

    /*Objets pour gerer les differentes panel*/
    private Stack<String> panelStack;
    private SButton backButton;
    private CardLayout cardLayout;
    private SPanel mainWrapper;

    private SPanel contentPane;
    private SPanel topBarPanel;
    private SPanel footerPanel;
    private SPanel welcomePanel;

    public static MainFrame getInstance()
    {
        if(instance == null)
            instance = new MainFrame();
        return instance;
    }

    private MainFrame()
    {
        initWindow();

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
        switchToPanel(welcomePanel);
    }

    private void switchToPrevious()
    {
        if(panelStack.size() > 1)
        {
            panelStack.pop();
            cardLayout.show(mainWrapper, panelStack.peek());
        }
        if(panelStack.size() <= 1)
        {
            backButton.setEnabled(false);
        }
    }
    public void switchToPanel(SPanel panel)
    {
        String name = panel.getClass().getName();

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
        setMinimumSize(new Dimension(Theme.WINDOW_WIDTH, Theme.WINDOW_HEIGHT));

        contentPane = new SPanel();
        contentPane.setLayout(new BorderLayout(5,5));
        setContentPane(contentPane);
    }

    private SPanel initTopBarPanel()
    {
        SPanel topBarPanel = new SPanel();
        topBarPanel.setLayout(new BorderLayout(0,0));

        backButton = new SButton("");
        backButton.setSize(Theme.BTN_DEFAULT_WIDTH/4, Theme.BTN_DEFAULT_HEIGHT*3/4);
        backButton.setIcon(FileManager.loadImage("back-icon", 48,48));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToPrevious();
            }
        });
        topBarPanel.add(backButton,BorderLayout.WEST);
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

            }
        });
        footerPanel.add(adminButton, BorderLayout.WEST);

//        SPanel copyrightPanel = new SPanel();
//        copyrightPanel.setLayout(new GridLayout(0,1));
//        copyrightPanel.setBorder(new LineBorder(Color.WHITE,1, false));

        JLabel copyrightText = new JLabel("Congress 2018 All right reserved");
        copyrightText.setFont(Theme.FONT_DEFAULT_SMALL);
        copyrightText.setForeground(Theme.FONT_DEFAULT_COLOR);
        copyrightText.setHorizontalAlignment(JLabel.CENTER);

//        copyrightPanel.add(copyrightText);
        footerPanel.add(copyrightText, BorderLayout.CENTER);

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
