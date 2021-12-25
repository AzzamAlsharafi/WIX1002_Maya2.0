package maya.page;

import maya.Main;
import maya.util.DataManager;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel{
    public LoginPanel(){
        Color background = new Color(180, 180, 180);

        Dimension fieldSize = new Dimension(300, 30);
        Insets fieldInsets = new Insets(2, 5, 3, 5);

        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(fieldSize);
        usernameField.setMargin(fieldInsets);

        JTextField passwordField = new JPasswordField();
        passwordField.setPreferredSize(fieldSize);
        passwordField.setMargin(fieldInsets);

        JCheckBox rememberMeCheckBox = new JCheckBox("Remember me");
        rememberMeCheckBox.setFocusPainted(false);
        rememberMeCheckBox.setBackground(background);

        JLabel usernameLabel = new JLabel("Username / Matric Number");

        JLabel passwordLabel = new JLabel("Password");

        JButton loginButton = new JButton("Log in");
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (Main.accounts.containsKey(username)) {
                if (Main.accounts.get(username).getPassword().equals(password)) {
                    Main.currentUser = Main.accounts.get(username);
                    DataManager.updateRememberMe(rememberMeCheckBox.isSelected());
                    MainFrame.getFrame().showCard(MainFrame.MODULE_KEY);
                }
            }
        });

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
        add(rememberMeCheckBox, c);

        c.gridx = 1;
        c.gridy = 5;
        c.anchor = GridBagConstraints.CENTER;
        c.ipadx = 75;
        c.ipady = 10;
        insets.top = 10;
        add(loginButton, c);

        setBackground(background);
    }
}
