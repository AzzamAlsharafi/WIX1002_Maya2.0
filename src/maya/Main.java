package maya;

import maya.object.Account;
import maya.object.Module;
import maya.page.MainFrame;
import maya.util.DataManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;

    public static final int maxCreditsPerStudent = 22;

    public static Map<String, Account> accounts = new HashMap<>();
    public static Map<String, Module> modules = new HashMap<>();

    public static Account currentUser = null;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        DataManager.loadAccounts();
        DataManager.loadModules();
        DataManager.loadRememberMe();

        // The first point of the program. MainFrame is the main window of the program.
        MainFrame.getFrame().setVisible(true);
    }
}
