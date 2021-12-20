package maya.util;

import maya.Main;
import maya.object.Account;

import java.io.*;

public class DataManager {

    static final String ACCOUNTS_FILE = "accounts";

    public static void storeAccounts(){
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ACCOUNTS_FILE));

            out.writeInt(Main.accounts.size());
            for (Account acc: Main.accounts.values()) {
                acc.storeAccount(out);
            }

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadAccounts(){
        try {
            File accountsFile = new File(ACCOUNTS_FILE);

            if(accountsFile.exists()){
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(ACCOUNTS_FILE));

                int accountsNumber = in.readInt();
                for(int i = 0; i < accountsNumber; i++){
                    Account acc = Account.loadAccount(in);
                    Main.accounts.put(acc.getUsername(), acc);
                }

                in.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
