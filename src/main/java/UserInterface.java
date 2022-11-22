import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private Controller controller = new Controller();
    private Scanner userInput = new Scanner(System.in);

    public void start() {
        controller.createUser("daniel jensen", "121290-1111", false, true);
        do {
            //Program infomation
            System.out.println("Velkommen til Delfinens medlemsdatabase");
            System.out.println("Du har nu følgende valgmuligheder");
            System.out.println("");

            //Formand
            System.out.println("1: Opret nyt medlem");
            System.out.println("2: Se liste af medlemmer");
            System.out.println("3: Redigere medlem");
            System.out.println("4: Slet medlem");
            System.out.println("5: Sortere medlemmer");
            System.out.println("6: Søge og sorter");
            System.out.println("7: Filtrere medlemmer");

            //kassere
            System.out.println("8: Induviduel Kontingent");
            System.out.println("9: Forventede kontingenter");
            System.out.println("10: Medlemmer i restance");

            //træner
            System.out.println("11: Top 5 Svømmere");
            System.out.println("12: Registrér resultat");

            //Exit
            System.out.println("13: Exit program");

            System.out.print("Vælg kommando: ");
            int menuChoice = userInput.nextInt();

            switch (menuChoice) {
                case 1: {
                    addMember();
                    break;
                }
                case 2: {
                    printMembers();
                    break;
                }
                case 13: {
                    //todo: gem liste af medlemmer i en CSV-fil
                    System.exit(0);
                }
                default: {

                }
            }
        } while (true);
    }

    public void printMembers() {
        ArrayList<Member> members = controller.getMembers();
        for (Member member: members) {
            System.out.println(member);
        }
    }

    public void addMember() {
        //name
        System.out.print("Venligst indtast fulde navn: ");
        String name = userInput.next();

        //cpr
        System.out.print("Venligst indtast brugerens CPR-nummer (112233-xxxx): ");
        String cpr;
        do {
            String inputCpr = userInput.next();
            boolean containsDash = inputCpr.substring(6,7).equals("-");
            boolean amountCharactersCorrect = inputCpr.length() == 11;
            if (containsDash && amountCharactersCorrect) {
                cpr = inputCpr;
                break;
            } else {
                System.out.println("Indtast det rigtige format (241290-1234) og husk ' - '");
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

        controller.createUser(name, cpr, active, competitive);
    }
}
