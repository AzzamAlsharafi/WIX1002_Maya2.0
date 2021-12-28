package maya.object;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Module {
    final String code;
    final String title;
    final String coordinator;
    final String restrictions;
    final int occurrencesCount;
    final int credit;
    final int activityType;
    final List<Occurrence> occurrences;

    public static final int MOD_TYPE_LECTURE = 0;
    public static final int MOD_TYPE_LECTURE_TUTORIAL = 1;
    public static final int MOD_TYPE_TUTORIAL = 2;

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public int getOccurrencesCount() {
        return occurrencesCount;
    }

    public int getCredit() {
        return credit;
    }

    public int getActivityType() {
        return activityType;
    }

    public List<Occurrence> getOccurrences() {
        return occurrences;
    }

    public String checkRestrictions(StudentAccount account){
        if(restrictions.isBlank()){
            return "";
        }

        String[] restrictionsArr = restrictions.split(" : ");

        if(Arrays.asList(restrictionsArr[0].split(", ")).contains(String.valueOf(account.programme)) || restrictionsArr[0].equals("-1")){
            if(Arrays.asList(restrictionsArr[1].split(", ")).contains(String.valueOf(account.englishBand)) || restrictionsArr[1].equals("-1")) {
                return "";
            }
        }

        return String.format("You are not eligible to register for %s.", code);
    }

    public boolean containsOccurrence(int occurrenceNumber, int activity){
        for (Occurrence occ: occurrences) {
            if(occ.getOccurrenceNumber() == occurrenceNumber && occ.getActivityType() == activity){
                return true;
            }
        }
        return false;
    }

    public void storeModule(ObjectOutputStream out) throws IOException {
        out.writeUTF(code);
        out.writeUTF(title);
        out.writeUTF(coordinator);
        out.writeUTF(restrictions);
        out.writeInt(occurrencesCount);
        out.writeInt(credit);
        out.writeInt(activityType);

        out.writeInt(occurrences.size());
        for (Occurrence occ: occurrences) {
            occ.storeOccurrence(out);
        }
    }

    public static Module loadModule(ObjectInputStream in) throws IOException {
        String code = in.readUTF();
        String title = in.readUTF();
        String coordinator = in.readUTF();
        String restriction = in.readUTF();
        int occurrencesCount = in.readInt();
        int credit = in.readInt();
        int activityType = in.readInt();

        List<Occurrence> occurrences = new ArrayList<>();
        int occurrencesListSize = in.readInt();
        for (int i = 0; i < occurrencesListSize; i++) {
            occurrences.add(Occurrence.loadOccurrence(in));
        }

        return new Module(code, title, coordinator, restriction, occurrencesCount, credit, activityType, occurrences);
    }

    public Module(String code, String title, String coordinator, String restrictions, int occurrencesCount, int credit, int activityType, List<Occurrence> occurrences) {
        this.code = code;
        this.title = title;
        this.coordinator = coordinator;
        this.restrictions = restrictions;
        this.occurrencesCount = occurrencesCount;
        this.credit = credit;
        this.activityType = activityType;
        this.occurrences = occurrences;
    }
}
