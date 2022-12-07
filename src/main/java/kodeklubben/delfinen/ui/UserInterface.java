package kodeklubben.delfinen.ui;
import kodeklubben.delfinen.controller.Controller;
import kodeklubben.delfinen.data.Member;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;


public class UserInterface {
    private Scanner userInput = new Scanner(System.in);
    private final TrainerUserInterface trainerUserInterface = new TrainerUserInterface(this);
    private final PresidentUserInterface presidentUserInterface = new PresidentUserInterface(this);
    private final AccountantUserInterface accountantUserInterface = new AccountantUserInterface(this);
    private final Controller controller = new Controller();

    public Controller getController() {
        return controller;
    }

    public Scanner getUserInput() {
        return userInput;
    }

    public void loadData() {
        controller.loadMemberData();
        controller.loadResults();
    }
    
    public void start() {
        loadData();
        do {
            //Introduction
            //Print delfin picture
            System.out.println("\u001B[0m\n" +
                    "\u001B[0m\n" +
                    "            \u001B[1;30m▄\u001B[46m▀\u001B[34m▄\u001B[30;40m█\u001B[0m        \u001B[1;30m█▄\u001B[0m    \u001B[1;30m▄█\u001B[0m\n" +
                    "           \u001B[1;30m▄\u001B[46m▀\u001B[34m▄\u001B[40m█\u001B[30;44m▀\u001B[40m▄\u001B[0m       \u001B[1;30m▀\u001B[5;44m▄▀\u001B[0;1;30m▄▄\u001B[5;44m▀\u001B[0;1;34m█\u001B[30m█\u001B[0m\n" +
                    "       \u001B[1;30m▄\u001B[46m▀▀▀\u001B[34;40m████\u001B[44m▄\u001B[30m▀\u001B[40m▄▄\u001B[0m       \u001B[1;30m▀\u001B[5;44m▄\u001B[0;1;34;44m▄\u001B[30m▄\u001B[40m▀\u001B[0m\n" +
                    "    \u001B[1;30m▄▄\u001B[46m▀\u001B[34m▄\u001B[40m▀██████████\u001B[5;30;44m▀▀\u001B[0;1;30;46m▀▀\u001B[40m▄▄▄\u001B[5;44m▀\u001B[0;1;34;44m▀\u001B[5;30m▄\u001B[0;1;30m▀\u001B[0m\n" +
                    "   \u001B[1;30m█\u001B[37;46m▄▄\u001B[5;34;47m▀▀▀\u001B[0;1;34m█\u001B[44m▀\u001B[5;30m▄\u001B[0;1;34m████\u001B[5;30;44m▄\u001B[0;1;34m████████\u001B[30;44m▄▄\u001B[40m▀\u001B[0m\n" +
                    "    \u001B[1;30m▀▀▀▀▀\u001B[5;47m▄\u001B[0;1;30;47m▄▄\u001B[40m█\u001B[34m█\u001B[0;34m█\u001B[1;30m█\u001B[5;44m▄▄▄▄▄▄\u001B[0;1;30m▀▀▀\u001B[0m\n" +
                    "             \u001B[1;30m▀\u001B[44m▄\u001B[40m▀\u001B[0m\n");

            System.out.println("Velkommen til Delfinens medlemsdatabase");
            System.out.println("Du kan logge ind som følgende:");
            System.out.println("1 - Formand");
            System.out.println("2 - Kasserer");
            System.out.println("3 - Træner");
            int loginChoice = getIntInput("Indtast kommando (1-3): ", 1, 4, "Indtast venligst et tal mellem 1 og 3");
            switch (loginChoice) {
                case 1 -> presidentUserInterface.presidentMenu();
                case 2 -> accountantUserInterface.accountantMenu();
                case 3 -> trainerUserInterface.trainerMenu();
                default -> System.out.println("Ugyldig valgmulighed");

            }
        } while (true);
    }

