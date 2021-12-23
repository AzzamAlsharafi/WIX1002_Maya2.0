package maya.page;

import maya.Main;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    CardLayout cardLayout;

    static MainFrame frame = null;

    static final String LOGIN_AND_SIGNUP_KEY = "login&signup";
    static final String STUDENT_MODULE_KEY = "student module";

    private MainFrame(){
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        add(new LoginAndSignupPanel(), LOGIN_AND_SIGNUP_KEY);
        add(new StudentModulePanel(), STUDENT_MODULE_KEY);

        if(Main.currentUser != null){
            showCard(STUDENT_MODULE_KEY);
        }

        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setSize(1200, 800);
//        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void showCard(String key){
        cardLayout.show(getContentPane(), key);
    }

    public static MainFrame getFrame(){
        if(frame == null){
            frame = new MainFrame();
        }
        return frame;
    }
}
