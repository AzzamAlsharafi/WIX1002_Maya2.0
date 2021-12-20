package maya.object;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

public class Occurrence {
    final String code;
    final String tutor;
    final String time; // TODO: change type later
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

    public List<String> getStudents() {
        return students;
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
