package maya.page;

import maya.Main;
import maya.object.Module;
import maya.object.Occurrence;
import maya.util.DataManager;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

class TotalCreditsPanel extends JPanel{
    JPanel header = new JPanel();
    JLabel maxHeaderLabel = new JLabel("Max Credits");
    JLabel currentHeaderLabel = new JLabel("Current");

    JPanel body = new JPanel();
    JLabel maxBodyLabel = new JLabel(Integer.toString(Main.maxCreditsPerStudent));
    JLabel currentBodyLabel = new JLabel();

    TotalCreditsPanel(int currentCredits){
        currentBodyLabel.setText(Integer.toString(currentCredits));

        header.add(maxHeaderLabel);
        header.add(currentHeaderLabel);
        Border border1 = BorderFactory.createCompoundBorder(new BevelBorder(BevelBorder.RAISED), new BevelBorder(BevelBorder.LOWERED));
        Border border = BorderFactory.createCompoundBorder(border1, new BevelBorder(BevelBorder.RAISED));
        header.setBorder(border);
        Color background = new Color(5, 49, 121);
        header.setBackground(background);
        maxHeaderLabel.setForeground(Color.WHITE);
        maxHeaderLabel.setBorder(new EmptyBorder(0, 0, 0, 50));
        currentHeaderLabel.setForeground(Color.WHITE);

        body.add(maxBodyLabel);
        body.add(currentBodyLabel);
        body.setLayout(new FlowLayout(FlowLayout.LEFT));
        body.setBorder(new BevelBorder(BevelBorder.RAISED));
        maxBodyLabel.setBorder(new EmptyBorder(0, 12, 0, 35));
        currentBodyLabel.setBorder(new EmptyBorder(0, 70, 0, 0));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(header);
        add(body);
    }
}

class RegisteredOccurrencePanel extends JPanel{
    JLabel moduleTitleLabel = new JLabel();
    JLabel occurrenceNumberLabel = new JLabel();
    JLabel creditsLabel = new JLabel();

    RegisteredOccurrencePanel thisPanel = this;

    RegisteredOccurrencePanel(String occ, StudentModulePanel parent){
        String[] codeAndOcc = occ.split("_");
        Module module = Main.modules.get(codeAndOcc[0]);

        moduleTitleLabel.setText(codeAndOcc[0]);
        occurrenceNumberLabel.setText(codeAndOcc[1]);
        creditsLabel.setText(Integer.toString(module.getCredit()));

        moduleTitleLabel.setToolTipText(module.getTitle());

        moduleTitleLabel.setBorder(new EmptyBorder(0, 0, 0, 27));
        occurrenceNumberLabel.setBorder(new EmptyBorder(0, 0, 0, 38));
        creditsLabel.setBorder(new EmptyBorder(0, 0, 0, 35));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setBorder(new BevelBorder(BevelBorder.LOWERED));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBorder(new BevelBorder(BevelBorder.RAISED));
                String title = "Remove Module";
                String message = String.format("Do you want to remove %s?", codeAndOcc[0]);
                int answer = JOptionPane.showConfirmDialog(thisPanel, message, title, JOptionPane.YES_NO_OPTION);
                if(answer == JOptionPane.YES_OPTION){
                    Main.currentUser.getOccurrences().remove(occ);
                    parent.redraw();
                    DataManager.storeAccounts();
                }
            }
        });

        Dimension size = new Dimension(200, 50);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);

        setBorder(new BevelBorder(BevelBorder.RAISED));

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        add(moduleTitleLabel, c);

        c.gridx = 1;
        add(occurrenceNumberLabel, c);

        c.gridx = 2;
        add(creditsLabel, c);
    }
}

class RegisteredOccurrenceHeaderPanel extends JPanel{
    JLabel moduleTitleLabel = new JLabel();
    JLabel occurrenceNumberLabel = new JLabel();
    JLabel creditsLabel = new JLabel();