    public String getUserID() {
        String userId;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("Venligst indtast svømmerens ID: ");
            String answer = scanner.next();
            // matcher userinput bruger id på en rigtig member? hvis nej så print det er ikke en medlems id prøve igen
            if (controller.userExists(answer)) {
                userId = answer;
                break;
            } else {
                System.out.println("Der er ingen medlemmer med dette medlemsId");
            }

        } while (true);
        return userId;
    }

    public void exitProgram() {
        controller.saveMemberData();
        controller.saveResults();
        System.exit(0);
    }

    public String getSearchCriteria(int menuChoice, String text) {
        System.out.println(text);
        do {
            Scanner scanner = new Scanner(System.in);
            try {
                if (menuChoice == 2) {
                    int search = scanner.nextInt();
                    return String.valueOf(search);
                } else {
                    return scanner.next().toLowerCase();
                }
            } catch (Exception exception) {
                System.out.println("Fejl, ugyldigt søgekriterie");
            }
        } while (true);
    }

    public void searchMembers() {
        //Menu items
        System.out.println("\nDu kan søge ud fra følgende:");
        System.out.println("1. Navn");
        System.out.println("2. Alder");
        System.out.println("3. Bruger-ID");
        System.out.println("4. Aktivt medlem ('ja'/'nej')");
        System.out.println("5. Afslut søgning");

        //menu choice
        int menuChoice = getIntInput("Indtast kommando (1-5): ", 1, 5, "Indtast venligst et tal mellem 1 og 5");
        if (menuChoice != 5) {
            String search = getSearchCriteria(menuChoice, "Indtast søgeord: ");

            ArrayList<Member> members = controller.searchMembers(menuChoice, search);
            for (Member member: members) {
                System.out.println(member);
            }
        }
    }

    public LocalDate getDate() {
        System.out.print("Venligst indtast dato (ddMMyyyy): ");
        LocalDate date;
        do {
            String dateInput = new Scanner(System.in).next();
            boolean amountCharactersCorrect = dateInput.length() == 8;
            if (amountCharactersCorrect) {
                try {
                    int year = Integer.parseInt(dateInput.substring(4, 8)); //24 12 1900
                    int month = Integer.parseInt(dateInput.substring(2, 4));
                    int day = Integer.parseInt(dateInput.substring(0, 2));
                    date = LocalDate.of(year, month, day);
                    break;
                } catch (Exception ex) {
                    System.out.println("Datoen er ugyldig");
                    System.out.print("Indtast korrekt dato: ");
                }
            } else {
                System.out.println("Indtast det rigtige format (24122022)");
            }
        } while (true);
        return date;
    }

    //Inputs

    public int getIntInput(String text, int min, int max, String onError) {
        Scanner scanner = new Scanner(System.in);
        do {
            try {
                System.out.print(text);
                int result = scanner.nextInt();
                if (min <= result && result < max) {
                    return result;
                }
            } catch (Exception exception) {
                System.out.println(onError);
                return 0;
            }
        } while (true);
    }

    public double getInputDouble(String text, int min, int max) {
        do {
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.print(text);
                double result = scanner.nextDouble();
                if (min <= result && result < max) {
                    return result;
                }
            } catch (Exception exception) {
                System.out.println("Fejl, venligst indtast et tal");
            }
        } while (true);
    }

    public String getStringInput(String text, int minLength, int maxLength, String onError) {
        do {
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.print(text);
                String result = scanner.nextLine();
                if (minLength <= result.length() && maxLength > result.length()) {
                    return result;
                }
            } catch (Exception exception) {
                System.out.println(onError);
            }
        } while (true);
    }

    public boolean getBoolean(String text, String onError) {
        System.out.print(text);
        do {
            String answerActive = userInput.nextLine().toLowerCase();
            if (answerActive.equals("ja")) {
                return true;
            } else if (answerActive.equals("nej")) {
                return false;
            } else {
                System.out.println(onError);
            }
        } while (true);
    }
}