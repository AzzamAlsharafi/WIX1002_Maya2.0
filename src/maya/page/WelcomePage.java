package maya.page;

import maya.Main;

public class WelcomePage {

    public static void run(){
        System.out.printf("Hello %s!\n\n", Main.currentUser.getFullName());

        System.out.print("1. Search Modules\n2. Register Module\n3. View Timetable\n4. Exit\nEnter your choice: ");

        switch (Main.scanner.nextInt()){
            case 1 -> SearchPage.run();
            case 4-> System.exit(0);
            default -> {
                System.out.println("Invalid choice. Try again.");
                run();
            }
        }
    }
}
