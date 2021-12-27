package maya.page;

import maya.Main;
import maya.object.Module;
import maya.object.Occurrence;
import maya.util.ColorsManager;
import maya.util.DataManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class StaffEditorPanel extends JPanel {
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

    JLabel codeLabel;
    JLabel titleLabel;
    JLabel activityLabel;
    JLabel timeLabel;
    JLabel occurrenceLabel;
    JLabel tutorLabel;
    JLabel creditsLabel;
    JLabel targetLabel;
    JLabel activityModTypeLabel;

    JButton saveButton;

    StaffModulePanel parent;
    JFrame frame;

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

        codeLabel = new JLabel("Code");
        titleLabel = new JLabel("Title");
        activityLabel = new JLabel("Activity");
        timeLabel = new JLabel("Time");
        occurrenceLabel = new JLabel("Occurrence");
        tutorLabel = new JLabel("Tutor");
        creditsLabel = new JLabel("Credits");
        targetLabel = new JLabel("Target");
        activityModTypeLabel = new JLabel("Activity Type");

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

        c.gridy = 8;
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

    void setEditOccurrenceMode(Occurrence occ){
        frame.setTitle("Edit Occurrence");

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
                                Integer.parseInt(targetField.getText()), occ.getActualStudents(),
                                Integer.parseInt(occurrenceField.getText()),
                                Occurrence.getActivityFromString(activityField.getText()), occ.getStudents());

                        module.getOccurrences().add(newOcc);

                        parent.updateAllOccurrences();
                        parent.redraw();

                        DataManager.storeModules();

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

    void setAddOccurrenceMode(){
        frame.setTitle("Add Occurrence");

        remove(codeField);
        remove(activityField);

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
                } else {
                    titleField.setText("");
                    creditsField.setText("");
                }
            }
        });

        saveButton.addActionListener(e -> {
            String title = "Unable to create";
            if (codeComboBox.getSelectedIndex() != 0 && !titleField.getText().isBlank() &&
                    activityOccTypeComboBox.getSelectedIndex() != 0 && !timeField.getText().isBlank() &&
                    !occurrenceField.getText().isBlank() && !tutorField.getText().isBlank() &&
                    !creditsField.getText().isBlank() && !targetField.getText().isBlank()){
                if(Occurrence.isValidTime(timeField.getText())){
                    if(occurrenceField.getText().matches("\\d+")){
                        if(targetField.getText().matches("\\d+")){
                            String code = (String) codeComboBox.getSelectedItem();

                            Occurrence newOcc = new Occurrence(code, tutorField.getText(), timeField.getText(),
                                    Integer.parseInt(targetField.getText()), 0,
                                    Integer.parseInt(occurrenceField.getText()),
                                    activityOccTypeComboBox.getSelectedIndex() - 1, new ArrayList<>());

                            Main.modules.get(code).getOccurrences().add(newOcc);

                            parent.updateAllOccurrences();
                            parent.redraw();

                            DataManager.storeModules();

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
                String message = "Please fill all the fields.";
                JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
            }
        });
    }

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

                Module newModule = new Module(module.getCode(), titleField.getText(), module.getCoordinator(),
                        module.getOccurrencesCount(), Integer.parseInt(creditsField.getText()),
                        activityModTypeComboBox.getSelectedIndex() - 1, module.getOccurrences());

                Main.modules.put(module.getCode(), newModule);

                parent.updateAllOccurrences();
                parent.redraw();

                DataManager.storeModules();

                frame.dispose();
            } else {
                String message = "Please fill all the fields.";
                String title = "Unable to edit";
                JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
            }
        });
    }

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
                                    0, Integer.parseInt(creditsField.getText()),
                                    activityModTypeComboBox.getSelectedIndex() - 1, new ArrayList<>());

                            Main.modules.put(newModule.getCode(), newModule);

                            DataManager.storeModules();

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

    // This doesn't delete the module itself, it only deletes all of its occurrences.
    void setDeleteModuleMode(){
        setModuleMode();

        frame.setTitle("Delete Module");

        remove(activityModTypeComboBox);
        remove(codeField);

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

                    DataManager.storeModules();

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

        codeComboBox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                if(codeComboBox.getSelectedIndex() != 0){
                    Module selection = Main.modules.get((String) codeComboBox.getSelectedItem());

                    titleField.setText(selection.getTitle());
                    creditsField.setText(String.valueOf(selection.getCredit()));
                    activityModTypeComboBox.setSelectedIndex(selection.getActivityType() + 1);
                } else {
                    titleField.setText("");
                    creditsField.setText("");
                    activityModTypeComboBox.setSelectedIndex(0);
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
