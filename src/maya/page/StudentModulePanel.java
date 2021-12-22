package maya.page;

import maya.Main;
import maya.object.Module;
import maya.object.Occurrence;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StudentModulePanel extends JPanel {
    public StudentModulePanel(){
        DefaultListModel<Occurrence> listModel = new DefaultListModel<>();

        List<Occurrence> occurrences = new ArrayList<>();

        Main.modules.values().forEach(m -> occurrences.addAll(m.getOccurrences()));

        occurrences.sort(new OccurrenceComparator());

        listModel.addAll(occurrences);


        JList<Occurrence> list = new JList<>(listModel);
        list.setCellRenderer(new OccurrenceRenderer());

        JScrollPane scrollPane = new JScrollPane(list);

        OccurrencePanel header = new OccurrencePanel();
        header.setHeaderMode();

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(header, c);

        c.gridy = 1;
        add(scrollPane, c);

        setBackground(new Color(180, 230, 230));
    }
}

class OccurrenceRenderer extends OccurrencePanel implements ListCellRenderer<Occurrence>{
    @Override
    public Component getListCellRendererComponent(JList<? extends Occurrence> list, Occurrence value, int index, boolean isSelected, boolean cellHasFocus) {
        setOccurrence(value);

        if(isSelected){
            setBorder(new BevelBorder(BevelBorder.LOWERED));
        } else {
            setBorder(new BevelBorder(BevelBorder.RAISED));
        }

        return this;
    }
}

class OccurrencePanel extends JPanel{
    Occurrence occurrence;

    JLabel moduleTitleLabel = new JLabel();
    JLabel occurrenceNumberLabel = new JLabel();
    JLabel activityLabel = new JLabel();
    JLabel timeLabel = new JLabel();
    JLabel tutorLabel = new JLabel();
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
        targetLabel.setText(Integer.toString(occurrence.getTargetStudents()));
        actualLabel.setText(Integer.toString(occurrence.getActualStudents()));

        setHeights(75);
    }

    void setHeaderMode(){
        moduleTitleLabel.setText("Module");
        occurrenceNumberLabel.setText("Occurrence");
        activityLabel.setText("Activity");
        timeLabel.setText("Time");
        tutorLabel.setText("Tutor");
        targetLabel.setText("Target");
        actualLabel.setText("Actual");

        setHeights(40);
        moduleTitleLabel.setPreferredSize(new Dimension(180, 40));

        Border border1 = BorderFactory.createCompoundBorder(new BevelBorder(BevelBorder.RAISED), new BevelBorder(BevelBorder.LOWERED));
        Border border = BorderFactory.createCompoundBorder(border1, new BevelBorder(BevelBorder.RAISED));
        setBorder(border);
    }

    void setHeights(int height){
        Dimension bigLabelSize = new Dimension(200, height);
        Dimension smallLabelSize = new Dimension(60, height);
        Dimension occurrenceLabelSize = new Dimension(90, height);
        Dimension activityLabelSize = new Dimension(75, height);

        moduleTitleLabel.setPreferredSize(bigLabelSize);
        occurrenceNumberLabel.setPreferredSize(occurrenceLabelSize);
        activityLabel.setPreferredSize(activityLabelSize);
        timeLabel.setPreferredSize(bigLabelSize);
        tutorLabel.setPreferredSize(bigLabelSize);
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
        add(targetLabel, c);

        c.gridx = 6;
        add(actualLabel, c);
    }
}