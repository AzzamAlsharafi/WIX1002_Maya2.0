package maya.page;

import maya.Main;
import maya.object.Occurrence;
import maya.object.StudentAccount;
import maya.util.DataManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StudentModulePanel extends JPanel {

    List<Occurrence> allOccurrences = new ArrayList<>();
    List<Occurrence> availableOccurrences = new ArrayList<>();
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

        RegisteredOccurrenceHeaderPanel registeredHeader = new RegisteredOccurrenceHeaderPanel();

        creditsPanel = new TotalCreditsPanel(currentCredits);

        JButton timetableButton = new JButton("View Timetable");
        timetableButton.addActionListener(e -> {
            JFrame timetableFrame = new JFrame("Timetable");
            timetableFrame.add(new TimetablePanel());
            timetableFrame.pack();
            timetableFrame.setResizable(false);
            timetableFrame.setLocationRelativeTo(null);
            timetableFrame.setVisible(true);
        });
        timetableButton.setFocusPainted(false);

        JButton profileButton = new JButton("View Profile");
        profileButton.setFocusPainted(false);

        JButton logoutButton = new JButton("Log out");
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> {
            Main.currentUser = null;

            MainFrame.getFrame().clearLoginPage();

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

        c.gridy = 2;
        insets.top = 15;
        add(creditsPanel, c);

        c.gridy = 3;
        c.ipady = 25;
        add(timetableButton, c);

//        c.gridy = 4;
//        c.ipady = 15;
//        add(profileButton, c);

        c.gridy = 5;
//        insets.top = 0;
        add(logoutButton, c);

        setBackground(new Color(180, 230, 230));
    }

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

    void filter(String toFilter){
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