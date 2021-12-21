package maya.page;

import maya.Main;
import maya.object.Module;
import maya.object.Occurrence;

import java.util.Comparator;

public class SearchPage {
    public static void run(){
        System.out.print("1. View all modules\n2. Search specific module\n3. Return\nEnter your choice: ");

        switch (Main.scanner.nextInt()){
            case 1 -> viewCourses("");
            case 2 -> searchModule();
            case 3 -> WelcomePage.run();
        }
    }

    private static void viewCourses(String codeToSearch){
        System.out.printf("%-7s   %-30s %-5s %-10s %-30s %-40s %-5s %-5s\n",
                "Module", "", "Occ", "Activity", "Time", "Tutor", "Target", "Actual");

        for (Module module: Main.modules.values().stream().filter(m -> m.getCode().contains(codeToSearch)).toList()) {
            module.getOccurrences().sort(new OccurrenceComparator());
            for(Occurrence occ: module.getOccurrences()){
                System.out.printf("%-7s - %-30s %-5d %-10s %-30s %-40s %-5d %-5d\n",
                        module.getCode(), module.getTitle(), occ.getOccurrenceNumber(),
                        occ.getActivityString(), occ.getTime(), occ.getTutor(),
                        occ.getTargetStudents(), occ.getActualStudents());
            }
        }
    }

    private static void searchModule(){
        Main.scanner.nextLine();
        System.out.print("Enter course code: ");

        String codeToSearch = Main.scanner.nextLine();

        viewCourses(codeToSearch);
    }
}

class OccurrenceComparator implements Comparator<Occurrence> {

    @Override
    public int compare(Occurrence o1, Occurrence o2) {
        if(o1.getOccurrenceNumber() == o2.getOccurrenceNumber()){
            return o1.getActivityType() < o2.getActivityType() ? -1 : 1;
        } else {
            return o1.getOccurrenceNumber() < o2.getOccurrenceNumber() ? -1 : 1;
        }
    }
}