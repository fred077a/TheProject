package UI;

import Controller.Controller;
import Data.Member;
import Data.Result;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class TrainerUserInterface {
    private final UserInterface userInterface;
    private Scanner userInput;

    public TrainerUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
        this.userInput = this.userInterface.getUserInput();
    }

    public void getTop5() {

        Enum disciplineTitle = getDisciplineTitle();
        ArrayList<Result> results = userInterface.getController().getTop5(disciplineTitle);
        for (Result result: results) {
            System.out.println(result);
        }
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
            int disciplineChoice = userInterface.getInput("Indtast kommando (1-4): ");
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

    public void trainerMenu() throws FileNotFoundException {
        userInterface.getController().loadResults();
        System.out.println("Du er logget ind som træner.");
        do {
            System.out.println("\nDu har følgende valgmuligheder");
            System.out.println("------------------------------");

            //menu
            System.out.println("1: Se liste af konkurrencesvømmere");
            System.out.println("2: Registrér resultat");
            System.out.println("3: Se liste af top 5 svømmere");
            System.out.println("4: Søg");
            System.out.println("5: Se hold");
            System.out.println("6: Gem og afslut");
            int menuChoice = userInterface.getInput("Indtast kommando (1-6): ");

            switch (menuChoice) {
                case 1 -> getCompetitiveMembers();
                case 2 -> addResult();
                case 3 -> getTop5();
                case 4 -> userInterface.searchMembers();
                //case 5 ->
                case 6 -> userInterface.exitProgram();
                default -> System.out.println("Ugyldig kommando");
            }
        } while (true);
    }

    public void addResult() throws FileNotFoundException {
        //competition or not
        System.out.println("Indtastning af resultat.");
        System.out.println("Er det et stævneresultat? ('ja' eller 'nej')");
        Controller controller = userInterface.getController();
        boolean competitionResult;
        do {
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.next();
            if (answer.toLowerCase().equals("ja")) { // KONKURRENCETID
                competitionResult = true;

                //User ID
                String userId = userInterface.getUserID(scanner);

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
                int placement = userInterface.getInput("Venligst indtast placering i disciplinen: ");

                controller.addResult(disciplinetitle, timeResult, userId, date, competitionTitle, placement);
                controller.saveResults();
                break;
            } else if (answer.toLowerCase().equals("nej")) { // TRÆNINGSTID
                competitionResult = false;

                //User ID
                String userId = userInterface.getUserID(scanner);

                //Disciplinetitle
                Enum disciplinetitle = getDisciplineTitle();

                //Time
                double timeResult = userInterface.getInputDouble("Venligst indtast tidsresultatet: ");

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

    public void getCompetitiveMembers() {
        ArrayList<Member> members = userInterface.getController().getCompetitiveMembers();
        for (Member member: members) {
            System.out.println(member);
        }
    }
}