package maya.util;

import maya.object.Occurrence;

import java.util.Comparator;

public class OccurrenceComparator implements Comparator<Occurrence> {

    @Override
    public int compare(Occurrence o1, Occurrence o2) {
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
