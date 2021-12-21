package maya.page;

import javax.swing.*;

public class MainFrame extends JFrame{
    public MainFrame(){
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        add(new LoginPanel().panel);

        setSize(500, 500);
//        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
