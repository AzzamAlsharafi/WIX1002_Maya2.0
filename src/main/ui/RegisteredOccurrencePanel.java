package main.ui;

import main.Main;
import main.object.Module;
import main.object.Occurrence;
import main.util.ColorsManager;
import main.util.DataManager;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// This class is used to display occurrences in the small list for registered modules in StudentModulePanel and StaffModulePanel.
class RegisteredOccurrencePanel extends JPanel {
    JLabel moduleTitleLabel = new JLabel();
    JLabel occurrenceNumberLabel = new JLabel();
    JLabel lastLabel = new JLabel();

    RegisteredOccurrencePanel thisPanel = this;

    RegisteredOccurrencePanel(String occ, ModulePanel parent){
        String[] occData = occ.split("_");
        Module module = Main.modules.get(occData[0]);

        if(parent instanceof StudentModulePanel){ // This section only works if the current logged-in user is a student.
            lastLabel.setText(Integer.toString(module.getCredit()));
            lastLabel.setBorder(new EmptyBorder(0, 0, 0, 35));

            if(Occurrence.getOccurrencesFromCodeAndOcc(occ).isEmpty()){
                setBackground(ColorsManager.missingOccurrence);
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
        } else { // This section only works if the current logged-in user is a staff.
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

