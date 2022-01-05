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
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

// This class is used for creating and editing occurrences and modules. For staff use only.
public class StaffEditorPanel extends JPanel implements ItemListener {
    JTextField codeField;
    JTextField titleField;
    JTextField activityField;
    JTextField timeField;
    JTextField occurrenceField;
    JTextField tutorField;
    JTextField creditsField;
    JTextField targetField;
    JTextField activityModTypeField;

    JComboBox<String> codeComboBox;
    JComboBox<String> activityModTypeComboBox;
    JComboBox<String> activityOccTypeComboBox;

    JPanel checkBoxesPanel;
    JCheckBox csnCheckBox;
    JCheckBox aiCheckBox;
    JCheckBox isCheckBox;
    JCheckBox seCheckBox;
    JCheckBox mmCheckBox;
    JCheckBox dsCheckBox;

    JLabel codeLabel;
    JLabel titleLabel;
    JLabel activityLabel;
    JLabel timeLabel;
    JLabel occurrenceLabel;
    JLabel tutorLabel;
    JLabel creditsLabel;
    JLabel targetLabel;
    JLabel activityModTypeLabel;
    JLabel programmesLabel;

    JButton saveButton;

    StaffModulePanel parent;
    JFrame frame;

    String restrictions = "";

    StaffEditorPanel(StaffModulePanel parent, JFrame frame){
        this.parent = parent;
        this.frame = frame;

        int fieldHeight = 30;
        Dimension bigFieldSize = new Dimension(300, fieldHeight);
        Dimension smallestFieldSize = new Dimension(100, fieldHeight);

        Insets fieldInsets = new Insets(2, 5, 3, 5);

        codeField = new JTextField();
        codeField.setPreferredSize(smallestFieldSize);
        codeField.setMargin(fieldInsets);

        titleField = new JTextField();
        titleField.setPreferredSize(bigFieldSize);
        titleField.setMargin(fieldInsets);

        activityField = new JTextField();
        activityField.setPreferredSize(smallestFieldSize);
        activityField.setMargin(fieldInsets);

        timeField = new JTextField();
        timeField.setPreferredSize(bigFieldSize);
        timeField.setMargin(fieldInsets);

        occurrenceField = new JTextField();
        occurrenceField.setPreferredSize(smallestFieldSize);
        occurrenceField.setMargin(fieldInsets);

        tutorField = new JTextField();
        tutorField.setPreferredSize(bigFieldSize);
        tutorField.setMargin(fieldInsets);

        creditsField = new JTextField();
        creditsField.setPreferredSize(smallestFieldSize);
        creditsField.setMargin(fieldInsets);

        targetField = new JTextField();
        targetField.setPreferredSize(smallestFieldSize);
        targetField.setMargin(fieldInsets);

        activityModTypeField = new JTextField();
        activityModTypeField.setPreferredSize(bigFieldSize);
        activityField.setMargin(fieldInsets);

        String[] codes = Main.modules.keySet().stream().filter(s -> Main.modules.get(s).getCoordinator().equalsIgnoreCase(Main.currentUser.getFullName())).toArray(String[]::new);
        String[] optionsCode = new String[codes.length + 1];
        optionsCode[0] = "SELECT";
        System.arraycopy(codes, 0, optionsCode, 1, codes.length);

        codeComboBox = new JComboBox<>(optionsCode);
        codeComboBox.setPreferredSize(smallestFieldSize);

        String[] optionsMod = new String[]{"SELECT", "Lecture only", "Lecture & Tutorial", "Tutorial only"};
        activityModTypeComboBox = new JComboBox<>(optionsMod);
        activityModTypeComboBox.setPreferredSize(bigFieldSize);
        activityModTypeComboBox.setBackground(ColorsManager.whiteComboBox);

        String[] optionsOcc = new String[]{"SELECT", "Lecture", "Tutorial"};
        activityOccTypeComboBox = new JComboBox<>(optionsOcc);
        activityOccTypeComboBox.setPreferredSize(smallestFieldSize);
        activityOccTypeComboBox.setBackground(ColorsManager.whiteComboBox);

        csnCheckBox = new JCheckBox("CSN");
        csnCheckBox.setBackground(ColorsManager.editorCheckBoxes);
        csnCheckBox.setFocusPainted(false);
        csnCheckBox.addItemListener(this);

        aiCheckBox = new JCheckBox("Artificial Intelligence");
        aiCheckBox.setBackground(ColorsManager.editorCheckBoxes);
        aiCheckBox.setFocusPainted(false);
        aiCheckBox.addItemListener(this);

        isCheckBox = new JCheckBox("Information Systems");
        isCheckBox.setBackground(ColorsManager.editorCheckBoxes);
        isCheckBox.setFocusPainted(false);
        isCheckBox.addItemListener(this);

        seCheckBox = new JCheckBox("Software Engineering");
        seCheckBox.setBackground(ColorsManager.editorCheckBoxes);
        seCheckBox.setFocusPainted(false);
        seCheckBox.addItemListener(this);

        mmCheckBox = new JCheckBox("Multimedia");
        mmCheckBox.setBackground(ColorsManager.editorCheckBoxes);
        mmCheckBox.setFocusPainted(false);
        mmCheckBox.addItemListener(this);

        dsCheckBox = new JCheckBox("Data Science");
        dsCheckBox.setBackground(ColorsManager.editorCheckBoxes);
        dsCheckBox.setFocusPainted(false);
        dsCheckBox.addItemListener(this);

        codeLabel = new JLabel("Code");
        titleLabel = new JLabel("Title");
        activityLabel = new JLabel("Activity");
        timeLabel = new JLabel("Time");
        occurrenceLabel = new JLabel("Occurrence");
        tutorLabel = new JLabel("Tutor");
        creditsLabel = new JLabel("Credits");
        targetLabel = new JLabel("Target");
        activityModTypeLabel = new JLabel("Activity Type");
        programmesLabel = new JLabel("Eligible Programmes");

        saveButton = new JButton("Save");
        saveButton.setFocusPainted(false);

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        setLayout(layout);

        Insets insets = new Insets(2, 0, 2, 0);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = insets;
        add(codeLabel, c);

        c.gridy = 1;
        add(codeField, c);

        c.gridy = 2;
        add(activityLabel, c);

        c.gridy = 3;
        add(activityField, c);

        c.gridy = 4;
        add(occurrenceLabel, c);

        c.gridy = 5;
        add(occurrenceField, c);

        c.gridy = 6;
        add(creditsLabel, c);

        c.gridx = 1;
        add(targetLabel, c);

        c.gridx = 0;
        c.gridy = 7;
        add(creditsField, c);

        c.gridx = 1;
        add(targetField, c);

        c.gridy = 0;
        c.gridx = 1;
        c.gridwidth = 3;
        add(titleLabel, c);

        c.gridy = 1;
        add(titleField, c);

        c.gridy = 2;
        add(timeLabel, c);

        c.gridy = 3;
        add(timeField, c);

        c.gridy = 4;
        add(tutorLabel, c);

        c.gridy = 5;
        add(tutorField, c);

        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 2;
        add(programmesLabel, c);

        checkBoxesPanel = new JPanel();
        Border border = BorderFactory.createCompoundBorder(new BevelBorder(BevelBorder.RAISED), new BevelBorder(BevelBorder.LOWERED));
        checkBoxesPanel.setBorder(border);
        checkBoxesPanel.setBackground(ColorsManager.editorCheckBoxes);
        checkBoxesPanel.setLayout(new GridBagLayout());

        c.gridy = 9;
        checkBoxesPanel.add(aiCheckBox, c);
        c.gridx = 2;
        c.gridy = 9;
        checkBoxesPanel.add(csnCheckBox, c);
        c.gridx = 0;
        c.gridy = 10;
        checkBoxesPanel.add(seCheckBox, c);
        c.gridx = 2;
        c.gridy = 10;
        checkBoxesPanel.add(mmCheckBox, c);
        c.gridx = 0;
        c.gridy = 11;
        checkBoxesPanel.add(isCheckBox, c);
        c.gridx = 2;
        c.gridy = 11;
        checkBoxesPanel.add(dsCheckBox, c);

        c.gridy = 9;
        c.gridx = 0;
        add(checkBoxesPanel, c);

        c.gridy = 15;
        c.gridwidth = 4;
        c.gridx = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.ipadx = 75;
        c.ipady = 10;
        insets.top = 10;
        add(saveButton, c);

        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(ColorsManager.editorBackground);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        restrictions = String.format("%s%s%s%s%s%s: -1",
                csnCheckBox.isSelected() ? StudentAccount.PROGRAMME_CSN + ", " : "",
                aiCheckBox.isSelected() ? StudentAccount.PROGRAMME_AI + ", " : "",
                isCheckBox.isSelected() ? StudentAccount.PROGRAMME_IS + ", " : "",
                seCheckBox.isSelected() ? StudentAccount.PROGRAMME_SE + ", " : "",
                mmCheckBox.isSelected() ? StudentAccount.PROGRAMME_MM + ", " : "",
                dsCheckBox.isSelected() ? StudentAccount.PROGRAMME_DS + ", " : "").replace(", : ", " : ");

        if(restrictions.equals(": -1")){
            restrictions = "-1 : -1";
        }
    }

