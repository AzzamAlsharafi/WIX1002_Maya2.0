package maya.util;

import maya.Main;
import maya.object.Occurrence;
import maya.object.StaffAccount;
import maya.object.StudentAccount;

import java.util.Comparator;

// This class is used to sort a list of Occurrence objects.
// It's used in StudentModulePanel and StaffModulePanel where it's needed to sort a list of occurrences.
public class OccurrenceComparator implements Comparator<Occurrence> {

    @Override
    public int compare(Occurrence o1, Occurrence o2) {
        if(Main.currentUser instanceof StudentAccount){
            boolean isAllowed1 = Main.modules.get(o1.getCode()).checkRestrictions((StudentAccount) Main.currentUser).isBlank();
            boolean isAllowed2 = Main.modules.get(o2.getCode()).checkRestrictions((StudentAccount) Main.currentUser).isBlank();

            if(isAllowed1 & !isAllowed2){
                return -1;
            }else if(isAllowed2 & !isAllowed1){
                return 1;
            }
        }else if(Main.currentUser instanceof StaffAccount){
            boolean isRegistered1 = Main.modules.get(o1.getCode()).getCoordinator().equalsIgnoreCase(Main.currentUser.getFullName())
                    || Main.currentUser.getOccurrences().contains(String.format("%s_%s_%s", o1.getCode(), o1.getOccurrenceNumber(), o1.getActivityString()));
            boolean isRegistered2 = Main.modules.get(o2.getCode()).getCoordinator().equalsIgnoreCase(Main.currentUser.getFullName())
                    || Main.currentUser.getOccurrences().contains(String.format("%s_%s_%s", o2.getCode(), o2.getOccurrenceNumber(), o2.getActivityString()));

            if(isRegistered1 & !isRegistered2){
                return -1;
            }else if(isRegistered2 & !isRegistered1){
                return 1;
            }
        }

        if (o1.getCode().compareTo(o2.getCode()) == 0) {
            if (o1.getOccurrenceNumber() == o2.getOccurrenceNumber()) {
                return Integer.compare(o1.getActivityType(), o2.getActivityType());
            } else {
                return Integer.compare(o1.getOccurrenceNumber(), o2.getOccurrenceNumber());
            }
        } else {
            return o1.getCode().compareTo(o2.getCode());
        }
    }
}
