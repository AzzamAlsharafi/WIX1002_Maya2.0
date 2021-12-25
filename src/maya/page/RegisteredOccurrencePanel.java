package maya.page;

import maya.Main;
import maya.object.Module;
import maya.object.Occurrence;
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
    JLabel lastLabel = new JLabel();

    RegisteredOccurrencePanel thisPanel = this;

    RegisteredOccurrencePanel(String occ, ModulePanel parent){
        String[] occData = occ.split("_");
        Module module = Main.modules.get(occData[0]);

        if(parent instanceof StudentModulePanel){
            lastLabel.setText(Integer.toString(module.getCredit()));
            lastLabel.setBorder(new EmptyBorder(0, 0, 0, 35));

            if(Occurrence.getOccurrencesFromCodeAndOcc(occ).isEmpty()){
                setBackground(new Color(230, 150, 150));
            }

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    setBorder(new BevelBorder(BevelBorder.LOWERED));
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    setBorder(new BevelBorder(BevelBorder.RAISED));
                    String title = "Remove Module";
                    String message = String.format("Do you want to remove %s?", occData[0]);
                    int answer = JOptionPane.showConfirmDialog(thisPanel, message, title, JOptionPane.YES_NO_OPTION);
                    if(answer == JOptionPane.YES_OPTION){
                        Main.currentUser.getOccurrences().remove(occ);
                        parent.redraw();
                        DataManager.storeAccounts();
                    }
                }
            });
        } else {
            lastLabel.setText(occData[2]);
            lastLabel.setBorder(new EmptyBorder(0, 0, 0, 5));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    setBorder(new BevelBorder(BevelBorder.LOWERED));
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    setBorder(new BevelBorder(BevelBorder.RAISED));
                    JFrame frame = new JFrame("Registered Students");
                    frame.add(new RegisteredStudentsPanel(occ));
                    frame.pack();
                    frame.setResizable(false);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            });
        }

        moduleTitleLabel.setText(occData[0]);
        occurrenceNumberLabel.setText(occData[1]);

        moduleTitleLabel.setToolTipText(module.getTitle());

        moduleTitleLabel.setBorder(new EmptyBorder(0, 0, 0, 27));
        occurrenceNumberLabel.setBorder(new EmptyBorder(0, 0, 0, 38));

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
        add(lastLabel, c);
    }
}

class RegisteredOccurrenceHeaderPanel extends JPanel{
    JLabel moduleTitleLabel = new JLabel();
    JLabel occurrenceNumberLabel = new JLabel();
    JLabel lastLabel = new JLabel();

    RegisteredOccurrenceHeaderPanel(ModulePanel parent){
        if (parent instanceof StudentModulePanel){
            lastLabel.setText("Credits");
            lastLabel.setBorder(new EmptyBorder(0, 0, 0, 15));
            occurrenceNumberLabel.setBorder(new EmptyBorder(0, 0, 0, 20));
        } else {
            lastLabel.setText("Activity");
            lastLabel.setBorder(new EmptyBorder(0, 0, 0, 10));
            occurrenceNumberLabel.setBorder(new EmptyBorder(0, 0, 0, 25));
        }

        moduleTitleLabel.setText("Module");
        occurrenceNumberLabel.setText("Occ");

        moduleTitleLabel.setBorder(new EmptyBorder(0, 10, 0, 30));

        Color fontColor = Color.WHITE;
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
        add(lastLabel, c);
    }
}