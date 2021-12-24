package maya.page;

import maya.Main;
import maya.object.Module;
import maya.util.DataManager;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class RegisteredOccurrencePanel extends JPanel {
    JLabel moduleTitleLabel = new JLabel();
    JLabel occurrenceNumberLabel = new JLabel();
    JLabel creditsLabel = new JLabel();

    RegisteredOccurrencePanel thisPanel = this;

    RegisteredOccurrencePanel(String occ, StudentModulePanel parent){
        String[] codeAndOcc = occ.split("_");
        Module module = Main.modules.get(codeAndOcc[0]);

        moduleTitleLabel.setText(codeAndOcc[0]);
        occurrenceNumberLabel.setText(codeAndOcc[1]);
        creditsLabel.setText(Integer.toString(module.getCredit()));

        moduleTitleLabel.setToolTipText(module.getTitle());

        moduleTitleLabel.setBorder(new EmptyBorder(0, 0, 0, 27));
        occurrenceNumberLabel.setBorder(new EmptyBorder(0, 0, 0, 38));
        creditsLabel.setBorder(new EmptyBorder(0, 0, 0, 35));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setBorder(new BevelBorder(BevelBorder.LOWERED));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBorder(new BevelBorder(BevelBorder.RAISED));
                String title = "Remove Module";
                String message = String.format("Do you want to remove %s?", codeAndOcc[0]);
                int answer = JOptionPane.showConfirmDialog(thisPanel, message, title, JOptionPane.YES_NO_OPTION);
                if(answer == JOptionPane.YES_OPTION){
                    Main.currentUser.getOccurrences().remove(occ);
                    parent.redraw();
                    DataManager.storeAccounts();
                }
            }
        });

        Dimension size = new Dimension(200, 50);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);

        setBorder(new BevelBorder(BevelBorder.RAISED));

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        add(moduleTitleLabel, c);

        c.gridx = 1;
        add(occurrenceNumberLabel, c);

        c.gridx = 2;
        add(creditsLabel, c);
    }
}

class RegisteredOccurrenceHeaderPanel extends JPanel{
    JLabel moduleTitleLabel = new JLabel();
    JLabel occurrenceNumberLabel = new JLabel();
    JLabel creditsLabel = new JLabel();

    RegisteredOccurrenceHeaderPanel(){
        moduleTitleLabel.setText("Module");
        occurrenceNumberLabel.setText("Occ");
        creditsLabel.setText("Credits");

        moduleTitleLabel.setBorder(new EmptyBorder(0, 10, 0, 30));
        occurrenceNumberLabel.setBorder(new EmptyBorder(0, 0, 0, 20));
        creditsLabel.setBorder(new EmptyBorder(0, 0, 0, 15));

        Color fontColor = Color.WHITE;
        moduleTitleLabel.setForeground(fontColor);
        occurrenceNumberLabel.setForeground(fontColor);
        creditsLabel.setForeground(fontColor);

        Dimension size = new Dimension(200, 39);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);

        Border border1 = BorderFactory.createCompoundBorder(new BevelBorder(BevelBorder.RAISED), new BevelBorder(BevelBorder.LOWERED));
        Border border = BorderFactory.createCompoundBorder(border1, new BevelBorder(BevelBorder.RAISED));
        setBorder(border);

        Color background = new Color(5, 49, 121);
        setBackground(background);

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        add(moduleTitleLabel, c);

        c.gridx = 1;
        add(occurrenceNumberLabel, c);

        c.gridx = 2;
        add(creditsLabel, c);
    }
}