package main.ui;

import main.Main;
import main.object.Occurrence;
import main.util.OccurrenceComparator;
import me.xdrop.fuzzywuzzy.FuzzySearch;

import javax.swing.*;
import java.util.*;

// This is the super class for StudentModulePanel, and StaffModulePanel.
public abstract class ModulePanel extends JPanel {
    List<Occurrence> allOccurrences = new ArrayList<>();
    List<Occurrence> availableOccurrences = new ArrayList<>();

    abstract void redraw();

    // This is used to filter the list of occurrence based on the text in the search field.
    // This method implements fuzzy search only for titles of modules and not codes.
    void filter(String toFilter){
        Map<Occurrence, Integer> ratios = new HashMap<>();

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
            String title = Main.modules.get(occ.getCode()).getTitle().concat(" ").concat(occ.getTutor());

            int ratio = FuzzySearch.partialRatio(restSearch.toLowerCase(), title.toLowerCase());
            boolean titleResult = ratio > 70;

            boolean result;
            if(codeSearch.isBlank()){
                result = code.toLowerCase().contains(restSearch.toLowerCase()) || titleResult;
            } else {
                result = code.toLowerCase().contains(codeSearch.toLowerCase()) && (titleResult || restSearch.isBlank());
            }

            if(result) {
                ratios.put(occ, 100 - ratio);
            }
        }

        List<Map.Entry<Occurrence, Integer>> entries = new ArrayList<>(ratios.entrySet());
        entries.sort(Map.Entry.comparingByKey(new OccurrenceComparator()));
        entries.sort(Map.Entry.comparingByValue());
        
        availableOccurrences.clear();

        for (Map.Entry<Occurrence, Integer> occEntry: entries) {
            availableOccurrences.add(occEntry.getKey());
        }

        redraw();
    }
}
