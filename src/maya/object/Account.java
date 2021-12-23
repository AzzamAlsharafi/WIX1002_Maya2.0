package maya.object;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Account {
    final String email;
    final String username;
    final String password;
    final String fullName;
    final ArrayList<String> occurrences;

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

    public void storeAccount(ObjectOutputStream out) throws IOException{
        out.writeUTF(email);
        out.writeUTF(username);
        out.writeUTF(password);
        out.writeUTF(fullName);
        out.writeUTF(occurrences.toString());
    }

    public static Account loadAccount(ObjectInputStream in) throws IOException {
        if(in.readUTF().equals("Student")){
            return new StudentAccount(in.readUTF(), in.readUTF(), in.readUTF(), in.readUTF(),
                    new ArrayList<>(Arrays.stream(in.readUTF().replace("[", "").replace("]", "").split(", ")).toList()),
                    in.readInt(), in.readInt(), in.readInt());
        } else {
            return new StaffAccount(in.readUTF(), in.readUTF(), in.readUTF(), in.readUTF(),
                    new ArrayList<>(Arrays.stream(in.readUTF().replace("[", "").replace("]", "").split(", ")).toList()));
        }
    }

    public Account(String email, String username, String password, String fullName, ArrayList<String> occurrences) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.occurrences = occurrences;
    }
}
