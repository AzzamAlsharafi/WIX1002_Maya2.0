package maya.page;

import maya.Main;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    CardLayout cardLayout;

    private static MainFrame frame = null;

    static final String LOGIN_AND_SIGNUP_KEY = "login&signup";
    static final String STUDENT_MODULE_KEY = "student module";

    LoginAndSignupPanel loginAndSignupPanel;
    StudentModulePanel studentModulePanel;

    private MainFrame(){
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        loginAndSignupPanel = new LoginAndSignupPanel();
        add(loginAndSignupPanel, LOGIN_AND_SIGNUP_KEY);

        if(Main.currentUser != null){
            showCard(STUDENT_MODULE_KEY);
        }

        setTitle("Maya 2.0");
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setSize(1400, 800);
//        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void clearLoginPage(){
        remove(loginAndSignupPanel);

        loginAndSignupPanel = new LoginAndSignupPanel();
        add(loginAndSignupPanel, LOGIN_AND_SIGNUP_KEY);

        revalidate();
        repaint();
    }

    public void showCard(String key){
        switch (key){
            case LOGIN_AND_SIGNUP_KEY -> {
                cardLayout.show(getContentPane(), key);
            }

            case STUDENT_MODULE_KEY -> {
                if(Main.currentUser != null){
                    studentModulePanel = new StudentModulePanel();
                    studentModulePanel.redraw();
                    add(studentModulePanel, STUDENT_MODULE_KEY);
                    cardLayout.show(getContentPane(), key);
                }
            }
        }
    }

    public static MainFrame getFrame(){
        if(frame == null){
            frame = new MainFrame();
        }
        return frame;
    }
}
