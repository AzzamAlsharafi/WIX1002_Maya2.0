package main.ui;

import main.Main;
import main.object.StudentAccount;

import javax.swing.*;
import java.awt.*;

// This is the main window of the program.
// MainFrame uses CardLayout, which allows the frame to contain all the pages of the program,
// but only one of them can be visible at a time, and it can switch between them.
public class MainFrame extends JFrame{
    CardLayout cardLayout;

    private static MainFrame frame = null;

    static final String LOGIN_AND_SIGNUP_KEY = "login&signup";
    static final String MODULE_KEY = "module";
    static final String STUDENT_MODULE_KEY = "student module";
    static final String STAFF_MODULE_KEY = "staff module";

    // The main pages of the program.
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
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // This is used to clear the text in login page,
    // so that when the user logout and return to the login page it will be empty.
    public void clearLoginPage(){
        remove(loginAndSignupPanel);

        loginAndSignupPanel = new LoginAndSignupPanel();
        add(loginAndSignupPanel, LOGIN_AND_SIGNUP_KEY);

        revalidate();
        repaint();
    }

    // This method is used to switch between the different pages in the CardLayout.
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

    // returns the current MainFrame object,
    // so that we don't have to create a new object everytime we want something from this class.
    public static MainFrame getFrame(){
        if(frame == null){
            frame = new MainFrame();
        }
        return frame;
    }
}
