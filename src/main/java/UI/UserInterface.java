package UI;
import Controller.Controller;
import Data.Member;
import Data.Result;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private final TrainerUserInterface trainerUserInterface = new TrainerUserInterface(this);
    private Controller controller = new Controller();
    private Scanner userInput = new Scanner(System.in);

    public Controller getController() {
        return controller;
    }

    //todo: UserInterface for kasserer
    //todo: UserInterface for formand
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
                case 1 -> presidentMenu();
                case 2 -> accountantMenu();
                case 3 -> trainerUserInterface.trainerMenu();
                default -> System.out.println("Ugyldig valgmulighed");
            }
        } while (true);
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
            int menuChoice = getInput("Indtast kommando (1-7): ");

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

    public Enum getDisciplineTitle() {
        System.out.println("Venligst indtast disciplintitel: ");
        System.out.println("""
                        1. Butterfly
                        2. Crawl
                        3. Rygcrawl
                        4. Brystsvømning
                        """);
        Enum disciplinetitle = null;

        do {
            int disciplineChoice = getInput("Indtast kommando (1-4): ");
            switch (disciplineChoice) {
                case 1 -> disciplinetitle = DisciplineTitles.BUTTERFLY;
                case 2 -> disciplinetitle = DisciplineTitles.CRAWL;
                case 3 -> disciplinetitle = DisciplineTitles.BACKCRAWL;
                case 4 -> disciplinetitle = DisciplineTitles.BREASTSTROKE;
                default -> System.out.println("Ugyldig kommando");
            } break;
        }while(true);
        return disciplinetitle;
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

    public LocalDate getDate() {
        System.out.print("Venligst indtast dato (ddMMyyyy): ");
        LocalDate date;
        do {
            String dateInput = new Scanner(System.in).next();
            boolean amountCharactersCorrect = dateInput.length() == 8;
            if (amountCharactersCorrect) {
                try {
                    int year = Integer.parseInt(dateInput.substring(4,8)); //24 12 1900
                    int month = Integer.parseInt(dateInput.substring(2,4));
                    int day = Integer.parseInt(dateInput.substring(0,2));
                    date = LocalDate.of(year, month, day);
                    break;
                } catch (Exception ex) {
                    System.out.println("Datoen er ugyldig");
                    System.out.print("Indtast korrekt dato: ");
                }
            } else {
                System.out.println("Indtast det rigtige format (24122022)");
            }
        } while(true);
        return date;
    }

    public void addResult() throws FileNotFoundException {
        //competition or not
        System.out.println("Indtastning af resultat.");
        System.out.println("Er det et stævneresultat? ('ja' eller 'nej')");
        boolean competitionResult;
        do {
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.next();
            if (answer.toLowerCase().equals("ja")) { // KONKURRENCETID
                competitionResult = true;

                //User ID
                String userId = getUserID(scanner);

                //Disciplinetitle
                Enum disciplinetitle = getDisciplineTitle();

                //Time
                System.out.print("Venligst indtast tidsresultatet : ");
                double timeResult = userInput.nextDouble();

                //Date
                LocalDate date = getDate();

                // competition part
                //CompetitionTitle / Stævnenavn
                System.out.print("Venligst indtast stævnenavn: ");
                userInput = new Scanner(System.in);
                String competitionTitle = userInput.nextLine();

                //Placement
                int placement = getInput("Venligst indtast placering i disciplinen: ");


                controller.addResult(disciplinetitle, timeResult, userId, date, competitionTitle, placement);
                controller.saveResults();
                break;
            } else if (answer.toLowerCase().equals("nej")) { // TRÆNINGSTID
                competitionResult = false;

                //User ID
                String userId = getUserID(scanner);

                //Disciplinetitle
                Enum disciplinetitle = getDisciplineTitle();

                //Time
                double timeResult = getInputDouble("Venligst indtast tidsresultatet: ");

                //Date
                LocalDate date = getDate();

                controller.addResult(disciplinetitle, timeResult, userId, date); //String disciplineTitle, double resultTime, String userId, LocalDate date
                controller.saveResults();
                break;
            } else {
                System.out.println("Ugyldig kommando, tast venligst 'ja' eller 'nej'");
            }
        } while (true);
    }

    public void accountantMenu() throws FileNotFoundException {
        System.out.println("Du er logget ind som kasserer.");
        do {
            System.out.println("\nDu har følgende valgmuligheder");
            System.out.println("------------------------------");

            //menu
            System.out.println("1: Se liste af medlemmers kontingenter");
            System.out.println("2: Søg efter bruger"); //from perspective of accountant - also shows payment amount
            System.out.println("3: Kontingentestimat");
            System.out.println("4: Gem og afslut");
            int menuChoice = getInput("Indtast kommando (1-4): ");
            switch (menuChoice) {
                case 1 -> printMembersSubscriptions();
                case 2 -> searchMembersSubscription();
                case 3 -> totalSubscription();
                case 4 -> exitProgram();
                default -> System.out.println("Ugyldig kommando");
            }
        } while (true);
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

    public void printMembers() {
        ArrayList<Member> members = controller.getMembers();
        for (Member member: members) {
            System.out.println(member);
        }
    }

    public void addMember() throws FileNotFoundException {
        //name
        System.out.print("Venligst indtast fulde navn: ");

        String name = userInput.nextLine();


        //birthday
        System.out.print("Venligst indtast brugerens fødselsdag (24122022): ");
        String birthday;
        do {
            String inputBirthday = userInput.next();
            boolean amountCharactersCorrect = inputBirthday.length() == 8;
            if (amountCharactersCorrect) {
                birthday = inputBirthday;
                break;
            } else {
                System.out.println("Indtast det rigtige format (24122022): ");
            }
        } while(true);

        //active member or not. Making sure the answer is suitable. Same for competitive member.
        boolean active;
        System.out.print("Venligst indtast om medlemmet er aktivt ('ja' eller 'nej'): ");
        do {
            String answerActive = userInput.next();
            if (answerActive.equals("ja")) {
                active = true;
                break;
            } else if (answerActive.equals("nej")) {
                active = false;
                break;
            } else {
                System.out.println("Venligst svar enten 'ja' eller 'nej': ");
            }
        } while (true);

        //Competitive member or not.
        boolean competitive;
        System.out.print("Venligst indsat om brugeren er konkurrencesvømmer ('ja' eller 'nej'): ");
        do {
            String competitiveAnswer = userInput.next();
            if (competitiveAnswer.equals("ja")) {
                competitive = true;
                break;
            } else if (competitiveAnswer.equals("nej")) {
                competitive = false;
                break;
            } else {
                System.out.println("Venligst svar enten 'ja' eller 'nej': ");
            }
        } while (true);

        //birthday
        System.out.print("Venligst indtast brugerens sidste betalingsår: ");
        int previousPayment;
        do {
            try {
                Scanner input = new Scanner(System.in);
                previousPayment = input.nextInt();
                break;
            } catch (Exception ex) {

            }
        } while(true);


        controller.createUser(name, birthday, active, competitive, previousPayment);
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

    public void sortMembers() {
        //Menu items
        System.out.println("\nDu kan sortere ud fra følgende:");
        System.out.println("1. Navn");
        System.out.println("2. Alder");
        System.out.println("3. Aktiv og passiv");
        System.out.println("4. Bruger-ID");
        System.out.println("5. Afslut sortering");
        int menuChoice = getInput("Indtast kommando (1-5): ");
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

    public void getTop5() {



        Enum disciplineTitle = getDisciplineTitle();
        ArrayList<Result> results = controller.getTop5(disciplineTitle);
        for (Result result: results) {
            System.out.println(result);
        }
    }
}