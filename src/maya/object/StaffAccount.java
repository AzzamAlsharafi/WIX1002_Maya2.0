package maya.object;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class StaffAccount extends Account{
    public StaffAccount(String umMail, String username, String password, String fullName, ArrayList<String> occurrences) {
        super(umMail, username, password, fullName, occurrences);
    }

    @Override
    public void storeAccount(ObjectOutputStream out) throws IOException {
        out.writeUTF("Staff");
        super.storeAccount(out);
    }
}
