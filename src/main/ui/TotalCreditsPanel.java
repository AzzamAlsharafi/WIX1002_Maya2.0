package main.ui;

import main.Main;
import main.util.ColorsManager;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// This is the small panel for showing the current number of registered credits in the student home page (StudentModulePanel).
class TotalCreditsPanel extends JPanel {
    JPanel header = new JPanel();
    JLabel maxHeaderLabel = new JLabel("Max Credits");
    JLabel currentHeaderLabel = new JLabel("Current");

    JPanel body = new JPanel();
    JLabel maxBodyLabel = new JLabel(Integer.toString(Main.maxCreditsPerStudent));
    JLabel currentBodyLabel = new JLabel();

    TotalCreditsPanel(int currentCredits){
        currentBodyLabel.setText(Integer.toString(currentCredits));

        header.add(maxHeaderLabel);
        header.add(currentHeaderLabel);
        Border border1 = BorderFactory.createCompoundBorder(new BevelBorder(BevelBorder.RAISED), new BevelBorder(BevelBorder.LOWERED));
        Border border = BorderFactory.createCompoundBorder(border1, new BevelBorder(BevelBorder.RAISED));
        header.setBorder(border);
        header.setBackground(ColorsManager.signatureDarkBlue);
        maxHeaderLabel.setForeground(ColorsManager.whiteFont);
        maxHeaderLabel.setBorder(new EmptyBorder(0, 0, 0, 50));
        currentHeaderLabel.setForeground(ColorsManager.whiteFont);

        body.add(maxBodyLabel);
        body.add(currentBodyLabel);
        body.setLayout(new FlowLayout(FlowLayout.LEFT));
        body.setBorder(new BevelBorder(BevelBorder.RAISED));
        maxBodyLabel.setBorder(new EmptyBorder(0, 12, 0, 35));
        currentBodyLabel.setBorder(new EmptyBorder(0, 70, 0, 0));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(header);
        add(body);
    }
}