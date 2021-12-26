package maya.page;

import maya.Main;
import maya.object.Module;
import maya.object.Occurrence;
import maya.util.DataManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StaffEditorPanel extends JPanel {
    JTextField codeField;
    JTextField titleField;
    JTextField activityField;
    JTextField timeField;
    JTextField occurrenceField;
    JTextField tutorField;
    JTextField creditsField;
    JTextField targetField;

    JComboBox<String> activityModTypeComboBox;

    JLabel codeLabel;
    JLabel titleLabel;
    JLabel activityLabel;
    JLabel timeLabel;
    JLabel occurrenceLabel;
    JLabel tutorLabel;
    JLabel creditsLabel;
    JLabel targetLabel;

    Module module;

    final static int EDIT_OCCURRENCE_MODE = 0;
    final static int EDIT_MODULE_MODE = 1;

    StaffEditorPanel(Occurrence occ, StaffModulePanel parent, JFrame frame, int mode) {
        module = Main.modules.get(occ.getCode());

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

        String[] options = new String[]{"SELECT", "Lecture only", "Lecture & Tutorial", "Tutorial only"};
        activityModTypeComboBox = new JComboBox<>(options);
        activityModTypeComboBox.setPreferredSize(bigFieldSize);
        activityModTypeComboBox.setBackground(Color.WHITE);

        codeLabel = new JLabel("Code");
        titleLabel = new JLabel("Title");
        activityLabel = new JLabel("Activity");
        timeLabel = new JLabel("Time");
        occurrenceLabel = new JLabel("Occurrence");
        tutorLabel = new JLabel("Tutor");
        creditsLabel = new JLabel("Credits");
        targetLabel = new JLabel("Target");

        JButton saveButton = new JButton("Save");
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> {
            if(mode == EDIT_OCCURRENCE_MODE){
                if (!codeField.getText().isBlank() && !titleField.getText().isBlank() &&
                        !activityField.getText().isBlank() && !timeField.getText().isBlank() &&
                        !occurrenceField.getText().isBlank() && !tutorField.getText().isBlank() &&
                        !creditsField.getText().isBlank() && !targetField.getText().isBlank()){
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
                }
            } else if(mode == EDIT_MODULE_MODE){
                if(!codeField.getText().isBlank() && !titleField.getText().isBlank()
                && !creditsField.getText().isBlank() && activityModTypeComboBox.getSelectedIndex() != 0) {
                    Main.modules.remove(module.getCode());

                    Module newModule = new Module(module.getCode(), titleField.getText(), module.getCoordinator(),
                            module.getOccurrencesCount(), Integer.parseInt(creditsField.getText()),
                            activityModTypeComboBox.getSelectedIndex() - 1, module.getOccurrences());

                    Main.modules.put(module.getCode(), newModule);

                    parent.updateAllOccurrences();
                    parent.redraw();

                    DataManager.storeModules();

                    frame.dispose();
                }
            }
        });

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
        add(titleLabel);

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
        setBackground(new Color(150, 230, 150));

        setData(occ);

        if(mode == EDIT_OCCURRENCE_MODE){
            editOccurrenceMode();
        }else if(mode == EDIT_MODULE_MODE){
            editModuleMode();
        }
    }

    void setData(Occurrence occ){
        codeField.setText(occ.getCode());
        titleField.setText(module.getTitle());
        activityField.setText(occ.getActivityString());
        timeField.setText(occ.getTime());
        occurrenceField.setText(String.valueOf(occ.getOccurrenceNumber()));
        tutorField.setText(occ.getTutor());
        creditsField.setText(String.valueOf(module.getCredit()));
        targetField.setText(String.valueOf(occ.getTargetStudents()));
        activityModTypeComboBox.setSelectedIndex(module.getActivityType() + 1);
    }

    void editOccurrenceMode(){
        codeField.setEditable(false);
        titleField.setEditable(false);
        occurrenceField.setEditable(false);
        creditsField.setEditable(false);
        activityField.setEditable(false);
    }

    void editModuleMode(){
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

        codeField.setEditable(false);
        creditsField.setEditable(false);

        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 3;
        c.gridx = 1;
        c.gridy = 7;
        add(activityModTypeComboBox, c);
    }
}
