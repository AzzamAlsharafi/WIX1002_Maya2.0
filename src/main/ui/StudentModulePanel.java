package main.ui;

import main.Main;
import main.object.Occurrence;
import main.util.ColorsManager;
import main.util.DataManager;
import main.util.OccurrenceComparator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// This class is the home page for staff users.
public class StudentModulePanel extends ModulePanel {

    JPanel container;
    JPanel registeredContainer;
    TotalCreditsPanel creditsPanel;

    int currentCredits = 0;

    public StudentModulePanel(){
        Main.modules.values().forEach(m -> allOccurrences.addAll(m.getOccurrences()));
        allOccurrences.sort(new OccurrenceComparator());
        availableOccurrences.addAll(allOccurrences);

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

        creditsPanel = new TotalCreditsPanel(currentCredits);

        JButton timetableButton = new JButton("View Timetable");
        timetableButton.addActionListener(e -> {
            JFrame timetableFrame = new JFrame("Timetable");
            timetableFrame.add(new TimetablePanel(TimetablePanel.STUDENT_MODE, this));
            timetableFrame.pack();
            timetableFrame.setResizable(false);
            timetableFrame.setLocationRelativeTo(null);
            timetableFrame.setVisible(true);
        });
        timetableButton.setFocusPainted(false);

        JButton logoutButton = new JButton("Log out");
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> {
            Main.currentUser = null;

            MainFrame.getFrame().showCard(MainFrame.LOGIN_AND_SIGNUP_KEY);

            DataManager.updateRememberMeJSON(false);
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

        c.gridy = 2;
        insets.top = 15;
        add(creditsPanel, c);

        c.gridy = 3;
        c.ipady = 25;
        add(timetableButton, c);

        c.gridy = 5;
        add(logoutButton, c);

        setBackground(ColorsManager.studentModuleBackground);
    }

    // Reload the page. Used when the lists of occurrences have been updated.
    @Override
    void redraw(){
        container.removeAll();
        registeredContainer.removeAll();
        currentCredits = 0;

        for (Occurrence occ: availableOccurrences) {
            OccurrencePanel occPanel = new OccurrencePanel();
            occPanel.setOccurrence(occ, this);
            container.add(occPanel);
        }

        for(String occString: Main.currentUser.getOccurrences()){
            if(!occString.isBlank()){
                RegisteredOccurrencePanel registeredOccPanel = new RegisteredOccurrencePanel(occString, this);
                registeredContainer.add(registeredOccPanel);

                currentCredits += Main.modules.get(occString.split("_")[0]).getCredit();
            }
        }
        creditsPanel.currentBodyLabel.setText(Integer.toString(currentCredits));

        container.revalidate();
        container.repaint();
        registeredContainer.revalidate();
        registeredContainer.repaint();
    }
}