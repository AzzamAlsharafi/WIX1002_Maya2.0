package main.ui;

import main.Main;
import main.object.Account;
import main.object.StaffAccount;
import main.util.ColorsManager;
import main.util.DataManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// One of the subpages of LoginAndSignupPanel. This is the subpage for creating a new staff account.
public class StaffSignupPanel extends JPanel{
    public StaffSignupPanel(){
        Dimension fieldSize = new Dimension(400, 30);
        Insets fieldInsets = new Insets(2, 5, 3, 5);

        JTextField umMailField = new JTextField();
        umMailField.setPreferredSize(fieldSize);
        umMailField.setMargin(fieldInsets);

        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(fieldSize);
        usernameField.setMargin(fieldInsets);

        Dimension passwordFieldSize = new Dimension(200, 30);
        JTextField passwordField = new JPasswordField();
        passwordField.setPreferredSize(passwordFieldSize);
        passwordField.setMargin(fieldInsets);

        JTextField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setPreferredSize(passwordFieldSize);
        confirmPasswordField.setMargin(fieldInsets);

        JTextField fullNameField = new JTextField();
        fullNameField.setPreferredSize(fieldSize);
        fullNameField.setMargin(fieldInsets);

        JLabel emailLabel = new JLabel("UMMail");

        JLabel usernameLabel = new JLabel("Username");

        JLabel passwordLabel = new JLabel("Password");

        JLabel confirmPasswordLabel = new JLabel("Confirm Password");

        JLabel fullNameLabel = new JLabel("Full Name");

        JButton signupButton = new JButton("Sign up");
        signupButton.setFocusPainted(false);
        signupButton.addActionListener(e -> {
            String umMail = umMailField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String fullName = fullNameField.getText();

            String title = "Unable to log in";
            if(!umMail.isBlank() &&  !username.isBlank() && !password.isBlank()
                    && !confirmPassword.isBlank() && !fullName.isBlank()){
                if(!Main.accounts.containsKey(username)){
                    if(password.equals(confirmPassword)){
                        Account newStaff = new StaffAccount(umMail, username, password, fullName, new ArrayList<>());
                        Main.accounts.put(username, newStaff);
                        Main.currentUser = newStaff;

                        // Switch to the staff home page after successfully creating a new staff account.
                        MainFrame.getFrame().showCard(MainFrame.STAFF_MODULE_KEY);
                        DataManager.storeAccountsJSON();
                    } else {
                        String message = "The passwords don't match.";
                        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    String message = "This username is already used.";
                    JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
                }
            } else {
                String message = "Please fill all the fields.";
                JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
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
        add(emailLabel, c);

        c.gridy = 1;
        add(umMailField, c);

        c.gridy = 2;
        add(usernameLabel, c);

        c.gridy = 3;
        add(usernameField, c);

        c.gridwidth = 1;
        c.gridy = 4;
        add(passwordLabel, c);

        c.gridy = 5;
        add(passwordField, c);

        c.gridx = 1;
        c.gridy = 4;
        add(confirmPasswordLabel, c);

        c.gridy = 5;
        add(confirmPasswordField, c);

        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        add(fullNameLabel, c);

        c.gridy = 7;
        add(fullNameField, c);

        c.gridy = 8;
        c.anchor = GridBagConstraints.CENTER;
        c.ipadx = 75;
        c.ipady = 10;
        insets.top = 10;
        add(signupButton, c);

        setBackground(ColorsManager.staffSignupBackground);
    }
}
