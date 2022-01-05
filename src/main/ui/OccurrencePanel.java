package main.ui;

import main.Main;
import main.object.Module;
import main.object.Occurrence;
import main.object.StudentAccount;
import main.util.ColorsManager;
import main.util.DataManager;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// This is the class used to display each Occurrence object in StudentModulePanel and StaffModulePanel.
// Each occurrence you see in the big list of occurrences is an instance of this class.
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

    // Call this method when you want to display an Occurrence object in the big list.
    void setOccurrence(Occurrence occurrence, ModulePanel parent){
        this.occurrence = occurrence;
        Module module = Main.modules.get(occurrence.getCode());

        String moduleOcc;
        boolean isRegistered;
        boolean isCoordinator;

        if(parent instanceof StudentModulePanel){ // This section only works if the current logged-in user is a student.
            moduleOcc = String.format("%s_%s", occurrence.getCode(), occurrence.getOccurrenceNumber());
            isRegistered = Main.currentUser.getOccurrences().contains(moduleOcc);

            boolean isAllowed = module.checkRestrictions((StudentAccount) Main.currentUser).isBlank();

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

                                    // Make sure that this occurrence isn't overlapping with an already registered occurrence,
                                    // and that it still hasn't reached the target number of students.
                                    if (isNotOverlapping(moduleOcc) && checkActualStudents(occurrence)) {
                                        Main.currentUser.getOccurrences().add(moduleOcc);
                                        parent.redraw();
                                        DataManager.storeAccountsJSON();
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

                            // Check if this student is eligible to register for this module.
                            String restrictionsResult = module.checkRestrictions((StudentAccount) Main.currentUser);
                            if(restrictionsResult.isEmpty()){

                                // Make sure that this occurrence isn't overlapping with an already registered occurrence,
                                // and that it still hasn't reached the target number of students.
                                if (isNotOverlapping(moduleOcc) && checkActualStudents(occurrence)) {

                                    // Make sure that the students won't exceed max credits by registering this module.
                                    if (((StudentModulePanel) parent).currentCredits + module.getCredit() <= Main.maxCreditsPerStudent) {
                                        Main.currentUser.getOccurrences().add(moduleOcc);
                                        parent.redraw();
                                        DataManager.storeAccountsJSON();
                                    } else {
                                        message = String.format("Registering %s will put you over %d credits.", moduleOcc.split("_")[0], Main.maxCreditsPerStudent);
                                        title = "Unable to register";
                                        JOptionPane.showMessageDialog(thisPanel, message, title, JOptionPane.WARNING_MESSAGE);
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(thisPanel, restrictionsResult, title, JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }
                }
            });

            if(isRegistered){
                setBackground(ColorsManager.studentRegisteredOccurrence);
                setBorder(new BevelBorder(BevelBorder.LOWERED));
            } else {
                setBorder(new BevelBorder(BevelBorder.RAISED));
            }

            if(!isAllowed){
                setBackground(ColorsManager.studentNotAllowedOccurrence);
            }
        } else { // This section only works if the current logged-in user is a staff.
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
                                JFrame frame = new JFrame("Edit Occurrence");
                                StaffEditorPanel editor = new StaffEditorPanel((StaffModulePanel) parent, frame);
                                editor.setEditOccurrenceMode(occurrence);
                                frame.add(editor);
                                frame.pack();
                                frame.setResizable(false);
                                frame.setLocationRelativeTo(null);
                                frame.setVisible(true);
                            }
                            case 1 -> {
                                if(JOptionPane.showConfirmDialog(thisPanel, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                                    module.getOccurrences().remove(occurrence);
                                    ((StaffModulePanel) parent).updateAllOccurrences();
                                    parent.redraw();
                                    DataManager.storeModulesJSON();
                                }
                            }
                        }
                    }
                }
            });

            if(isCoordinator){
                setBorder(new BevelBorder(BevelBorder.RAISED));
                setBackground(ColorsManager.staffCoordinatorOccurrence);
            } else {
                setBorder(new BevelBorder(BevelBorder.LOWERED));
            }

            if(isRegistered){
                setBackground(ColorsManager.staffRegisteredOccurrence);
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

    // Method to check if the occurrence hasn't reached target students.
    public boolean checkActualStudents(Occurrence occ){
        if(occ.getActualStudents() < occ.getTargetStudents()){
            return true;
        }

        String message = "This occurrence has reached max students limit.";
        String title = "Unable to register";
        JOptionPane.showMessageDialog(thisPanel, message, title, JOptionPane.WARNING_MESSAGE);
        return false;
    }

    // Method to check if the occurrence isn't overlapping with an already registered occurrence.
    public boolean isNotOverlapping(String codeAndOcc){
        for (String registered: Main.currentUser.getOccurrences()) {
            if(Occurrence.checkOverlapOfCodeAndOcc(codeAndOcc, registered)){
                String message = String.format("This occurrence conflicts with %s.", registered.split("_")[0]);
                String title = "Unable to register";
                JOptionPane.showMessageDialog(thisPanel, message, title, JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
        return true;
    }

    // This method is called once for the header bar at the top of the big list in StudentModulePanel and StaffModulePanel.
    void setHeaderMode(ModulePanel parent){
        occurrenceNumberLabel.setText("Occurrence");
        activityLabel.setText("Activity");
        timeLabel.setText("Time");
        tutorLabel.setText("Tutor");
        creditsLabel.setText("Credits");
        targetLabel.setText("Target");
        actualLabel.setText("Actual");

        Color fontColor = ColorsManager.whiteFont;
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

        Color background = ColorsManager.signatureDarkBlue;
        holder.setBackground(background);
        setBackground(background);
    }

    void setHeights(int height){
        Dimension bigLabelSize = new Dimension(200, height);
        Dimension tutorLabelSize = new Dimension(190, height);
        Dimension timeLabelSize = new Dimension(210, height);
        Dimension smallLabelSize = new Dimension(70, height);
        Dimension occurrenceLabelSize = new Dimension(90, height);
        Dimension activityLabelSize = new Dimension(75, height);

        moduleTitleLabel.setPreferredSize(bigLabelSize);
        occurrenceNumberLabel.setPreferredSize(occurrenceLabelSize);
        activityLabel.setPreferredSize(activityLabelSize);
        timeLabel.setPreferredSize(timeLabelSize);
        tutorLabel.setPreferredSize(tutorLabelSize);
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