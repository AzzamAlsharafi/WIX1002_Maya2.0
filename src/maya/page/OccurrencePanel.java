package maya.page;

import maya.Main;
import maya.object.Module;
import maya.object.Occurrence;
import maya.util.DataManager;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class OccurrencePanel extends JPanel {
    Occurrence occurrence;
    OccurrencePanel thisPanel = this;

    JLabel moduleTitleLabel = new JLabel();
    JLabel occurrenceNumberLabel = new JLabel();
    JLabel activityLabel = new JLabel();
    JLabel timeLabel = new JLabel();
    JLabel tutorLabel = new JLabel();
    JLabel creditsLabel = new JLabel();
    JLabel targetLabel = new JLabel();
    JLabel actualLabel = new JLabel();

    void setOccurrence(Occurrence occurrence, ModulePanel parent){
        this.occurrence = occurrence;
        Module module = Main.modules.get(occurrence.getCode());

        String moduleOcc;
        boolean isRegistered;
        boolean isCoordinator;

        if(parent instanceof StudentModulePanel){
            moduleOcc = String.format("%s_%s", occurrence.getCode(), occurrence.getOccurrenceNumber());
            isRegistered = Main.currentUser.getOccurrences().contains(moduleOcc);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (!isRegistered) {
                        setBorder(new BevelBorder(BevelBorder.LOWERED));
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (!isRegistered) {
                        setBorder(new BevelBorder(BevelBorder.RAISED));

                        String title = "Register Module";

                        // Check if the same module is already registered but in a different occurrence.
                        for (String registered : Main.currentUser.getOccurrences()) {
                            if (registered.contains(occurrence.getCode())) {
                                String message = String.format("You already registered %s in another occurrence,\ndo you want to replace it?", occurrence.getCode());
                                int answer = JOptionPane.showConfirmDialog(thisPanel, message, title, JOptionPane.YES_NO_OPTION);
                                if (answer == JOptionPane.YES_OPTION) {
                                    int index = Main.currentUser.getOccurrences().indexOf(registered);
                                    Main.currentUser.getOccurrences().remove(registered);
                                    if (isNotOverlapping(moduleOcc)) {
                                        Main.currentUser.getOccurrences().add(moduleOcc);
                                        parent.redraw();
                                        DataManager.storeAccounts();
                                    } else {
                                        Main.currentUser.getOccurrences().add(index, registered);
                                    }
                                }
                                return;
                            }
                        }

                        String message = String.format("Do you want to register %s?", occurrence.getCode());
                        int answer = JOptionPane.showConfirmDialog(thisPanel, message, title, JOptionPane.YES_NO_OPTION);
                        if (answer == JOptionPane.YES_OPTION) {
                            if (isNotOverlapping(moduleOcc)) {
                                if (((StudentModulePanel) parent).currentCredits + module.getCredit() <= Main.maxCreditsPerStudent) {
                                    Main.currentUser.getOccurrences().add(moduleOcc);
                                    parent.redraw();
                                    DataManager.storeAccounts();
                                } else {
                                    message = String.format("Registering %s will put you over %d credits", moduleOcc.split("_")[0], Main.maxCreditsPerStudent);
                                    title = "Unable to register";
                                    JOptionPane.showMessageDialog(thisPanel, message, title, JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        }
                    }
                }
            });

            if(isRegistered){
                setBackground(new Color(217, 237, 247));
                setBorder(new BevelBorder(BevelBorder.LOWERED));
            } else {
                setBorder(new BevelBorder(BevelBorder.RAISED));
            }
        } else {
            moduleOcc = String.format("%s_%s_%s", occurrence.getCode(), occurrence.getOccurrenceNumber(), occurrence.getActivityString());
            isRegistered = Main.currentUser.getOccurrences().contains(moduleOcc);
            isCoordinator = module.getCoordinator().equalsIgnoreCase(Main.currentUser.getFullName());

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if(isCoordinator){
                        setBorder(new BevelBorder(BevelBorder.LOWERED));
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if(isCoordinator){
                        setBorder(new BevelBorder(BevelBorder.RAISED));

                        String title = "Module";
                        String message = "Do you want to edit or delete this occurrence?";
                        String[] options = new String[]{"Edit", "Delete", "Cancel"};

                        int ans = JOptionPane.showOptionDialog(thisPanel, message, title, JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                        switch (ans){
                            case 0 -> {
                                message = "Do you want to edit this occurrence or edit the module?";
                                title = "Edit";
                                options = new String[]{"Edit occurrence", "Edit module", "Cancel"};
                                ans = JOptionPane.showOptionDialog(thisPanel, message, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                if(ans == JOptionPane.YES_OPTION || ans == JOptionPane.NO_OPTION){
                                    JFrame frame = new JFrame("Edit Occurrence");
                                    frame.add(new EditOccurrencePanel(occurrence, (StaffModulePanel) parent, frame, ans));
                                    frame.pack();
                                    frame.setResizable(false);
                                    frame.setLocationRelativeTo(null);
                                    frame.setVisible(true);
                                }
                            }
                            case 1 -> {
                                if(JOptionPane.showConfirmDialog(thisPanel, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                                    module.getOccurrences().remove(occurrence);
                                    ((StaffModulePanel) parent).updateAllOccurrences();
                                    parent.redraw();
                                    DataManager.storeModules();
                                }
                            }
                        }
                    }
                }
            });

            if(isCoordinator){
                setBorder(new BevelBorder(BevelBorder.RAISED));
                setBackground(new Color(217, 237, 247));
            } else {
                setBorder(new BevelBorder(BevelBorder.LOWERED));
            }

            if(isRegistered){
                setBackground(new Color(80, 200, 220));
            }
        }

        moduleTitleLabel.setText(String.format("<html>%s - %s</html>", module.getCode(), module.getTitle()));
        occurrenceNumberLabel.setText(Integer.toString(occurrence.getOccurrenceNumber()));
        activityLabel.setText(occurrence.getActivityString());
        timeLabel.setText(occurrence.getTime());
        tutorLabel.setText(String.format("<html>%s</html>", occurrence.getTutor()));
        creditsLabel.setText(Integer.toString(module.getCredit()));
        targetLabel.setText(Integer.toString(occurrence.getTargetStudents()));
        actualLabel.setText(Integer.toString(occurrence.getActualStudents()));

        Dimension size = new Dimension(982, 80);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);

        setHeights(75);
    }

    public boolean isNotOverlapping(String codeAndOcc){
        for (String registered: Main.currentUser.getOccurrences()) {
            if(Occurrence.checkOverlapOfCodeAndOcc(codeAndOcc, registered)){
                String message = String.format("This occurrence conflicts with %s", registered.split("_")[0]);
                String title = "Unable to register";
                JOptionPane.showMessageDialog(thisPanel, message, title, JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
        return true;
    }

    void setHeaderMode(ModulePanel parent){
        occurrenceNumberLabel.setText("Occurrence");
        activityLabel.setText("Activity");
        timeLabel.setText("Time");
        tutorLabel.setText("Tutor");
        creditsLabel.setText("Credits");
        targetLabel.setText("Target");
        actualLabel.setText("Actual");

        Color fontColor = Color.WHITE;
        occurrenceNumberLabel.setForeground(fontColor);
        activityLabel.setForeground(fontColor);
        timeLabel.setForeground(fontColor);
        tutorLabel.setForeground(fontColor);
        creditsLabel.setForeground(fontColor);
        targetLabel.setForeground(fontColor);
        actualLabel.setForeground(fontColor);

        setHeights(40);

        JPanel holder = new JPanel();
        holder.setPreferredSize(new Dimension(184, 39));

        JTextField moduleSearchField = new JTextField();
        moduleSearchField.setToolTipText("Module Search");
        moduleSearchField.setPreferredSize(new Dimension(150, 30));
        moduleSearchField.setMargin(new Insets(0, 5, 0, 5));
        moduleSearchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                parent.filter(moduleSearchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                parent.filter(moduleSearchField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });

        holder.setLayout(new FlowLayout(FlowLayout.LEFT));
        holder.add(moduleSearchField);

        remove(moduleTitleLabel);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        add(holder, c);

        Border border1 = BorderFactory.createCompoundBorder(new BevelBorder(BevelBorder.RAISED), new BevelBorder(BevelBorder.LOWERED));
        Border border = BorderFactory.createCompoundBorder(border1, new BevelBorder(BevelBorder.RAISED));
        setBorder(border);

        Color background = new Color(5, 49, 121);
        holder.setBackground(background);
        setBackground(background);
    }

    void setHeights(int height){
        Dimension bigLabelSize = new Dimension(200, height);
        Dimension smallLabelSize = new Dimension(70, height);
        Dimension occurrenceLabelSize = new Dimension(90, height);
        Dimension activityLabelSize = new Dimension(75, height);

        moduleTitleLabel.setPreferredSize(bigLabelSize);
        occurrenceNumberLabel.setPreferredSize(occurrenceLabelSize);
        activityLabel.setPreferredSize(activityLabelSize);
        timeLabel.setPreferredSize(bigLabelSize);
        tutorLabel.setPreferredSize(bigLabelSize);
        creditsLabel.setPreferredSize(smallLabelSize);
        targetLabel.setPreferredSize(smallLabelSize);
        actualLabel.setPreferredSize(smallLabelSize);
    }


    OccurrencePanel(){
        int padding = 10;
        EmptyBorder paddingBorder = new EmptyBorder(0, padding, 0, padding);

        moduleTitleLabel.setBorder(paddingBorder);
        occurrenceNumberLabel.setBorder(paddingBorder);
        activityLabel.setBorder(paddingBorder);
        timeLabel.setBorder(paddingBorder);
        tutorLabel.setBorder(paddingBorder);
        creditsLabel.setBorder(paddingBorder);
        targetLabel.setBorder(paddingBorder);
        actualLabel.setBorder(paddingBorder);

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        add(moduleTitleLabel, c);

        c.gridx = 1;
        add(occurrenceNumberLabel, c);

        c.gridx = 2;
        add(activityLabel, c);

        c.gridx = 3;
        add(timeLabel, c);

        c.gridx = 4;
        add(tutorLabel, c);

        c.gridx = 5;
        add(creditsLabel);

        c.gridx = 6;
        add(targetLabel, c);

        c.gridx = 7;
        add(actualLabel, c);
    }
}