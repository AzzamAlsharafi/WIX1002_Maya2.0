package maya.page;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel{
    public LoginPanel(){
        Dimension fieldSize = new Dimension(300, 30);
        Insets fieldInsets = new Insets(2, 5, 3, 5);

        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(fieldSize);
        usernameField.setMargin(fieldInsets);

        JTextField passwordField = new JPasswordField();
        passwordField.setPreferredSize(fieldSize);
        passwordField.setMargin(fieldInsets);

        JLabel usernameLabel = new JLabel("Username / Matric Number");

        JLabel passwordLabel = new JLabel("Password");

        JButton loginButton = new JButton("Log in");

        JButton signinButton = new JButton("Sign in");

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        setLayout(layout);

        Insets insets = new Insets(2, 0, 2, 0);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = insets;
        add(usernameLabel, c);

        c.gridy = 1;
        add(usernameField, c);

        c.gridy = 2;
        add(passwordLabel, c);

        c.gridy = 3;
        add(passwordField, c);

        c.gridy = 4;
        c.gridwidth = 1;
        c.ipadx = 75;
        c.ipady = 10;
        insets.top = 5;
        add(signinButton, c);

        c.gridx = 1;
        c.gridy = 4;
        c.anchor = GridBagConstraints.LINE_END;
        add(loginButton, c);

        Dimension size = new Dimension(400, 400);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);

        setBackground(new Color(180, 180, 180));
    }
}
