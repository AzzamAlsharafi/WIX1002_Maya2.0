package maya.util;

import maya.Main;
import maya.object.Account;
import maya.object.Module;

import java.io.*;

public class DataManager {

    static final String ACCOUNTS_FILE = "accounts";
    static final String MODULES_FILE = "modules";

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

    public static void storeModules(){
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(MODULES_FILE));

            out.writeInt(Main.modules.size());
            for (Module module: Main.modules.values()) {
                module.storeModule(out);
            }

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadModules(){
        try {
            File modulesFile = new File(MODULES_FILE);

            if(modulesFile.exists()){
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(MODULES_FILE));

                int modulesNumber = in.readInt();
                for(int i = 0; i < modulesNumber; i++){
                    Module module = Module.loadModule(in);
                    Main.modules.put(module.getCode(), module);
                }

                in.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
