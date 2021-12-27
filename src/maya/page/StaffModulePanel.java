package maya.page;

import maya.Main;
import maya.object.Module;
import maya.object.Occurrence;
import maya.util.DataManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StaffModulePanel extends ModulePanel {

    List<Occurrence> allOccurrences = new ArrayList<>();
    List<Occurrence> availableOccurrences = new ArrayList<>();
    JPanel container;
    JPanel registeredContainer;

    public StaffModulePanel() {
        updateAllOccurrences();

        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(1000, 550));
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        OccurrencePanel header = new OccurrencePanel();
        header.setHeaderMode(this);

        registeredContainer = new JPanel();
        registeredContainer.setLayout(new BoxLayout(registeredContainer, BoxLayout.Y_AXIS));

        JScrollPane registeredScrollPane = new JScrollPane(registeredContainer);
        registeredScrollPane.setPreferredSize(new Dimension(200, 303));
        registeredScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        RegisteredOccurrenceHeaderPanel registeredHeader = new RegisteredOccurrenceHeaderPanel(this);

        JButton timetableButton = new JButton("View Timetable");
        timetableButton.addActionListener(e -> {
            JFrame timetableFrame = new JFrame("Timetable");
            timetableFrame.add(new TimetablePanel(TimetablePanel.STAFF_MODE));
            timetableFrame.pack();
            timetableFrame.setResizable(false);
            timetableFrame.setLocationRelativeTo(null);
            timetableFrame.setVisible(true);
        });
        timetableButton.setFocusPainted(false);

        JButton editorButton = new JButton("Module Editor");
        editorButton.setFocusPainted(false);
        editorButton.addActionListener(e -> {
            String message = "Choose an option";
            String title = "Module Editor";
            String[] options = new String[]{"Add", "Edit", "Delete"};
            int ans = JOptionPane.showOptionDialog(this, message, title, JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            JFrame frame = new JFrame();
            StaffEditorPanel editor = new StaffEditorPanel(this, frame);
            frame.add(editor);
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            switch (ans) {
                case 0 -> {
                    message = "Do you want to add an occurrence or add a new module?";
                    title = "Add";
                    options = new String[]{"Add occurrence", "Add module", "Cancel"};
                    ans = JOptionPane.showOptionDialog(this, message, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    switch (ans) {
                        case 0 -> {
                            editor.setAddOccurrenceMode();
                            frame.setVisible(true);
                        }
                        case 1 -> {
                            editor.setAddModuleMode();
                            frame.setVisible(true);
                        }
                        default -> frame.dispose();
                    }
                }
                case 1 -> {
                    editor.setEditModuleMode();
                    frame.setVisible(true);
                }
                case 2 -> {
                    editor.setDeleteModuleMode();
                    frame.setVisible(true);
                }
                default -> frame.dispose();
            }
        });

        JButton logoutButton = new JButton("Log out");
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> {
            Main.currentUser = null;

            MainFrame.getFrame().showCard(MainFrame.LOGIN_AND_SIGNUP_KEY);

            DataManager.updateRememberMe(false);
        });

        redraw();

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        Insets insets = new Insets(0, 0, 0, 0);

        c.insets = insets;
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(header, c);

        c.gridy = 1;
        c.gridheight = 5;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        add(scrollPane, c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 1;
        insets.left = 30;
        c.anchor = GridBagConstraints.PAGE_END;
        add(registeredHeader, c);

        c.gridy = 1;
        c.anchor = GridBagConstraints.PAGE_START;
        add(registeredScrollPane, c);

        c.gridy = 3;
        c.ipady = 25;
        insets.top = 20;
        add(timetableButton, c);

        c.gridy = 4;
        c.ipady = 25;
        // Check that current user is the coordinator of at least one module
        for (Module m : Main.modules.values()) {
            if (m.getCoordinator().equalsIgnoreCase(Main.currentUser.getFullName())) {
                add(editorButton, c);
                break;
            }
        }

        c.gridy = 5;
//        insets.top = 0;
        add(logoutButton, c);

        setBackground(new Color(230, 230, 180));
    }

    public void updateAllOccurrences(){
        allOccurrences.clear();
        Main.modules.values().forEach(m -> allOccurrences.addAll(m.getOccurrences()));
        allOccurrences.sort(new OccurrenceComparator());

        availableOccurrences.clear();
        availableOccurrences.addAll(allOccurrences);
    }

    @Override
    public void redraw(){
        container.removeAll();
        registeredContainer.removeAll();

        for (Occurrence occ: availableOccurrences) {
            OccurrencePanel occPanel = new OccurrencePanel();
            occPanel.setOccurrence(occ, this);
            container.add(occPanel);
        }

        for(String occString: Main.currentUser.getOccurrences()){
            if(!occString.isBlank()){
                RegisteredOccurrencePanel registeredOccPanel = new RegisteredOccurrencePanel(occString, this);
                registeredContainer.add(registeredOccPanel);
            }
        }

        container.revalidate();
        container.repaint();
        registeredContainer.revalidate();
        registeredContainer.repaint();
    }

    @Override
    public void filter(String toFilter){
        for (Occurrence occ: allOccurrences) {
            String title = String.format("%s - %s", occ.getCode(), Main.modules.get(occ.getCode()).getTitle());

            if(title.toLowerCase().contains(toFilter.toLowerCase())){
                if(!availableOccurrences.contains(occ)){
                    availableOccurrences.add(occ);
                }
            } else {
                availableOccurrences.remove(occ);
            }
        }

        availableOccurrences.sort(new OccurrenceComparator());
        redraw();
    }
}
