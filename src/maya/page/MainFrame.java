package maya.page;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    CardLayout cardLayout;

    static final String LOGIN_KEY = "login";
    static final String SIGNUP_KEY = "signup";

    public MainFrame(){
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        add(new LoginPanel(this), LOGIN_KEY);
        add(new SignupPanel(), SIGNUP_KEY);

        setSize(500, 500);
//        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void showCard(String key){
        cardLayout.show(getContentPane(), key);
    }
}
