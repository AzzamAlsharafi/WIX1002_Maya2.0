package maya.page;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    CardLayout cardLayout;

    static MainFrame frame = null;

    static final String LOGIN_AND_SIGNUP_KEY = "login&signup";

    private MainFrame(){
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        add(new LoginAndSignupPanel(), LOGIN_AND_SIGNUP_KEY);
        setSize(600, 600);
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
