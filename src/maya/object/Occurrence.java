package maya.object;

import maya.Main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Occurrence {
    final String code;
    final String tutor;
    final String time;
    final int targetStudents;
    final int actualStudents;
    final int occurrenceNumber;
    final int activityType;
    final List<String> students;

    public static final int OCC_TYPE_LECTURE = 0;
    public static final int OCC_TYPE_TUTORIAL = 1;

    public String getCode() {
        return code;
    }

    public String getTutor() {
        return tutor;
    }

    public String getTime() {
        return time;
    }

    public int getTargetStudents() {
        return targetStudents;
    }

    public int getActualStudents() {
        return actualStudents;
    }

    public int getOccurrenceNumber() {
        return occurrenceNumber;
    }

    public int getActivityType() {
        return activityType;
    }

    public String getActivityString(){
        switch (activityType){
            case OCC_TYPE_LECTURE -> {return "Lecture";}
            case OCC_TYPE_TUTORIAL -> {return "Tutorial";}
            default -> {return "Default";}
        }
    }

    static public int getActivityFromString(String string){
        switch (string){
            case "Lecture" -> {return OCC_TYPE_LECTURE;}
            case "Tutorial" -> {return OCC_TYPE_TUTORIAL;}
        }
        return -1;
    }

    public List<String> getStudents() {
        return students;
    }

    public static boolean checkOverlapOfCodeAndOcc(String codeAndOcc1, String codeAndOcc2){
        if(!codeAndOcc1.isBlank() && !codeAndOcc2.isBlank()){
            List<Occurrence> occ1 = getOccurrencesFromCodeAndOcc(codeAndOcc1);
            List<Occurrence> occ2 = getOccurrencesFromCodeAndOcc(codeAndOcc2);

            for (Occurrence o1: occ1) {
                for (Occurrence o2: occ2) {
                    if(checkOverlapOfOccurrence(o1, o2)){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean checkOverlapOfOccurrence(Occurrence occ1, Occurrence occ2){
        SimpleDateFormat format = new SimpleDateFormat("E h:m a");

        try {
            String[] time1 = occ1.getTime().split(" ");
            Date start1 = format.parse(String.format("%s %s %s", time1[0], time1[1], time1[2]));
            Date end1 = format.parse(String.format("%s %s %s", time1[0], time1[4], time1[5]));

            String[] time2 = occ2.getTime().split(" ");
            Date start2 = format.parse(String.format("%s %s %s", time2[0], time2[1], time2[2]));
            Date end2 = format.parse(String.format("%s %s %s", time2[0], time2[4], time2[5]));

            return start1.before(end2) && start2.before(end1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static List<Occurrence> getOccurrencesFromCodeAndOcc(String occ){
        String[] codeAndOcc = occ.split("_");

        if(codeAndOcc.length == 2) {
            return Main.modules.get(codeAndOcc[0]).getOccurrences().stream().filter(o -> o.getOccurrenceNumber() == Integer.parseInt(codeAndOcc[1])).toList();
        } else {
            return Main.modules.get(codeAndOcc[0]).getOccurrences().stream().filter(o -> o.getOccurrenceNumber() == Integer.parseInt(codeAndOcc[1]) && o.getActivityString().equals(codeAndOcc[2])).toList();
        }
    }

    public void storeOccurrence(ObjectOutputStream out) throws IOException {
        out.writeUTF(code);
        out.writeUTF(tutor);
        out.writeUTF(time);
        out.writeInt(targetStudents);
        out.writeInt(actualStudents);
        out.writeInt(occurrenceNumber);
        out.writeInt(activityType);
        out.writeUTF(students.toString());
    }

    public static Occurrence loadOccurrence(ObjectInputStream in) throws IOException{
        return new Occurrence(in.readUTF(), in.readUTF(), in.readUTF(), in.readInt(), in.readInt(), in.readInt(), in.readInt(),
            Arrays.stream(in.readUTF().replace("[", "").replace("]", "").split(", ")).toList());
    }

    public Occurrence(String code, String tutor, String time, int targetStudents, int actualStudents, int occurrenceNumber, int activityType, List<String> students) {
        this.code = code;
        this.tutor = tutor;
        this.time = time;
        this.targetStudents = targetStudents;
        this.actualStudents = actualStudents;
        this.occurrenceNumber = occurrenceNumber;
        this.activityType = activityType;
        this.students = students;
    }
}
