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

    // This method returns a list of registered occurrences under this StaffAccount,
    // because unlike StudentAccount, the occurrences of each staff aren't stored in the StaffAccount object.
    @Override
    public List<String> getOccurrences() {
        List<Occurrence> allOccurrences = new ArrayList<>();
        Main.modules.values().forEach(m -> allOccurrences.addAll(m.getOccurrences()));

        ArrayList<String> registeredOccurrences = new ArrayList<>();
        for (Occurrence occ: allOccurrences) {
            if(occ.getTutor().equalsIgnoreCase(fullName)){
                registeredOccurrences.add(String.format("%s_%s_%s", occ.getCode(), occ.getOccurrenceNumber(), occ.getActivityString()));
            }
        }

        registeredOccurrences.sort(String::compareTo);

        return registeredOccurrences;
    }

    @Override
    public void storeAccount(ObjectOutputStream out) throws IOException {
        out.writeUTF("Staff");
        super.storeAccount(out);
    }
}
