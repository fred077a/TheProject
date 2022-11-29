package UI;
import Controller.Controller;
import Data.Member;
import Data.Result;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private Scanner userInput = new Scanner(System.in);
    private final TrainerUserInterface trainerUserInterface = new TrainerUserInterface(this);
    private final PresidentUserInterface presidentUserInterface = new PresidentUserInterface(this);
    private final AccountantUserInterface accountantUserInterface = new AccountantUserInterface(this);
    private Controller controller = new Controller();

    public Controller getController() {
        return controller;
    }

    public Scanner getUserInput() {
        return userInput;
    }

    public void start() throws FileNotFoundException {
        controller.loadMemberData();
        do {
            //Introduction
            System.out.println("Velkommen til Delfinens medlemsdatabase");
            System.out.println("Du kan logge ind som følgende:");
            System.out.println("1 - Formand");
            System.out.println("2 - Kasserer");
            System.out.println("3 - Træner");
            int loginChoice = getInput("Indtast kommando (1-3): ");
            switch (loginChoice) {
                case 1 -> presidentUserInterface.presidentMenu();
                case 2 -> accountantUserInterface.accountantMenu();
                case 3 -> trainerUserInterface.trainerMenu();
                default -> System.out.println("Ugyldig valgmulighed");
            }
        } while (true);
    }

    public String getUserID(Scanner scanner) {
        String userId;
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

    public void exitProgram() throws FileNotFoundException {
        controller.saveMemberData();
        controller.saveResults();
        System.exit(0);
    }

    public String getSearchCriteria(int menuChoice) {
        do {
            Scanner scanner = new Scanner(System.in);
            try {
                if (menuChoice == 2) {
                    int search = scanner.nextInt();
                    return String.valueOf(search);
                } else {
                    return scanner.next();
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
        int menuChoice = getInput("Indtast kommando (1-5): ");
        if (menuChoice != 5) {
            System.out.print("Indtast søgeord: ");
            String search = getSearchCriteria(menuChoice);

            ArrayList<Member> members = controller.searchMembers(menuChoice, search);
            for (Member member: members) {
                System.out.println(member);
            }
        }
    }

    //Inputs
    public int getInput(String text) {
        Scanner scanner = new Scanner(System.in);
        do {
            try {
                System.out.print(text);
                return scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("Fejl, venligst indtast et tal");
                scanner.nextLine();

            }
        } while (true);
    }

    public double getInputDouble(String text) {
        do {
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.print(text);
                return scanner.nextDouble();
            } catch (Exception exception) {
                System.out.println("Fejl, venligst indtast et tal");
            }
        } while (true);
    }
}