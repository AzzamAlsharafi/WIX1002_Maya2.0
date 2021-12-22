package maya.page;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SignupPanel extends JPanel {
    public SignupPanel(){
        JTabbedPane tabbedPane = new JTabbedPane();

        int padding = 20;
        EmptyBorder paddingBorder = new EmptyBorder(padding, padding, padding, padding);

        JPanel staffPanel = new StaffSignupPanel();
        staffPanel.setBorder(paddingBorder);

        JPanel studentPanel = new StudentSignupPanel();
        studentPanel.setBorder(paddingBorder);

        tabbedPane.add("Staff", staffPanel);
        tabbedPane.add("Student", studentPanel);

        setLayout(new GridBagLayout());

        add(tabbedPane);

        setBackground(new Color(180, 230, 180));
    }
}