    void updateCheckBoxes(){
        if(restrictions.isBlank() || restrictions.equals("-1 : -1")){
            csnCheckBox.setSelected(true);
            aiCheckBox.setSelected(true);
            isCheckBox.setSelected(true);
            seCheckBox.setSelected(true);
            mmCheckBox.setSelected(true);
            dsCheckBox.setSelected(true);
        } else {
            String programmeRestriction = restrictions.substring(0, restrictions.indexOf(" : "));
            csnCheckBox.setSelected(programmeRestriction.contains(StudentAccount.PROGRAMME_CSN));
            aiCheckBox.setSelected(programmeRestriction.contains(StudentAccount.PROGRAMME_AI));
            isCheckBox.setSelected(programmeRestriction.contains(StudentAccount.PROGRAMME_IS));
            seCheckBox.setSelected(programmeRestriction.contains(StudentAccount.PROGRAMME_SE));
            mmCheckBox.setSelected(programmeRestriction.contains(StudentAccount.PROGRAMME_MM));
            dsCheckBox.setSelected(programmeRestriction.contains(StudentAccount.PROGRAMME_DS));
        }
    }

    void removeCheckBoxes(){
        remove(programmesLabel);
        remove(checkBoxesPanel);
    }

    // Called when this class is used for editing an occurrence.
    void setEditOccurrenceMode(Occurrence occ){
        frame.setTitle("Edit Occurrence");

        removeCheckBoxes();

        Module module = Main.modules.get(occ.getCode());

        codeField.setText(occ.getCode());
        titleField.setText(module.getTitle());
        activityField.setText(occ.getActivityString());
        timeField.setText(occ.getTime());
        occurrenceField.setText(String.valueOf(occ.getOccurrenceNumber()));
        tutorField.setText(occ.getTutor());
        creditsField.setText(String.valueOf(module.getCredit()));
        targetField.setText(String.valueOf(occ.getTargetStudents()));

        codeField.setEditable(false);
        titleField.setEditable(false);
        occurrenceField.setEditable(false);
        creditsField.setEditable(false);
        activityField.setEditable(false);

        saveButton.addActionListener(e -> {
            String title = "Unable to edit";
            if (!codeField.getText().isBlank() && !titleField.getText().isBlank() &&
                    !activityField.getText().isBlank() && !timeField.getText().isBlank() &&
                    !occurrenceField.getText().isBlank() && !tutorField.getText().isBlank() &&
                    !creditsField.getText().isBlank() && !targetField.getText().isBlank()){
                if(Occurrence.isValidTime(timeField.getText())){
                    if(targetField.getText().matches("\\d+")){
                        module.getOccurrences().remove(occ);

                        Occurrence newOcc = new Occurrence(codeField.getText(), tutorField.getText(), timeField.getText(),
                                Integer.parseInt(targetField.getText()), Integer.parseInt(occurrenceField.getText()),
                                Occurrence.getActivityFromString(activityField.getText()));

                        module.getOccurrences().add(newOcc);

                        parent.updateAllOccurrences();
                        parent.redraw();

                        DataManager.storeModulesJSON();

                        frame.dispose();
                    } else {
                        String message = "Please enter a valid number for the target.";
                        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    String message = "Please enter a valid time format.";
                    JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
                }
            } else {
                String message = "Please fill all the fields.";
                JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    // Called when this class is used for creating an occurrence.
    void setAddOccurrenceMode(){
        frame.setTitle("Add Occurrence");

        remove(codeField);
        remove(activityField);
        removeCheckBoxes();

        titleField.setEditable(false);
        creditsField.setEditable(false);

        GridBagConstraints c = new GridBagConstraints();

        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(codeComboBox, c);

        c.gridy = 3;
        add(activityOccTypeComboBox, c);

        codeComboBox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                if(codeComboBox.getSelectedIndex() != 0){
                    Module selection = Main.modules.get((String) codeComboBox.getSelectedItem());

                    titleField.setText(selection.getTitle());
                    creditsField.setText(String.valueOf(selection.getCredit()));
                    activityOccTypeComboBox.setEnabled(true);
                } else {
                    titleField.setText("");
                    creditsField.setText("");
                    activityOccTypeComboBox.setEnabled(false);
                }
            }
        });

        activityOccTypeComboBox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                String title = "Invalid activity type";
                Module selected = Main.modules.get((String) codeComboBox.getSelectedItem());

                if(selected.getActivityType() == Module.MOD_TYPE_LECTURE && activityOccTypeComboBox.getSelectedIndex() == 2){
                    String message = String.format("%s only accepts Lecture type occurrences.", selected.getCode());
                    JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
                    activityOccTypeComboBox.setSelectedIndex(1);
                } else if (selected.getActivityType() == Module.MOD_TYPE_TUTORIAL && activityOccTypeComboBox.getSelectedIndex() == 1){
                    String message = String.format("%s only accepts Tutorial type occurrences.", selected.getCode());
                    JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
                    activityOccTypeComboBox.setSelectedIndex(2);
                }
            }
        });

