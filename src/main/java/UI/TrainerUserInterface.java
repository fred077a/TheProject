package UI;
import Controller.Controller;
import Data.CompetitionResult;
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
        boolean running = true;
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
            System.out.println("6: Log ud");
            int menuChoice = userInterface.getIntInput("Indtast kommando (1-6): ", 1, 7, "Indtast venligst et tal mellem 1-6");
            switch (menuChoice) {
                case 1 -> getCompetitiveMembers();
                case 2 -> addResult();
                case 3 -> showTop5Menu();
                case 4 -> userInterface.searchMembers();
                case 5 -> showTeams();
                case 6 -> running = false;
                default -> System.out.println("Ugyldig kommando");
            }
        } while (running);
    }

    public void showTeams() {
        final String red = "\u001B[31m";
        final String green = "\u001B[32m";
        final String yellow = "\u001B[33m";
        final String blue = "\u001B[34m";
        final String purple = "\u001B[35m";
        final String cyan = "\u001B[36m";
        final String resetText = "\u001B[0m";
        final String line = "\u2550";
        boolean isSenior = userInterface.getBoolean("Vil du have senior? (ja/nej): ", "Venligst indtast 'ja' eller 'nej'");
        ArrayList<Member> team = userInterface.getController().getTeam(isSenior);
        int longestName = 0;
        int longestUserId = 0;
        for (Member member: team) {
            int nameLength = member.getName().length();
            if (nameLength > longestName) {
                longestName = nameLength;
            }
            int uidLength = member.getUid().length();
            if (uidLength > longestUserId) {
                longestUserId = uidLength;
            }
        }
        System.out.println(isSenior? "Seniorholdet: " : "Juniorholdet");
        for (Member member: team) {
            System.out.printf(
                    red + "Navn: %-" + longestName +"s " + resetText +
                            green + " Fødselsdag: %-8s " + resetText +
                            yellow + " Alder: %-3s" + resetText +
                            blue + " Aktiv: %-3s " + resetText +
                            cyan + " Bruger-ID: %-" + longestUserId +"s " + resetText +
                            purple + " Konkurrencesvømmer: %-3s " + resetText + "\n"
                    , member.getName(), member.getBirthday(), member.getAge(), member.getActive()? "Ja" : "Nej", member.getUid(), member.getCompetitiveStatus()? "Ja" : "Nej");
        }
        System.out.println();
    }

    public void showTop5Menu() {
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

    public void printResults(ArrayList<Result> results, String title) {
        final String red = "\u001B[31m";
        final String green = "\u001B[32m";
        final String yellow = "\u001B[33m";
        final String blue = "\u001B[34m";
        final String purple = "\u001B[35m";
        final String cyan = "\u001B[36m";
        final String resetText = "\u001B[0m";
        final String bold = "\033[0;1m";
        System.out.println(bold + title + resetText);
        int longestUserId = 0;
        int longestTime = 0;
        int longestCompetitionTitleLength = 0;
        int disciplineTitleLength = 0;
        if (results.size() > 0) {
            disciplineTitleLength = results.get(0).getDisciplineTitle().toString().length();
        }
        for (Result result: results) {
            if (result instanceof CompetitionResult) {

            }
            int userIdLength = result.getUserId().length();
            if (userIdLength > longestUserId) {
                longestUserId = userIdLength;
            }
            int timeLength = String.valueOf(result.getResultTime()).length();
            if (timeLength > longestTime) {
                longestTime = timeLength;
            }
            if (result instanceof CompetitionResult) {
                int competitionTitleLength = ((CompetitionResult) result).getCompetitionTitle().length();
                if (competitionTitleLength > longestCompetitionTitleLength) {
                    longestCompetitionTitleLength = competitionTitleLength;
                }
            }
        }
        for (Result result: results) {
            if (result instanceof CompetitionResult) {
                System.out.printf(
                        red + "BrugerId: %-" + longestUserId +"s " + resetText +
                                green + " Disciplin: %-" + disciplineTitleLength +"s " + resetText +
                                yellow + " Tid: %-" + longestTime +"s" + resetText +
                                blue + "  Dato: %-8s " + resetText +
                                cyan + " Placering: %-3s" + resetText +
                                purple + "Stævnenavn: %" + longestCompetitionTitleLength + "s " + resetText + "\n"
                        , result.getUserId(), result.getDisciplineTitle(), result.getResultTime(), result.getDate().toString(), ((CompetitionResult) result).getPlacement(), ((CompetitionResult) result).getCompetitionTitle());
            } else {
                System.out.printf(
                        red + "BrugerId: %-" + longestUserId +"s " + resetText +
                                green + " Disciplin: %-12s " + resetText +
                                yellow + " Tid: %-" + longestTime+2 +"s" + resetText +
                                blue + "  Dato: %-8s " + resetText + "\n"
                        , result.getUserId(), result.getDisciplineTitle(), result.getResultTime(), result.getDate().toString());
            }
        }
        System.out.println();
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

                controller.addCompetitionResult(disciplinetitle, timeResult, userId, date, competitionTitle, placement);
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