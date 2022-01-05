package main.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.Main;
import main.object.Account;
import main.object.Module;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;

// This class is responsible for storing and loading the data of modules and accounts in files,
// so they are not lost when the program is rebooted.
public class DataManager {

    static final String ACCOUNTS_FILE = "data/accounts";
    static final String MODULES_FILE = "data/modules";
    static final String REMEMBER_ME_FILE = "data/rememberMe";

    static final String JSON_ACCOUNTS_FILE = "data/accounts.json";
    static final String JSON_MODULES_FILE = "data/modules.json";
    static final String JSON_REMEMBER_ME_FILE = "data/rememberMe.json";

    static final String DATA_DIRECTORY = "data";

    public static void storeAccountsJSON(){
        try {
            System.out.print(new File(DATA_DIRECTORY).mkdir());

            FileOutputStream out = new FileOutputStream(JSON_ACCOUNTS_FILE);

            Gson gson = new Gson();

            out.write(gson.toJson(Main.accounts).getBytes());

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadAccountsJSON(){
        try {
            File accountsFile = new File(JSON_ACCOUNTS_FILE);

            if(accountsFile.exists()){
                FileInputStream in = new FileInputStream(JSON_ACCOUNTS_FILE);

                Gson gson = new Gson();

                Type type = new TypeToken<HashMap<String, Account>>(){}.getType();
                Main.accounts = gson.fromJson(new String(in.readAllBytes()), type);

                in.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void storeModulesJSON(){
        try {
            System.out.print(new File(DATA_DIRECTORY).mkdir());

            FileOutputStream out = new FileOutputStream(JSON_MODULES_FILE);

            Gson gson = new Gson();

            out.write(gson.toJson(Main.modules).getBytes());

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadModulesJSON(){
        try {
            File modulesFile = new File(JSON_MODULES_FILE);

            if(modulesFile.exists()){
                FileInputStream in = new FileInputStream(JSON_MODULES_FILE);

                Gson gson = new Gson();

                Type type = new TypeToken<HashMap<String, Module>>(){}.getType();
                Main.modules = gson.fromJson(new String(in.readAllBytes()), type);

                in.close();
            } else  {
                ModulesAdder.addAll();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateRememberMeJSON(boolean rememberMe){
        try {
            System.out.print(new File(DATA_DIRECTORY).mkdir());

            FileOutputStream out = new FileOutputStream(JSON_REMEMBER_ME_FILE);

            Gson gson = new Gson();

            String[] rememberMeData = new String[]{String.valueOf(rememberMe), rememberMe ? Main.currentUser.getUsername() : ""};

            out.write(gson.toJson(rememberMeData).getBytes());

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadRememberMeJSON(){
        try {
            File accountsFile = new File(JSON_REMEMBER_ME_FILE);

            if(accountsFile.exists()){
                FileInputStream in = new FileInputStream(JSON_REMEMBER_ME_FILE);

                Gson gson = new Gson();

                Type type = new TypeToken<String[]>(){}.getType();
                String[] data = gson.fromJson(new String(in.readAllBytes()), type);

                if(data[0].equals(String.valueOf(true))){
                    Main.currentUser = Main.accounts.get(data[1]);
                }

                in.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