        saveButton.addActionListener(e -> {
            String title = "Unable to create";
            if (codeComboBox.getSelectedIndex() != 0 && !titleField.getText().isBlank() &&
                    activityOccTypeComboBox.getSelectedIndex() != 0 && !timeField.getText().isBlank() &&
                    !occurrenceField.getText().isBlank() && !tutorField.getText().isBlank() &&
                    !creditsField.getText().isBlank() && !targetField.getText().isBlank()){
                if(!Main.modules.get((String) codeComboBox.getSelectedItem()).containsOccurrence(Integer.parseInt(occurrenceField.getText()), activityOccTypeComboBox.getSelectedIndex() - 1)){
                    if(Occurrence.isValidTime(timeField.getText())){
                        if(occurrenceField.getText().matches("\\d+")){
                            if(targetField.getText().matches("\\d+")){
                                String code = (String) codeComboBox.getSelectedItem();

                                Occurrence newOcc = new Occurrence(code, tutorField.getText(), timeField.getText(),
                                        Integer.parseInt(targetField.getText()), Integer.parseInt(occurrenceField.getText()),
                                        activityOccTypeComboBox.getSelectedIndex() - 1);

                                Main.modules.get(code).getOccurrences().add(newOcc);

                                parent.updateAllOccurrences();
                                parent.redraw();

                                DataManager.storeModulesJSON();

                                frame.dispose();
                            } else {
                                String message = "Please enter a valid number for the target.";
                                JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            String message = "Please enter a valid number for the occurrence.";
                            JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        String message = "Please enter a valid time.";
                        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    String message = String.format("Occurrence %s already has a created %s class.", occurrenceField.getText(), activityOccTypeComboBox.getSelectedItem());
                    JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
                }
            } else {
                String message = "Please fill all the fields.";
                JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    // Called when this class is used for editing a module.
    void setEditModuleMode(){
        setModuleMode();

        frame.setTitle("Edit Module");

        remove(codeField);

        creditsField.setEditable(false);

        GridBagConstraints c = new GridBagConstraints();

        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(codeComboBox, c);

        saveButton.addActionListener(e -> {
            if(codeComboBox.getSelectedIndex() != 0 && !titleField.getText().isBlank()
                    && !creditsField.getText().isBlank() && activityModTypeComboBox.getSelectedIndex() != 0) {
                Module module = Main.modules.get((String) codeComboBox.getSelectedItem());

                Main.modules.remove(module.getCode());
                Module newModule = new Module(module.getCode(), titleField.getText(), module.getCoordinator(), restrictions, Integer.parseInt(creditsField.getText()),
                        activityModTypeComboBox.getSelectedIndex() - 1, module.getOccurrences());

                Main.modules.put(module.getCode(), newModule);

                parent.updateAllOccurrences();
                parent.redraw();

                DataManager.storeModulesJSON();

                frame.dispose();
            } else {
                String message = "Please fill all the fields.";
                String title = "Unable to edit";
                JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    // Called when this class is used for creating a module.
    void setAddModuleMode(){
        setModuleMode();

        frame.setTitle("Create Module");

        saveButton.addActionListener(e -> {
            String title = "Unable to create";
            if(!codeField.getText().isBlank() && !titleField.getText().isBlank()
                    && !creditsField.getText().isBlank() && activityModTypeComboBox.getSelectedIndex() != 0) {
                if(!Main.modules.containsKey(codeField.getText().toUpperCase())){
                    if(codeField.getText().toUpperCase().matches("^[A-Z]{3}[0-9]{4}$")){
                        if(creditsField.getText().matches("\\d+")){

                            Module newModule = new Module(codeField.getText().toUpperCase(), titleField.getText(), Main.currentUser.getFullName(),
                                    restrictions, Integer.parseInt(creditsField.getText()),
                                    activityModTypeComboBox.getSelectedIndex() - 1, new ArrayList<>());

                            Main.modules.put(newModule.getCode(), newModule);

                            DataManager.storeModulesJSON();

                            frame.dispose();
                        } else {
                            String message = "Please enter a valid number for the credits.";
                            JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        String message = "Please enter a valid code.";
                        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    String message = "A module with this code already exists.";
                    JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
                }
            } else {
                String message = "Please fill all the fields.";
                JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    // Called when this class is used for deleting a module.
    // This doesn't delete the module itself, it only deletes all of its occurrences.
    void setDeleteModuleMode(){
        setModuleMode();

        frame.setTitle("Delete Module");

        remove(activityModTypeComboBox);
        remove(codeField);
        removeCheckBoxes();

        titleField.setEditable(false);
        creditsField.setEditable(false);
        activityModTypeField.setEditable(false);

        activityModTypeComboBox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                if(activityModTypeComboBox.getSelectedIndex() != 0){
                    activityModTypeField.setText((String) activityModTypeComboBox.getSelectedItem());
                }else{
                    activityModTypeField.setText("");
                }
            }
        });

        saveButton.setText("Delete");
        saveButton.addActionListener(e -> {
            if(codeComboBox.getSelectedIndex() != 0){
                if(JOptionPane.showConfirmDialog(frame, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                    Main.modules.get((String) codeComboBox.getSelectedItem()).getOccurrences().clear();

                    parent.updateAllOccurrences();
                    parent.redraw();

                    DataManager.storeModulesJSON();

                    frame.dispose();
                }
            }
        });

        GridBagConstraints c = new GridBagConstraints();

        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(codeComboBox, c);

        c.gridwidth = 3;
        c.gridx = 1;
        c.gridy = 7;
        add(activityModTypeField, c);
    }

    // This is called inside setEditModuleMode, setAddModuleMode, setDeleteModuleMode.
    // It contains a bunch of instructions that are used in all three methods,
    // so putting them in a separate method makes the code shorter, rather than repeating the same code in three places.
    void setModuleMode(){
        remove(activityLabel);
        remove(activityField);
        remove(timeLabel);
        remove(timeField);
        remove(occurrenceLabel);
        remove(occurrenceField);
        remove(tutorLabel);
        remove(tutorField);
        remove(targetLabel);
        remove(targetField);

        updateCheckBoxes();

        codeComboBox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                if(codeComboBox.getSelectedIndex() != 0){
                    Module selection = Main.modules.get((String) codeComboBox.getSelectedItem());

                    titleField.setText(selection.getTitle());
                    creditsField.setText(String.valueOf(selection.getCredit()));
                    activityModTypeComboBox.setSelectedIndex(selection.getActivityType() + 1);
                    restrictions = selection.getRestrictions();
                    updateCheckBoxes();
                } else {
                    titleField.setText("");
                    creditsField.setText("");
                    activityModTypeComboBox.setSelectedIndex(0);
                    restrictions = "";
                    updateCheckBoxes();
                }
            }
        });

        GridBagConstraints c = new GridBagConstraints();

        c.gridwidth = 3;
        c.gridx = 1;
        c.gridy = 6;
        c.anchor = GridBagConstraints.LINE_START;
        add(activityModTypeLabel, c);

        c.gridy = 7;
        add(activityModTypeComboBox, c);
    }
}
