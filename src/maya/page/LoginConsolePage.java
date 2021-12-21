package maya.page;

import maya.Main;
import maya.object.Account;
import maya.object.StaffAccount;
import maya.object.StudentAccount;
import maya.util.DataManager;

import java.util.ArrayList;
import java.util.List;

public class LoginConsolePage {
    public static void run(){
        System.out.print("1. Log in\n2. Sign in\n3. Exit\nEnter your choice: ");

        switch (Main.scanner.nextInt()) {
            case 1 -> login();
            case 2 -> signin();
            case 3 -> System.exit(0);
            default -> {
                System.out.println("Invalid choice. Try again.");
                run();
            }
        }
    }

    private static void login(){
        Main.scanner.nextLine();
        System.out.print("Enter username/matric number: ");
        String username = Main.scanner.nextLine();
        System.out.print("Enter password: ");
        String password = Main.scanner.nextLine();

        if (Main.accounts.containsKey(username)) {
            if (Main.accounts.get(username).getPassword().equals(password)) {
                Main.currentUser = Main.accounts.get(username);
                return;
            }
        }

        System.out.println("This username doesn't exist or the password is incorrect. Try again.");
        login();
    }

    private static void signin(){
        System.out.print("1. New student account\n2. New staff account\nEnter your choice: ");
        switch (Main.scanner.nextInt()) {
            case 1 -> {
                Main.scanner.nextLine();
                System.out.print("Enter SiswaMail address: ");
                String siswaMail = Main.scanner.nextLine();
                System.out.print("Enter Matric Number: ");
                String matricNumber = Main.scanner.nextLine();
                System.out.print("Enter Password: ");
                String password = Main.scanner.nextLine();
                System.out.print("Enter Full Name: ");
                String fullName = Main.scanner.nextLine();
                System.out.print("Enter Programme: ");
                int programme = Main.scanner.nextInt();
                System.out.print("Enter English Band: ");
                int englishBand = Main.scanner.nextInt();
                System.out.print("Enter Citizenship: ");
                int citizenship = Main.scanner.nextInt();

                List<String> occurrences = new ArrayList<>();
                occurrences.add("WIX1002_1");
                occurrences.add("WIX1001_2");
                occurrences.add("WIX1003_5");

                Account newAccount = new StudentAccount(siswaMail, matricNumber, password, fullName, occurrences, programme, englishBand, citizenship);
                Main.accounts.put(matricNumber, newAccount);
                DataManager.storeAccounts();
                Main.currentUser = newAccount;
            }
            case 2 -> {
                Main.scanner.nextLine();
                System.out.print("Enter UMMail address: ");
                String umMail = Main.scanner.nextLine();
                System.out.print("Enter Username: ");
                String username = Main.scanner.nextLine();
                System.out.print("Enter Password: ");
                String password = Main.scanner.nextLine();
                System.out.print("Enter Full Name: ");
                String fullName = Main.scanner.nextLine();

                List<String> occurrences = new ArrayList<>();
                occurrences.add("WIX1002_1");
                occurrences.add("WIX1001_2");
                occurrences.add("WIX1003_5");

                Account newAccount = new StaffAccount(umMail, username, password, fullName, occurrences);
                Main.accounts.put(username, newAccount);
                DataManager.storeAccounts();
                Main.currentUser = newAccount;
            }
            default -> {
                System.out.println("Invalid choice. Try again.");
                signin();
            }
        }
    }
}
