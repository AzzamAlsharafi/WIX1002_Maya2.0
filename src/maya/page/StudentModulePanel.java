package maya.page;

import maya.Main;

import javax.swing.*;
import java.awt.*;

public class StudentModulePanel extends JPanel {
    public StudentModulePanel(){
        JLabel label = new JLabel("EMPTY");

        JButton button = new JButton("PRESS ME");
        button.addActionListener(e -> label.setText(Main.currentUser.getFullName()));

        add(label);
        add(button);

        setBackground(new Color(180, 230, 230));
    }
}
