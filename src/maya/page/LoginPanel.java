package maya.page;

import javax.swing.*;
import java.awt.*;

public class LoginPanel {
    public JPanel panel;

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

        panel = new JPanel();
        panel.setLayout(layout);

        Insets insets = new Insets(2, 0, 2, 0);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = insets;
        panel.add(usernameLabel, c);

        c.gridy = 1;
        panel.add(usernameField, c);

        c.gridy = 2;
        panel.add(passwordLabel, c);

        c.gridy = 3;
        panel.add(passwordField, c);

        c.gridy = 4;
        c.gridwidth = 1;
        c.ipadx = 75;
        c.ipady = 10;
        insets.top = 5;
        panel.add(signinButton, c);

        c.gridx = 1;
        c.gridy = 4;
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(loginButton, c);

        Dimension size = new Dimension(400, 400);
        panel.setMinimumSize(size);
        panel.setPreferredSize(size);
        panel.setMaximumSize(size);

        panel.setBackground(new Color(180, 180, 180));
    }
}
