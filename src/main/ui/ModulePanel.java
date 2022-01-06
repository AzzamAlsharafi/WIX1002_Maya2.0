package main.ui;

import main.Main;
import main.object.Occurrence;
import main.util.OccurrenceComparator;
import me.xdrop.fuzzywuzzy.FuzzySearch;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// This is the super class for StudentModulePanel, and StaffModulePanel.
public abstract class ModulePanel extends JPanel {
    List<Occurrence> allOccurrences = new ArrayList<>();
    List<Occurrence> availableOccurrences = new ArrayList<>();

    abstract void redraw();

    // This is used to filter the list of occurrence based on the text in the search field.
    // This method implements fuzzy search only for titles of modules and not codes.
    void filter(String toFilter){
        String codeSearch = "";
        String restSearch = "";

        if(toFilter.contains("-")){
            codeSearch = toFilter.substring(0, toFilter.indexOf("-")).strip();
            restSearch = toFilter.substring(toFilter.indexOf("-") + 1).strip();
        } else {
            restSearch = toFilter.strip();
        }

        for (Occurrence occ: allOccurrences) {
            String code = occ.getCode();
            String title = Main.modules.get(occ.getCode()).getTitle();

            boolean titleResult = FuzzySearch.partialRatio(restSearch.toLowerCase(), title.toLowerCase()) > 70;

            boolean result;
            if(codeSearch.isBlank()){
                result = code.toLowerCase().contains(restSearch.toLowerCase()) || titleResult;
            } else {
                result = code.toLowerCase().contains(codeSearch.toLowerCase()) && (titleResult || restSearch.isBlank());
            }

            if(result){
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
