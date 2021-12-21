package maya;

import maya.object.Account;
import maya.object.Module;
import maya.page.LoginConsolePage;
import maya.page.MainFrame;
import maya.page.WelcomeConsolePage;
import maya.util.DataManager;

import java.util.*;

public class Main {
    public static Scanner scanner;

    public static Map<String, Account> accounts = new HashMap<>();
    public static Map<String, Module> modules = new HashMap<>();

    public static Account currentUser = null;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        DataManager.loadAccounts();
        DataManager.loadModules();

        if(accounts.containsKey("S1234567")){
            currentUser = accounts.get("S1234567");
        }

        new MainFrame().setVisible(true);

        if(currentUser == null){
            LoginConsolePage.run();
        }

        if(currentUser != null){
            WelcomeConsolePage.run();
        }
    }
}
