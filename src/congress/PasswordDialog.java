package congress;

import congress.theme.SButton;
import congress.theme.SLabel;
import congress.theme.SPanel;
import congress.theme.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ${} on 03/12/2017.
 */
public class PasswordDialog extends JDialog
{
    private boolean passwordValid;

    public PasswordDialog(Frame owner, String title, boolean modal)
    {
        super(owner, title, modal);

        passwordValid = false;

        setResizable(false);

        initContent();
        setLocationRelativeTo(null);
    }

    private void initContent()
    {
        setSize(400, 200);

        SPanel contentPane = new SPanel();

        contentPane.setLayout(new BorderLayout());

        SPanel centerPane = new SPanel();

        centerPane.setBorder(new EmptyBorder(20, 30, 20, 30));
        centerPane.setLayout(new GridLayout(3, 1));

        SPanel usernamePanel = new SPanel();
        SPanel passwordPanel = new SPanel();
        usernamePanel.setLayout(new BorderLayout());
        passwordPanel.setLayout(new BorderLayout());

        SLabel usernameLbl = new SLabel("Username : ", Theme.FONT_DEFAULT);
        SLabel passwordLbl = new SLabel("Password : ", Theme.FONT_DEFAULT);
        usernameLbl.setSize(120, Theme.BTN_DEFAULT_HEIGHT);
        passwordLbl.setSize(120, Theme.BTN_DEFAULT_HEIGHT);

        JTextField usernameField = new JTextField();
        JTextField passwordField = new JPasswordField();

        usernamePanel.add(usernameLbl, BorderLayout.WEST);
        passwordPanel.add(passwordLbl, BorderLayout.WEST);
        usernamePanel.add(usernameField, BorderLayout.CENTER);
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        centerPane.add(usernamePanel);
        centerPane.add(passwordPanel);


        SPanel buttonPanel = new SPanel();
        buttonPanel.setLayout(new BorderLayout());

        SButton loginBtn = new SButton("valider");
        SButton cancelBtn = new SButton("Annuler");

        loginBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (usernameField.getText().equals("root") && passwordField.getText().equals("root"))
                {
                    passwordValid = true;
                    setVisible(false);
                }
                else
                {
                    JOptionPane jop3 = new JOptionPane();
                    jop3.showMessageDialog(null, "Nom d'utilisateur ou Mot de passe incorrect", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        cancelBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
            }
        });

        buttonPanel.add(loginBtn, BorderLayout.WEST);
        buttonPanel.add(cancelBtn, BorderLayout.EAST);

        centerPane.add(buttonPanel);
        contentPane.add(centerPane, BorderLayout.CENTER);
        setContentPane(contentPane);
    }

    public void showDialog()
    {
        passwordValid = false;
        setVisible(true);
    }

    public boolean isPasswordValid()
    {
        return passwordValid;
    }
}
