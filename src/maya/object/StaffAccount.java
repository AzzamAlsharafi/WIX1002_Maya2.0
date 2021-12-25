package maya.object;

import maya.Main;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class StaffAccount extends Account{
    public StaffAccount(String umMail, String username, String password, String fullName, ArrayList<String> occurrences) {
        super(umMail, username, password, fullName, occurrences);
    }

    public static ArrayList<String> getOccurrencesByName(String fullName){
        List<Occurrence> allOccurrences = new ArrayList<>();
        Main.modules.values().forEach(m -> allOccurrences.addAll(m.getOccurrences()));

        ArrayList<String> registeredOccurrences = new ArrayList<>();
        for (Occurrence occ: allOccurrences) {
            if(occ.getTutor().equalsIgnoreCase(fullName)){
                registeredOccurrences.add(String.format("%s_%s_%s", occ.getCode(), occ.getOccurrenceNumber(), occ.getActivityString()));
            }
        }

        return registeredOccurrences;
    }

    @Override
    public void storeAccount(ObjectOutputStream out) throws IOException {
        out.writeUTF("Staff");
        super.storeAccount(out);
    }
}
