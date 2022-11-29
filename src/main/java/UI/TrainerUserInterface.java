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
            int menuChoice = userInterface.getIntInput("Indtast kommando (1-6): ", 1, 7, "Indtast venligst et tal mellem 1-6");

            switch (menuChoice) {
                case 1 -> getCompetitiveMembers();
                case 2 -> addResult();
                case 3 -> showTop5();
                case 4 -> userInterface.searchMembers();
                //case 5 ->
                case 6 -> userInterface.exitProgram();
                default -> System.out.println("Ugyldig kommando");
            }
        } while (true);
    }

    public void showTop5() {
        //Vælg mellem træningResultat eller KonkuranceResultat
        System.out.println("Vælg hvad du vil se en top5 for");
        System.out.println("1: Top 5 svømmetider i træning");
        System.out.println("2: Top 5 svømmetider blandt konkurrencesvømmere ");
        int resultatType = userInterface.getIntInput("Indtast kommando (1-2): ", 1, 3, "Indtast venligst et tal mellem 1-2");

        switch (resultatType) {
            case 1 -> top5Training();
            case 2 -> top5Compitition();

        }
    }

    public void top5Training() {
        System.out.println("Top5 svømmetider for senior");
        printResults(userInterface.getController().getTop5(DisciplineTitles.BUTTERFLY, true, false), "Butterfly: ");
        printResults(userInterface.getController().getTop5(DisciplineTitles.CRAWL, true, false), "Crawl: ");
        printResults(userInterface.getController().getTop5(DisciplineTitles.BACKCRAWL, true, false), "Backcrawl: ");
        printResults(userInterface.getController().getTop5(DisciplineTitles.BREASTSTROKE, true, false), "Brystsvømning: ");

        System.out.println("Top5 svømmetider for juinor");
        printResults(userInterface.getController().getTop5(DisciplineTitles.BUTTERFLY, false, false), "Butterfly: ");
        printResults(userInterface.getController().getTop5(DisciplineTitles.CRAWL, false, false), "Crawl: ");
        printResults(userInterface.getController().getTop5(DisciplineTitles.BACKCRAWL, false, false), "Backcrawl: ");
        printResults(userInterface.getController().getTop5(DisciplineTitles.BREASTSTROKE, false, false), "Brystsvømning: ");


    }

    public void printResults(ArrayList<Result> results, String title) {
        System.out.println(title);
        for (Result result: results) {
            System.out.println(result);
        }
        System.out.println();
    }

    public void top5Compitition() {
        System.out.println("Top5 svømmetider for senior");
        printResults(userInterface.getController().getTop5(DisciplineTitles.BUTTERFLY, true, true), "Butterfly: ");
        printResults(userInterface.getController().getTop5(DisciplineTitles.CRAWL, true, true), "Crawl: ");
        printResults(userInterface.getController().getTop5(DisciplineTitles.BACKCRAWL, true, true), "Backcrawl: ");
        printResults(userInterface.getController().getTop5(DisciplineTitles.BREASTSTROKE, true, true), "Brystsvømning: ");

        System.out.println("Top5 svømmetider for juinor");
        printResults(userInterface.getController().getTop5(DisciplineTitles.BUTTERFLY, false, true), "Butterfly: ");
        printResults(userInterface.getController().getTop5(DisciplineTitles.CRAWL, false, true), "Crawl: ");
        printResults(userInterface.getController().getTop5(DisciplineTitles.BACKCRAWL, false, true), "Backcrawl: ");
        printResults(userInterface.getController().getTop5(DisciplineTitles.BREASTSTROKE, false, true), "Brystsvømning: ");
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
            int disciplineChoice = userInterface.getIntInput("Indtast kommando (1-4): ", 1, 5, "Indtast venligst et tal mellem 1-4");
            switch (disciplineChoice) {
                case 1 -> disciplinetitle = DisciplineTitles.BUTTERFLY;
                case 2 -> disciplinetitle = DisciplineTitles.CRAWL;
                case 3 -> disciplinetitle = DisciplineTitles.BACKCRAWL;
                case 4 -> disciplinetitle = DisciplineTitles.BREASTSTROKE;
                default -> System.out.println("Ugyldig kommando");
            }
            break;
        } while (true);
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

    public void addResult() throws FileNotFoundException {
        //competition or not
        System.out.println("Indtastning af resultat.");
        Controller controller = userInterface.getController();
        boolean competitionResult = userInterface.getBoolean("Er det et stævneresultat? ('ja'/'nej')", "Venligst indtast 'ja' eller 'nej'");
        do {
            if (competitionResult) {
                //User ID
                String userId = userInterface.getUserID();

                //Disciplinetitle
                Enum disciplinetitle = getDisciplineTitle();

                //Time
                double timeResult = userInterface.getInputDouble("Venligst indtast tidsresultatet i sekunder: ", 1, 1500);

                //Date
                LocalDate date = getDate();

                //CompetitionTitle
                String competitionTitle = userInterface.getStringInput("Venligst indtast stævnenavn: ", 1, 100, "Ugyldig indtastning");

                //Placement
                int placement = userInterface.getIntInput("Venligst indtast placering i disciplinen: ", 1, 1000, "Indtast venligst en placering mellem 1-1000");

                controller.addResult(disciplinetitle, timeResult, userId, date, competitionTitle, placement);
                controller.saveResults();
                break;
            } else { // TRÆNINGSTID

                //User ID
                String userId = userInterface.getUserID();

                //Disciplinetitle
                Enum disciplinetitle = getDisciplineTitle();

                //Time
                double timeResult = userInterface.getInputDouble("Venligst indtast tidsresultatet i sekunder: ", 1, 10000);

                //Date
                LocalDate date = getDate();

                controller.addResult(disciplinetitle, timeResult, userId, date); //String disciplineTitle, double resultTime, String userId, LocalDate date
                controller.saveResults();
                break;
            }
        } while (true);
    }

    public void getCompetitiveMembers() {
        ArrayList<Member> members = userInterface.getController().getCompetitiveMembers();
        for (Member member : members) {
            System.out.println(member);
        }
    }
}