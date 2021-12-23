package maya.page;

import maya.Main;
import maya.object.Module;
import maya.object.Occurrence;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StudentModulePanel extends JPanel {

    List<Occurrence> allOccurrences = new ArrayList<>();
    List<Occurrence> availableOccurrences = new ArrayList<>();
    JPanel container;

    public StudentModulePanel(){
        Main.modules.values().forEach(m -> allOccurrences.addAll(m.getOccurrences()));
        allOccurrences.sort(new OccurrenceComparator());
        availableOccurrences.addAll(allOccurrences);

        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        redrawContainer();

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(1000, 550));
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        OccurrencePanel header = new OccurrencePanel();
        header.setHeaderMode(this);

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(header, c);

        c.gridy = 1;
        add(scrollPane, c);

        setBackground(new Color(180, 230, 230));
    }

    void redrawContainer(){
        container.removeAll();

        for (Occurrence occ: availableOccurrences) {
            OccurrencePanel occPanel = new OccurrencePanel();
            occPanel.setOccurrence(occ);
            container.add(occPanel);
        }

        container.revalidate();
        container.repaint();
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
        redrawContainer();
    }
}

class OccurrencePanel extends JPanel{
    Occurrence occurrence;

    JLabel moduleTitleLabel = new JLabel();
    JLabel occurrenceNumberLabel = new JLabel();
    JLabel activityLabel = new JLabel();
    JLabel timeLabel = new JLabel();
    JLabel tutorLabel = new JLabel();
    JLabel creditsLabel = new JLabel();
    JLabel targetLabel = new JLabel();
    JLabel actualLabel = new JLabel();

    void setOccurrence(Occurrence occurrence){
        this.occurrence = occurrence;

        Module module = Main.modules.get(occurrence.getCode());

        moduleTitleLabel.setText(String.format("<html>%s - %s</html>", module.getCode(), module.getTitle()));
        occurrenceNumberLabel.setText(Integer.toString(occurrence.getOccurrenceNumber()));
        activityLabel.setText(occurrence.getActivityString());
        timeLabel.setText(occurrence.getTime());
        tutorLabel.setText(String.format("<html>%s</html>", occurrence.getTutor()));
        creditsLabel.setText(Integer.toString(module.getCredit()));
        targetLabel.setText(Integer.toString(occurrence.getTargetStudents()));
        actualLabel.setText(Integer.toString(occurrence.getActualStudents()));

        setBorder(new BevelBorder(BevelBorder.RAISED));

        setHeights(75);
    }

    void setHeaderMode(StudentModulePanel parent){
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