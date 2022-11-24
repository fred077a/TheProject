package UI;
import Controller.Controller;
import Data.Member;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {
    private Controller controller = new Controller();
    private Scanner userInput = new Scanner(System.in);

    public void start() throws FileNotFoundException {
        controller.loadMemberData();
        do {
            //Introduction
            System.out.println("Velkommen til Delfinens medlemsdatabase");
            System.out.println("Du kan logge ind som følgende:");
            System.out.println("1 - Formand");
            System.out.println("2 - Kasserer");
            System.out.println("3 - Træner");
            int loginChoice = getMenuChoice("(1-3)");
            switch (loginChoice) {
                case 1 -> presidentMenu();
                case 2 -> accountantMenu();
                case 3 -> trainerMenu();
                default -> System.out.println("Ugyldig valgmulighed");
            }
        } while (true);
    }

    public int getMenuChoice(String range) {
        do {
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.print("Vælg kommando" + range + ": ");
                return scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("Fejl, venligst indtast et tal");
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

    public void presidentMenu() throws FileNotFoundException {
        System.out.println("Du er logget ind som formand.");
        do {
            System.out.println("\nDu har følgende valgmuligheder");
            System.out.println("------------------------------");

            //menu
            System.out.println("1: Opret nyt medlem");
            System.out.println("2: Se liste af medlemmer");
            System.out.println("3: Redigere medlem");
            System.out.println("4: Slet et medlem");
            System.out.println("5: Sortér medlemmer");
            System.out.println("6: Søg blandt medlemmer");
            System.out.println("7: Gem og afslut");
            int menuChoice = getMenuChoice("(1-8)");

            switch (menuChoice) {
                case 1 -> addMember();
                case 2 -> printMembers();
                case 3 -> editMember();
                case 4 -> deleteMember();
                case 5 -> sortMembers();
                case 6 -> searchMembers();
                case 7 -> exitProgram();
                default -> System.out.println("Ugyldig kommando");
            }
        } while (true);
    }

    public void trainerMenu() throws FileNotFoundException {
        System.out.println("Du er logget ind som træner.");
        do {
            System.out.println("\nDu har følgende valgmuligheder");
            System.out.println("------------------------------");

            //menu
            System.out.println("1: Se liste af konkurrencesvømmere");
            System.out.println("2: Registrér resultat");
            System.out.println("3: Se liste af top 5 svømmere");
            System.out.println("4: Søg");
            System.out.println("5: Filtrér medlemmer");
            System.out.println("6: Gem og afslut");
            int menuChoice = getMenuChoice("(1-6)");

            switch (menuChoice) {
                case 1 -> getCompetitiveMembers();
                //case 2 ->
                //case 3 ->
                case 4 -> searchMembers();
                //case 5 ->
                case 6 -> exitProgram();
                default -> System.out.println("Ugyldig kommando");
            }
        } while (true);
    }

    public void addResult() {

        //Svimmer id
        System.out.print("Venligst indtast svømmerens ID: ");
        String userId = userInput.nextLine();

        //time
        System.out.print("Venligst indtast svømmerens tid: ");
        String cpr;
        do {
            String inputBirthday = userInput.next();
            boolean amountCharactersCorrect = inputBirthday.length() == 8;
            if (amountCharactersCorrect) {
                cpr = inputBirthday;
                break;
            } else {
                System.out.println("Indtast det rigtige format (24122022)");
            }
        } while(true);


        //placement
        boolean active;
        System.out.print("Venligst indtast svømmerens placering: ");
        do {
            String answerActive = userInput.next();
            if (answerActive.equals("ja")) {
                active = true;
                break;
            } else if (answerActive.equals("nej")) {
                active = false;
                break;
            } else {
                System.out.println("Venligst svar enten 'ja' eller 'nej'");
            }
        } while (true);

        //Competitive member or not.

        //birthday

    }

    public void accountantMenu() throws FileNotFoundException {
        System.out.println("Du er logget ind som træner.");
        do {
            System.out.println("du har følgende valgmuligheder");
            System.out.println("");

            //menu
            System.out.println("1: Se liste af medlemmers kontingenter");
            System.out.println("2: Søg efter bruger"); //from perspective of accountant - also shows payment amount
            System.out.println("3: Kontingentestimat");
            System.out.println("4: Gem og afslut");
            int menuChoice = getMenuChoice("(1-5)");
            switch (menuChoice) {
                case 5 -> exitProgram();
                default -> System.out.println("Ugyldig kommando");
            }
        } while (true);
    }

    public void exitProgram() throws FileNotFoundException {
        controller.saveMemberData();
        System.exit(0);
    }

    public void printMembers() {
        ArrayList<Member> members = controller.getMembers();
        for (Member member: members) {
            System.out.println(member);
        }
    }

    public void addMember() throws FileNotFoundException {
        //name
        System.out.print("Venligst indtast fulde navn: ");
        // Scannerbug fix
        userInput.nextLine();
        String name = userInput.nextLine();


        //birthday
        System.out.print("Venligst indtast brugerens fødselsdag (24122022): ");
        String cpr;
        do {
            String inputBirthday = userInput.next();
            boolean amountCharactersCorrect = inputBirthday.length() == 8;
            if (amountCharactersCorrect) {
                cpr = inputBirthday;
                break;
            } else {
                System.out.println("Indtast det rigtige format (24122022)");
            }
        } while(true);

        //active member or not. Making sure the answer is suitable. Same for competitive member.
        boolean active;
        System.out.print("Venligst indtast om medlemmet er aktivt ('ja' eller 'nej')");
        do {
            String answerActive = userInput.next();
            if (answerActive.equals("ja")) {
                active = true;
                break;
            } else if (answerActive.equals("nej")) {
                active = false;
                break;
            } else {
                System.out.println("Venligst svar enten 'ja' eller 'nej'");
            }
        } while (true);

        //Competitive member or not.
        boolean competitive;
        System.out.print("Venligst indsat om brugeren er konkurrencesvømmer ('ja' eller 'nej')");
        do {
            String competitiveAnswer = userInput.next();
            if (competitiveAnswer.equals("ja")) {
                competitive = true;
                break;
            } else if (competitiveAnswer.equals("nej")) {
                competitive = false;
                break;
            } else {
                System.out.println("Venligst svar enten 'ja' eller 'nej'");
            }
        } while (true);

        //birthday
        System.out.print("Venligst indtast brugerens sidste betalingsår");
        int previousPayment;
        do {
            try {
                Scanner input = new Scanner(System.in);
                previousPayment = input.nextInt();
                break;
            } catch (Exception ex) {

            }
        } while(true);


        controller.createUser(name, cpr, active, competitive, previousPayment);
    }

    public void printMemberMenu() {
        for (int i = 0; i < controller.getMembers().size(); i++) {
            System.out.println(i + 1 + ": " + controller.getMembers().get(i));
        }
    }

    public void editMember() throws FileNotFoundException {
        printMemberMenu();
        System.out.print("Hvilket medlem skal redigeres: ");
        int memberNumber = userInput.nextInt();
        userInput.nextLine(); // scannerbug
        Member memberToBeEdited = controller.getMembers().get(memberNumber - 1);

        boolean exit = false;
        do {
            System.out.println("""
                        Hvad skal redigeres?
                        1: Navn
                        2: Fødselsdag
                        3: Aktiv / Passiv svømmer
                        4: Konkurrencesvømmer / Fritidsvømmer
                        5: Afslut redigering af svømmer
                        """);
            int menuChoice = userInput.nextInt();
            userInput.nextLine(); //scannerbug
            switch (menuChoice) {
                case 1: {
                    System.out.print("Du vil skifte " + memberToBeEdited.getName() + " til: ");
                    String newName = userInput.nextLine();
                    memberToBeEdited.setName(newName);
                    break;
                }
                case 2: {
                    System.out.print("Du vil skifte " + memberToBeEdited.getBirthday() + " til: ");
                    do {
                        String newBirthday = userInput.nextLine();
                        boolean amountCharactersCorrect = newBirthday.length() == 8;
                        if (amountCharactersCorrect) {
                            memberToBeEdited.setBirthday(newBirthday);
                            break;
                        } else {
                            System.out.println("Ugyldigt input\nIndtast venligst efter det rigtige format (24122022)");
                        }
                    }while(true);
                    break;
                }
                case 3: {
                    System.out.print("Du vil skifte \"" + memberToBeEdited.activeStatusToString() + "\" til ja eller nej");
                    do {
                        String answerActive = userInput.next();
                        if (answerActive.equals("ja")) {
                            memberToBeEdited.setActive(true);
                            System.out.println("Du har ændret aktivitetsstatus til \"ja\"");
                            break;
                        } else if (answerActive.equals("nej")) {
                            memberToBeEdited.setActive(false);
                            System.out.println("Du har ændret aktivitetsstatus til \"nej\"");
                            break;
                        } else {
                            System.out.println("Venligst svar enten 'ja' eller 'nej'");
                        }
                    } while (true);
                    break;
                }
                case 4: {
                    System.out.print("Du vil skifte \"" + memberToBeEdited.competetiveStatusToString() + "\" til ja eller nej");
                    do {
                        String answerActive = userInput.next();
                        if (answerActive.toLowerCase().equals("ja")) {
                            memberToBeEdited.setCompetitiveStatus(true);
                            System.out.println("Du har ændret konkurrencestatus til \"ja\"");
                            break;
                        } else if (answerActive.toLowerCase().equals("nej")) {
                            memberToBeEdited.setCompetitiveStatus(false);
                            System.out.println("Du har ændret konkurrencestatus til \"nej\"");
                            break;
                        } else {
                            System.out.println("Venligst svar enten 'ja' eller 'nej'");
                        }
                    } while (true);
                    break;

                }
                case 5: {
                    exit = true;
                    break;
                }
                default:
                    System.out.println("Ugyldigt input");
            }
        } while (exit == false);
        controller.saveMemberData();
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
        int menuChoice = getMenuChoice("(1-5)");
        if (menuChoice != 5) {
            System.out.print("Indtast søgeord: ");
            String search = getSearchCriteria(menuChoice);

            ArrayList<Member> members = controller.searchMembers(menuChoice, search);
            for (Member member: members) {
                System.out.println(member);
            }
        }
    }

    public void sortMembers() {
        //Menu items
        System.out.println("\nDu kan sortere ud fra følgende:");
        System.out.println("1. Navn");
        System.out.println("2. Alder");
        System.out.println("3. Aktiv og passiv");
        System.out.println("4. Bruger-ID");
        System.out.println("5. Afslut sortering");
        int menuChoice = getMenuChoice("(1-5)");
        if (menuChoice != 5) {
            controller.sortMembers(menuChoice);
            printMembers();
        }
    };

    public void deleteMember() throws FileNotFoundException {
        System.out.print("Indtast brugerID på medlemmet der skal fjernes: ");
        String search = userInput.next();
        Member memberFound = controller.getMemberFromUid(search);
        if (memberFound.getUid().equals("")) {
            System.out.println("Ingen medlemmer med bruger-ID " + search);
        } else {
            System.out.print("Er du sikker på at slette '" + memberFound.getName() + "'? ('ja' eller 'nej') ");
            do {
                String confirmationAnswer = userInput.next();
                if (confirmationAnswer.toLowerCase().equals("ja")) {
                    System.out.println(memberFound.getName() + " er blevet slettet fra systemet");
                    controller.deleteMember(memberFound);
                    break;
                } else if (confirmationAnswer.toLowerCase().equals("nej")) {
                    break;
                } else {
                    System.out.println("Venligst svar 'ja' eller 'nej'");
                }
            } while (true);
        }
    }

    public void printMembersSubscriptions() {
        ArrayList<Member> members = controller.getMembers();
        for (Member member: members) {
            System.out.println("Navn: '" + member.getName() + "', Bruger-ID: '" + member.getUid() + "', Aktiv medlem: '"
                    + (member.getActive()? "Ja" : "Nej") + "' , Kontingentbeløb: 'kr." + member.getSubscriptionAmount() + "'");
        }
    }

    public void getCompetitiveMembers() {
        ArrayList<Member> members = controller.getCompetitiveMembers();
        for (Member member: members) {
            System.out.println(member);
        }
    }

    public void searchMembersSubscription() {
        //Menu items
        System.out.println("\nDu kan søge ud fra følgende:");
        System.out.println("1. Navn");
        System.out.println("2. Alder");
        System.out.println("3. Bruger-ID");
        System.out.println("4. Aktiv-status ('ja' eller 'nej')");
        System.out.println("5. I restance ('ja' eller 'nej')");
        System.out.println("6 Afslut søgning");

        //menu choice
        int menuChoice = getMenuChoice("(1-6)");
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
}