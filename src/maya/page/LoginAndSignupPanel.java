package maya.page;

import maya.util.ColorsManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginAndSignupPanel extends JPanel {
    public LoginAndSignupPanel(){
        JTabbedPane tabbedPane = new JTabbedPane();

        int padding = 20;
        EmptyBorder paddingBorder = new EmptyBorder(padding, padding, padding, padding);

        JPanel loginPanel = new LoginPanel();
        loginPanel.setBorder(paddingBorder);

        JPanel staffPanel = new StaffSignupPanel();
        staffPanel.setBorder(paddingBorder);

        JPanel studentPanel = new StudentSignupPanel();
        studentPanel.setBorder(paddingBorder);

        tabbedPane.add("Login", loginPanel);
        tabbedPane.add("New Staff", staffPanel);
        tabbedPane.add("New Student", studentPanel);

        setLayout(new GridBagLayout());

        add(tabbedPane);

        setBackground(ColorsManager.loginAndSignupBackground);
    }
}
