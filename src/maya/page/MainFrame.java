package maya.page;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    CardLayout cardLayout;

    static final String LOGIN_AND_SIGNUP_KEY = "login&signup";

    public MainFrame(){
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
}
