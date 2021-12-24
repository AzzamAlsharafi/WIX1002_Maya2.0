package maya.page;

import maya.Main;
import maya.object.Occurrence;

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

        redraw();

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(header, c);

        c.gridy = 1;
        c.gridheight = 2;
        add(scrollPane, c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 1;
        c.insets = new Insets(0, 30, 0, 0);
        c.anchor = GridBagConstraints.PAGE_END;
        add(registeredHeader, c);

        c.gridy = 1;
        c.anchor = GridBagConstraints.PAGE_START;
        add(registeredScrollPane, c);

        c.gridy = 2;
        c.insets = new Insets(30, 30, 0, 0);
        add(creditsPanel, c);


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