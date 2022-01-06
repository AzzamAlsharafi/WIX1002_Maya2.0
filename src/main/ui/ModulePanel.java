package main.ui;

import main.Main;
import main.object.Occurrence;
import main.util.OccurrenceComparator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// This is the super class for StudentModulePanel, and StaffModulePanel.
public abstract class ModulePanel extends JPanel {
    List<Occurrence> allOccurrences = new ArrayList<>();
    List<Occurrence> availableOccurrences = new ArrayList<>();

    abstract void redraw();

    // This is used to filter the list of occurrence based on the text in the search field.
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
