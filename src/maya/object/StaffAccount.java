package maya.object;

import java.util.List;

public class StaffAccount extends Account{
    public StaffAccount(String umMail, String username, String password, String fullName, List<String> occurrences) {
        super(umMail, username, password, fullName, occurrences);
    }
}
