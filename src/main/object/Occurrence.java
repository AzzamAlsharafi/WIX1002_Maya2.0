package main.object;

import main.Main;

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
    final int occurrenceNumber;
    final int activityType;

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

    // Returns the number of students who have registered this occurrence
    public int getActualStudents() {
        int actual = 0;

        for (Account acc: Main.accounts.values()) {
            if(acc instanceof StudentAccount){
                String codeAndOcc = String.format("%s_%s", code, occurrenceNumber);
                if(acc.getOccurrences().contains(codeAndOcc)){
                    actual++;
                }
            }
        }

        return actual;
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

    // Returns length of the occurrence in hours.
    public int getHours(){
        SimpleDateFormat format = new SimpleDateFormat("E h:m a");

        try {
            String[] timeS = time.split(" ");
            Date start = format.parse(String.format("%s %s %s", timeS[0], timeS[1], timeS[2]));
            Date end = format.parse(String.format("%s %s %s", timeS[0], timeS[4], timeS[5]));

            return (int) (end.getTime() - start.getTime()) / 3600000;
        } catch (ParseException e){
            e.printStackTrace();
        }

        return 0;
    }

    static public int getActivityFromString(String string){
        switch (string){
            case "Lecture" -> {return OCC_TYPE_LECTURE;}
            case "Tutorial" -> {return OCC_TYPE_TUTORIAL;}
        }
        return -1;
    }

    // Checks if a string is a valid time format e.g. "Monday 9:00 AM - 10:00 AM"
    public static boolean isValidTime(String timeString){
        SimpleDateFormat format = new SimpleDateFormat("E h:m a");

        try {
            String[] timeArray = timeString.split(" ");
            if(timeArray.length > 5){
                System.out.println(format.parse(String.format("%s %s %s", timeArray[0], timeArray[1], timeArray[2])));
                System.out.println(format.parse(String.format("%s %s %s", timeArray[0], timeArray[4], timeArray[5])));
            } else {
                return false;
            }

            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // Check if two occurrences overlap in time. This method doesn't work on Occurrence objects,
    // rather it works on Strings e.g. "WIX1002_1", this means occurrence 1 of module WIX1002.
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

    // Check if two occurrences overlap in time. This method works on Occurrence objects.
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

    // Returns a list of Occurrence objects from String e.g. "WIX1002_1",
    // this will return all Occurrence objects that are from module WIX1002 and are part of occurrence 1.
    // This method exists because the Lecture class and the Tutorial class of the same occurrence are stored
    // in two separate Occurrence objects.
    public static List<Occurrence> getOccurrencesFromCodeAndOcc(String occ){
        String[] codeAndOcc = occ.split("_");

        if(codeAndOcc.length == 2) {
            return Main.modules.get(codeAndOcc[0]).getOccurrences().stream().filter(o -> o.getOccurrenceNumber() == Integer.parseInt(codeAndOcc[1])).toList();
        } else {
            return Main.modules.get(codeAndOcc[0]).getOccurrences().stream().filter(o -> o.getOccurrenceNumber() == Integer.parseInt(codeAndOcc[1]) && o.getActivityString().equals(codeAndOcc[2])).toList();
        }
    }

    // Returns a list of Occurrence objects from String e.g. "WIX1002 L\n1, 2, 3, 4",
    // This will return all Occurrence objects from WIX1002, that are Lectures and are from
    // occurrences 1, 2, 3 and 4.
    // This is used from TimetablePanel.
    public static List<Occurrence> getOccurrencesFromUnderEdit(String underEdit){
        String code = underEdit.split(" ")[0];
        char type = underEdit.split("\n")[0].split(" ")[1].charAt(0);
        List<Integer> occurrences = Arrays.stream(underEdit.split("\n")[1].split(", ")).map(Integer::parseInt).toList();

        return Main.modules.get(code).getOccurrences().stream().filter(o -> occurrences.contains(o.getOccurrenceNumber()) && o.getActivityString().charAt(0) == type).toList();
    }

    public void storeOccurrence(ObjectOutputStream out) throws IOException {
        out.writeUTF(code);
        out.writeUTF(tutor);
        out.writeUTF(time);
        out.writeInt(targetStudents);
        out.writeInt(occurrenceNumber);
        out.writeInt(activityType);
    }

    public static Occurrence loadOccurrence(ObjectInputStream in) throws IOException{
        return new Occurrence(in.readUTF(), in.readUTF(), in.readUTF(), in.readInt(), in.readInt(), in.readInt());
    }

    public Occurrence(String code, String tutor, String time, int targetStudents, int occurrenceNumber, int activityType) {
        this.code = code;
        this.tutor = tutor;
        this.time = time;
        this.targetStudents = targetStudents;
        this.occurrenceNumber = occurrenceNumber;
        this.activityType = activityType;
    }
}
