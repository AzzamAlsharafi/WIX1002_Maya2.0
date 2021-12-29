package maya.util;

import maya.Main;
import maya.object.Module;
import maya.object.Occurrence;
import maya.object.StudentAccount;

import java.util.Comparator;

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