    RegisteredOccurrenceHeaderPanel(){
        moduleTitleLabel.setText("Module");
        occurrenceNumberLabel.setText("Occ");
        creditsLabel.setText("Credits");

        moduleTitleLabel.setBorder(new EmptyBorder(0, 10, 0, 30));
        occurrenceNumberLabel.setBorder(new EmptyBorder(0, 0, 0, 20));
        creditsLabel.setBorder(new EmptyBorder(0, 0, 0, 15));

        Color fontColor = Color.WHITE;
        moduleTitleLabel.setForeground(fontColor);
        occurrenceNumberLabel.setForeground(fontColor);
        creditsLabel.setForeground(fontColor);

        Dimension size = new Dimension(200, 39);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);

        Border border1 = BorderFactory.createCompoundBorder(new BevelBorder(BevelBorder.RAISED), new BevelBorder(BevelBorder.LOWERED));
        Border border = BorderFactory.createCompoundBorder(border1, new BevelBorder(BevelBorder.RAISED));
        setBorder(border);

        Color background = new Color(5, 49, 121);
        setBackground(background);

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        add(moduleTitleLabel, c);

        c.gridx = 1;
        add(occurrenceNumberLabel, c);

        c.gridx = 2;
        add(creditsLabel, c);
    }
}

class OccurrencePanel extends JPanel{
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

    void setOccurrence(Occurrence occurrence, StudentModulePanel parent){
        this.occurrence = occurrence;
        String moduleOcc = String.format("%s_%s", occurrence.getCode(), occurrence.getOccurrenceNumber());
        boolean isRegistered = Main.currentUser.getOccurrences().contains(moduleOcc);

        Module module = Main.modules.get(occurrence.getCode());

        moduleTitleLabel.setText(String.format("<html>%s - %s</html>", module.getCode(), module.getTitle()));
        occurrenceNumberLabel.setText(Integer.toString(occurrence.getOccurrenceNumber()));
        activityLabel.setText(occurrence.getActivityString());
        timeLabel.setText(occurrence.getTime());
        tutorLabel.setText(String.format("<html>%s</html>", occurrence.getTutor()));
        creditsLabel.setText(Integer.toString(module.getCredit()));
        targetLabel.setText(Integer.toString(occurrence.getTargetStudents()));
        actualLabel.setText(Integer.toString(occurrence.getActualStudents()));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(!isRegistered){
                    setBorder(new BevelBorder(BevelBorder.LOWERED));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(!isRegistered){
                    setBorder(new BevelBorder(BevelBorder.RAISED));

                    String title = "Register Module";

                    // Check if the same module is already registered but in a different occurrence.
                    for (String module: Main.currentUser.getOccurrences()) {
                        if(module.contains(occurrence.getCode())){
                            String message = String.format("You already registered %s in another occurrence,\ndo you want to replace it?", occurrence.getCode());
                            int answer = JOptionPane.showConfirmDialog(thisPanel, message, title, JOptionPane.YES_NO_OPTION);
                            if(answer == JOptionPane.YES_OPTION){
                                Main.currentUser.getOccurrences().remove(module);
                                Main.currentUser.getOccurrences().add(moduleOcc);
                                parent.redraw();
                                DataManager.storeAccounts();
                            }
                            return;
                        }
                    }

                    String message = String.format("Do you want to register %s?", occurrence.getCode());
                    int answer = JOptionPane.showConfirmDialog(thisPanel, message, title, JOptionPane.YES_NO_OPTION);
                    if(answer == JOptionPane.YES_OPTION){
                        Main.currentUser.getOccurrences().add(moduleOcc);
                        parent.redraw();
                        DataManager.storeAccounts();
                    }
                }
            }

        });

        Dimension size = new Dimension(982, 80);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);

        if(isRegistered){
            setBackground(new Color(217, 237, 247));
            setBorder(new BevelBorder(BevelBorder.LOWERED));
        } else {
            setBorder(new BevelBorder(BevelBorder.RAISED));
        }

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