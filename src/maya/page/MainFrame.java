package maya.page;

import maya.Main;
import maya.object.StudentAccount;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    CardLayout cardLayout;

    private static MainFrame frame = null;

    static final String LOGIN_AND_SIGNUP_KEY = "login&signup";
    static final String MODULE_KEY = "module";
    static final String STUDENT_MODULE_KEY = "student module";
    static final String STAFF_MODULE_KEY = "staff module";

    LoginAndSignupPanel loginAndSignupPanel;
    StudentModulePanel studentModulePanel;
    StaffModulePanel staffModulePanel;

    private MainFrame(){
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        loginAndSignupPanel = new LoginAndSignupPanel();
        add(loginAndSignupPanel, LOGIN_AND_SIGNUP_KEY);

        if(Main.currentUser != null){
            showCard(MODULE_KEY);
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
                clearLoginPage();
                cardLayout.show(getContentPane(), key);
            }

            case MODULE_KEY -> {
                if(Main.currentUser != null){
                    if(Main.currentUser instanceof StudentAccount){
                        showCard(STUDENT_MODULE_KEY);
                    } else {
                        showCard(STAFF_MODULE_KEY);
                    }
                }
            }

            case STUDENT_MODULE_KEY -> {
                if(Main.currentUser != null){
                    studentModulePanel = new StudentModulePanel();
                    studentModulePanel.redraw();
                    add(studentModulePanel, STUDENT_MODULE_KEY);
                    cardLayout.show(getContentPane(), key);
                }
            }

            case STAFF_MODULE_KEY -> {
                if(Main.currentUser != null){
                    staffModulePanel = new StaffModulePanel();
                    staffModulePanel.redraw();
                    add(staffModulePanel, STAFF_MODULE_KEY);
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
