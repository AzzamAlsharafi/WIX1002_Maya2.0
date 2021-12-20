package maya.object;

import java.util.List;

public class Account {
    final String email;
    final String username;
    final String password;
    final String fullName;
    final List<String> occurrences;

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public List<String> getOccurrences() {
        return occurrences;
    }

    public Account(String email, String username, String password, String fullName, List<String> occurrences) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.occurrences = occurrences;
    }
}
