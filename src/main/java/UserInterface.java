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
            System.out.print("Indtast venligst hvem du logger ind som (1-3): ");
            int loginChoice = userInput.nextInt();
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

    public void presidentMenu() throws FileNotFoundException {
        do {
            System.out.println("");
            System.out.println("Du er logget ind som formand.");
            System.out.println("Du har nu følgende valgmuligheder");
            System.out.println("");

            //menu
            System.out.println("1: Opret nyt medlem");
            System.out.println("2: Se liste af medlemmer");
            System.out.println("3: Redigere medlem");
            System.out.println("4: Slet et medlem");
            System.out.println("5: Sortér medlemmer");
            System.out.println("6: Filtrér medlemmer");
            System.out.println("7: Søg blandt medlemmer");
            System.out.println("8: Gem og afslut");
            int menuChoice = getMenuChoice("(1-8)");

            switch (menuChoice) {
                case 1 -> addMember();
                case 2 -> printMembers();
                case 3 -> editMember();
                case 4 -> deleteMember();
                //todo: mangler 5, 6 & 7.
                case 8 -> exitProgram();
                default -> System.out.println("Ugyldig kommando");
            }
        } while (true);
    }

    public void trainerMenu() throws FileNotFoundException {
        do {
            System.out.println("Du er logget ind som træner.");
            System.out.println("Du har nu følgende valgmuligheder");
            System.out.println("");

            //menu
            System.out.println("1: Se liste af konkurrencesvømmere");
            System.out.println("2: Se liste af top 5 svømmere");
            System.out.println("3: Søg");
            System.out.println("4: Filtrér medlemmer");
            System.out.println("5: Registrér resultat");
            System.out.println("6: Gem og afslut");
            int menuChoice = getMenuChoice("(1-6)");

            switch (menuChoice) {
                case 6 -> exitProgram();
                default -> System.out.println("Ugyldig kommando");
            }
        } while (true);
    }

    public void accountantMenu() throws FileNotFoundException {
        do {
            System.out.println("Du er logget ind som træner.");
            System.out.println("Du har nu følgende valgmuligheder");
            System.out.println("");

            //menu
            System.out.println("1: Se liste af medlemmer");
            System.out.println("2: Søg efter bruger"); //from perspective of accountant - also shows payment amount
            System.out.println("3: Kontingentestimat");
            System.out.println("4: Se medlemmer i restance");
            System.out.println("5: Gem og afslut");
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

        controller.createUser(name, cpr, active, competitive);
    }

    public void editMember() throws FileNotFoundException {
            for (int i = 0; i < controller.getMembers().size(); i++) {
                System.out.println(i + 1 + ": " + controller.getMembers().get(i));
            }
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
                        String newBirthday = userInput.nextLine();
                        memberToBeEdited.setBirthday(newBirthday);
                        break;
                    }
                    case 3: {
                        System.out.print("Du vil skifte " + memberToBeEdited.getActive() + " til: true/false");
                        Boolean newActiveStatus = userInput.nextBoolean();
                        memberToBeEdited.setActive(newActiveStatus);
                        break;
                    }
                    case 4: {
                        System.out.print("Du vil skifte " + memberToBeEdited.getCompetitiveStatus() + " til: true/false");
                        Boolean newCompetetiveStatus = userInput.nextBoolean();
                        memberToBeEdited.setCompetitiveStatus(newCompetetiveStatus);
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

    public void deleteMember() throws FileNotFoundException {
        ArrayList<Member> members = controller.getMembers();
        //menu
        for (int i = 0; i < members.size(); i++) {
            System.out.println(i + 1 + ": " + controller.getMembers().get(i));
        }
        do {
            try {
                int memberNumber;
                do {
                    try {
                        System.out.println("Indtast nummeret på det medlem som skal slettes:");
                        System.out.println("Tast 0 for at annullere");
                        memberNumber = userInput.nextInt();
                        break;
                    } catch (Exception exception) {
                        System.out.println("Det skal være et tal.");
                    }
                } while (true);
                //userInput.nextLine(); // scannerbugfix
                if (memberNumber == 0) {
                    break;
                }
                Member member = members.get(memberNumber-1);
                System.out.println(member.getUid() + " er blevet fjernet fra databasen");
                controller.deleteMember(member);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Der skete en fejl, indtast venligst et nummer fra listen");
                break;
            }
        } while (true);
        controller.saveMemberData();
    }
}
