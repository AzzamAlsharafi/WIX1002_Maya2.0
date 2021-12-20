package maya;

import maya.object.Account;
import maya.page.LoginPage;
import maya.util.DataManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static Scanner scanner;

    public static Map<String, Account> accounts = new HashMap<>();

    public static Account currentUser;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        DataManager.loadAccounts();
    }
}
