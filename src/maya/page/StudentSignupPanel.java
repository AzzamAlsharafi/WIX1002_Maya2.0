package maya.page;

import maya.Main;
import maya.object.Account;
import maya.object.StudentAccount;
import maya.util.DataManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StudentSignupPanel extends JPanel{
    public StudentSignupPanel(){
        Dimension bigFieldSize = new Dimension(400, 30);
        Insets fieldInsets = new Insets(2, 5, 3, 5);

        JTextField siswaMailField = new JTextField();
        siswaMailField.setPreferredSize(bigFieldSize);
        siswaMailField.setMargin(fieldInsets);

        JTextField matricNumberField = new JTextField();
        matricNumberField.setPreferredSize(bigFieldSize);
        matricNumberField.setMargin(fieldInsets);

        Dimension smallFieldSize = new Dimension(200, 30);
        JTextField passwordField = new JPasswordField();
        passwordField.setPreferredSize(smallFieldSize);
        passwordField.setMargin(fieldInsets);

        JTextField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setPreferredSize(smallFieldSize);
        confirmPasswordField.setMargin(fieldInsets);

        JTextField fullNameField = new JTextField();
        fullNameField.setPreferredSize(bigFieldSize);
        fullNameField.setMargin(fieldInsets);

        JTextField englishScoreField = new JTextField();
        englishScoreField.setPreferredSize(smallFieldSize);
        englishScoreField.setMargin(fieldInsets);

        String comboBoxEmptyOption = "SELECT";

        String[] programmes = new String[]{comboBoxEmptyOption, "Computer System & Network", "Artificial Intelligence", "Information Systems",
                "Software Engineering", "Multimedia", "Data Science"};
        JComboBox<String> programmeComboBox = new JComboBox<>(programmes);

        String[] englishTestOptions = new String[]{comboBoxEmptyOption, "MUET", "IELTS",
                "TOEFL Paper–Based Test", "TOEFL Computer–Based Test", "TOEFL Internet–Based Test",
                "PTE (Academic)", "FCE", "GCE A Level (English)", "IGCSE/GCSE (English)"};
        JComboBox<String> englishTestComboBox = new JComboBox<>(englishTestOptions);

        String[] citizenshipOptions = new String[]{comboBoxEmptyOption, "Malaysian", "Non-Malaysian"};
        JComboBox<String> citizenshipComboBox = new JComboBox<>(citizenshipOptions);


        JLabel emailLabel = new JLabel("SiswaMail");

        JLabel usernameLabel = new JLabel("Matric Number");

        JLabel passwordLabel = new JLabel("Password");

        JLabel confirmPasswordLabel = new JLabel("Confirm Password");

        JLabel fullNameLabel = new JLabel("Full Name");

        JLabel programmeLabel = new JLabel("Programme");

        JLabel citizenshipLabel = new JLabel("Citizenship");

        JLabel englishTestLabel = new JLabel("English Test");

        JLabel englishScoreLabel = new JLabel("Band/Score");

        JButton signupButton = new JButton("Sign up");
        signupButton.setFocusPainted(false);
        signupButton.addActionListener(e -> {
            String siswaMail = siswaMailField.getText();
            String matricNumber = matricNumberField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String fullName = fullNameField.getText();
            int programme = programmeComboBox.getSelectedIndex();
            int englishTest = englishTestComboBox.getSelectedIndex();
            String englishScore = englishScoreField.getText();
            int citizenship = citizenshipComboBox.getSelectedIndex();

            if(!siswaMail.isBlank() &&  !matricNumber.isBlank() && !password.isBlank()
                    && !confirmPassword.isBlank() && !fullName.isBlank() && programme != 0
                    && englishTest != 0 && !englishScore.isBlank() && citizenship != 0){
                if(password.equals(confirmPassword)){
                    Account newStudent = new StudentAccount(siswaMail, matricNumber, password, fullName,
                            new ArrayList<>(), programme, StudentAccount.calculateMUETBand(englishTest, englishScore), citizenship);
                    Main.accounts.put(matricNumber, newStudent);
                    DataManager.storeAccounts();
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
        add(emailLabel, c);

        c.gridy = 1;
        add(siswaMailField, c);

        c.gridy = 2;
        add(usernameLabel, c);

        c.gridy = 3;
        add(matricNumberField, c);

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
        add(programmeLabel, c);

        c.gridy = 9;
        add(programmeComboBox, c);

        c.gridy = 10;
        add(englishTestLabel, c);

        c.gridy = 11;
        c.gridwidth = 1;
        add(englishTestComboBox, c);

        c.gridx = 1;
        c.gridy = 10;
        add(englishScoreLabel, c);

        c.gridy = 11;
        add(englishScoreField, c);

        c.gridx = 0;
        c.gridy = 12;
        c.gridwidth = 2;
        add(citizenshipLabel, c);

        c.gridy = 13;
        add(citizenshipComboBox, c);

        c.gridy = 14;
        c.anchor = GridBagConstraints.CENTER;
        c.ipadx = 75;
        c.ipady = 10;
        insets.top = 10;
        add(signupButton, c);

        setBackground(new Color(230, 180, 230));
    }
}
