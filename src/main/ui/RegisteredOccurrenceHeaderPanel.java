package main.ui;

import main.util.ColorsManager;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// This class is used once for the header bar at the top of the small list for registered occurrences in StudentModulePanel and StaffModulePanel.
class RegisteredOccurrenceHeaderPanel extends JPanel {
    JLabel moduleTitleLabel = new JLabel();
    JLabel occurrenceNumberLabel = new JLabel();
    JLabel lastLabel = new JLabel();

    RegisteredOccurrenceHeaderPanel(ModulePanel parent) {
        if (parent instanceof StudentModulePanel) { // Check if current user is student.
            lastLabel.setText("Credits");
            lastLabel.setBorder(new EmptyBorder(0, 0, 0, 15));
            occurrenceNumberLabel.setBorder(new EmptyBorder(0, 0, 0, 20));
        } else { // If current user is staff.
            lastLabel.setText("Activity");
            lastLabel.setBorder(new EmptyBorder(0, 0, 0, 10));
            occurrenceNumberLabel.setBorder(new EmptyBorder(0, 0, 0, 25));
        }

        moduleTitleLabel.setText("Module");
        occurrenceNumberLabel.setText("Occ");

        moduleTitleLabel.setBorder(new EmptyBorder(0, 10, 0, 30));

        Color fontColor = ColorsManager.whiteFont;
        moduleTitleLabel.setForeground(fontColor);
        occurrenceNumberLabel.setForeground(fontColor);
        lastLabel.setForeground(fontColor);

        Dimension size = new Dimension(200, 39);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);

        Border border1 = BorderFactory.createCompoundBorder(new BevelBorder(BevelBorder.RAISED), new BevelBorder(BevelBorder.LOWERED));
        Border border = BorderFactory.createCompoundBorder(border1, new BevelBorder(BevelBorder.RAISED));
        setBorder(border);

        setBackground(ColorsManager.signatureDarkBlue);

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        add(moduleTitleLabel, c);

        c.gridx = 1;
        add(occurrenceNumberLabel, c);

        c.gridx = 2;
        add(lastLabel, c);
    }
}
