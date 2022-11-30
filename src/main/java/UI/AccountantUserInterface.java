package UI;

import Controller.Controller;
import Data.Member;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountantUserInterface {
    private final UserInterface userInterface;
    private Scanner userInput;


    public AccountantUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
        this.userInput = this.userInterface.getUserInput();
    }

    public void accountantMenu() throws FileNotFoundException {
        boolean running = true;
        System.out.println("Du er logget ind som kasserer.");
        do {
            System.out.println("\nDu har følgende valgmuligheder");
            System.out.println("------------------------------");

            //menu
            System.out.println("1: Se liste af medlemmers kontingenter");
            System.out.println("2: Søg efter bruger"); //from perspective of accountant - also shows payment amount
            System.out.println("3: Kontingentestimat");
            System.out.println("4: Log ud");
            int menuChoice = userInterface.getIntInput("Indtast kommando (1-4): ", 1, 5, "Indtast venligst et tal mellem 1-5");
            switch (menuChoice) {
                case 1 -> printMembersSubscriptions();
                case 2 -> searchMembersSubscription();
                case 3 -> totalSubscription();
                case 4 -> running = false;
                default -> System.out.println("Ugyldig kommando");
            }
        } while (running);
    }

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

    public void printMembersSubscriptions() {
        final String red = "\u001B[31m";
        final String green = "\u001B[32m";
        final String yellow = "\u001B[33m";
        final String blue = "\u001B[34m";
        final String purple = "\u001B[35m";
        final String cyan = "\u001B[36m";
        final String resetText = "\u001B[0m";
        Controller controller = userInterface.getController();
        ArrayList<Member> members = controller.getMembers();
        int longestName = 0;
        int longestUserId = 0;
        for (Member member: members) {
            int nameLength = member.getName().length();
            if (nameLength > longestName) {
                longestName = nameLength;
            }
            int uidLength = member.getUid().length();
            if (uidLength > longestUserId) {
                longestUserId = uidLength;
            }
        }
        for (Member member: members) {
            //System.out.println("Navn: '" + member.getName() + "', Bruger-ID: '" + member.getUid() + "', Aktiv medlem: '"
            //        + (member.getActive()? "Ja" : "Nej") + "' , Kontingentbeløb: 'kr." + member.getSubscriptionAmount() + "'");
            System.out.printf( "Navn: %-" + longestName + "s " + resetText +
                    cyan + " Bruger-ID: %-" + longestUserId +"s " + resetText +
                    blue + " Aktiv: %-3s " + resetText +
                    purple + "Kontingentbeløb: %-15s" + resetText + "\n",
                    member.getName(), member.getUid(), member.getActive()? "Ja" : "Nej", member.getSubscriptionAmount());



        }
    }

    /*

        ArrayList<Member> members = userInterface.getController().getMembers();
        int longestName = 0;
        int longestUserId = 0;
        for (Member member: members) {
            int nameLength = member.getName().length();
            if (nameLength > longestName) {
                longestName = nameLength;
            }
            int uidLength = member.getUid().length();
            if (uidLength > longestUserId) {
                longestUserId = uidLength;
            }
        }
        for (Member member: members) {
            System.out.printf(
                    red + "Navn: %-" + longestName +"s " + resetText +//
                            green + " Fødselsdag: %-8s " + resetText +
                            yellow + " Alder: %-3s" + resetText +
                            blue + " Aktiv: %-3s " + resetText +
                            cyan + " Bruger-ID: %-" + longestUserId +"s " + resetText +//
                            purple + " Konkurrencesvømmer: %-3s " + resetText + "\n"
                    , member.getName(), member.getBirthday(), member.getAge(), member.getActive()? "Ja" : "Nej", member.getUid(), member.getCompetitiveStatus()? "Ja" : "Nej");
        }
    }


     */

    public void searchMembersSubscription() {
        Controller controller = userInterface.getController();
        //Menu items
        System.out.println("\nDu kan søge ud fra følgende:");
        System.out.println("1. Navn");
        System.out.println("2. Alder");
        System.out.println("3. Bruger-ID");
        System.out.println("4. Aktiv-status ('ja' eller 'nej')");
        System.out.println("5. I restance ('ja' eller 'nej')");
        System.out.println("6 Afslut søgning");

        //menu choice
        int menuChoice = getInput("Indtast kommando (1-6): ");
        if (menuChoice != 6) {
            System.out.print("Indtast søgeord: ");
            String search = getSearchCriteria(menuChoice);

            ArrayList<Member> members = controller.searchMembers(menuChoice, search);
            for (Member member: members) {
                System.out.println(
                        "Navn: '" + member.getName()
                                + "', Bruger-ID: '" + member.getUid()
                                + "', Aktiv medlem: '" + (member.getActive()? "Ja" : "Nej")
                                + "' , Kontingentbeløb: 'kr." + member.getSubscriptionAmount()
                                + "' , Antal år ikke betalt for: '" + member.lateOnPayments() + "' ");

            }
        }
    }

    public void totalSubscription() {
        Controller controller = userInterface.getController();
        ArrayList<Member> members = controller.getMembers();
        int totalSubscriptionAmount = 0;
        int fromLatePayments = 0;
        int fromPassiveMembers = 0;
        int fromActiveMembers = 0;


        for (Member member: members) {
            double subscriptionAmount = member.getSubscriptionAmount();
            totalSubscriptionAmount += subscriptionAmount;
            if (member.lateOnPayments() > 0) {
                fromLatePayments += subscriptionAmount;
                totalSubscriptionAmount += subscriptionAmount;
            }
            if (!member.getActive()) {
                fromPassiveMembers += subscriptionAmount;
            } else {
                fromActiveMembers += subscriptionAmount;
            }
        }

        System.out.println();
        System.out.println("Estimeret kontigentbeløb for i år er: kr. " + totalSubscriptionAmount + " (inkl. betalinger i restance)");
        System.out.println("Estimeret kontigentbeløb for i år er: kr. " + (totalSubscriptionAmount - fromLatePayments) + " (eksl. betalinger i restance)");
        System.out.println("Heraf kr. " + fromLatePayments + " fra medlemmer i restance.");
        System.out.println("kr. " + fromActiveMembers + " fra aktive medlemmer " +
                "og kr. " + fromPassiveMembers + " fra passive medlemmer");
        System.out.println();
    }

    public void exitProgram() throws FileNotFoundException {
        Controller controller = userInterface.getController();
        controller.saveMemberData();
        controller.saveResults();
        System.exit(0);
    }





}