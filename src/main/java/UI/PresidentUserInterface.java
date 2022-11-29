package UI;

import Controller.Controller;
import Data.Member;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PresidentUserInterface {
    private final UserInterface userInterface;
    private Scanner userInput;

    public PresidentUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
        this.userInput = this.userInterface.getUserInput();
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
            int menuChoice = userInterface.getIntInput("Indtast kommando (1-7): ", 1, 8, "Indtast venligst et tal mellem 1-9");

            switch (menuChoice) {
                case 1 -> addMember();
                case 2 -> printMembers();
                case 3 -> editMember();
                case 4 -> deleteMember();
                case 5 -> sortMembers();
                case 6 -> userInterface.searchMembers();
                case 7 -> userInterface.exitProgram();
                default -> System.out.println("Ugyldig kommando");
            }
        } while (true);
    }

    public void addMember() throws FileNotFoundException {
        //name
        String name = userInterface.getStringInput("Venligst indtast fulde navn: ", 0, 100, "Fejl, prøv igen.");

        //birthday
        String birthday = userInterface.getStringInput("Venligst indtast brugerens fødselsdag (24122022): ", 8, 9, "Indtast venligst dato i rigtigt format (ddMMyyyy)");

        //active member or not. Making sure the answer is suitable. Same for competitive member.
        boolean active = userInterface.getBoolean("Er brugeren aktiv? ('ja'/'nej'): ", "Venligst indtast 'ja' eller 'nej");

        //Competitive member or not.
        boolean competitive = userInterface.getBoolean("Venligst indsat om brugeren er konkurrencesvømmer ('ja'/'nej'): ", "Venligst indtast 'ja' eller 'nej");

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

        userInterface.getController().createUser(name, birthday, active, competitive, previousPayment);
    }

    public void printMembers() {
        ArrayList<Member> members = userInterface.getController().getMembers();
        for (Member member: members) {
            System.out.println(member);
        }
    }

    public void printMemberMenu() {
        Controller controller = userInterface.getController();
        for (int i = 0; i < controller.getMembers().size(); i++) {
            System.out.println(i + 1 + ": " + controller.getMembers().get(i));
        }
    }

    public void editMember() throws FileNotFoundException {
        printMemberMenu();
        System.out.print("Hvilket medlem skal redigeres: ");
        int memberNumber = userInput.nextInt();
        userInput.nextLine(); // scannerbug
        Controller controller = userInterface.getController();
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

    public void deleteMember() throws FileNotFoundException {
        System.out.print("Indtast brugerID på medlemmet der skal fjernes: ");
        String search = userInput.next();
        Member memberFound = userInterface.getController().getMemberFromUid(search);
        if (memberFound.getUid().equals("")) {
            System.out.println("Ingen medlemmer med bruger-ID " + search);
        } else {
            System.out.print("Er du sikker på at slette '" + memberFound.getName() + "'? ('ja' eller 'nej') ");
            do {
                String confirmationAnswer = userInput.next();
                if (confirmationAnswer.toLowerCase().equals("ja")) {
                    System.out.println(memberFound.getName() + " er blevet slettet fra systemet");
                    userInterface.getController().deleteMember(memberFound);
                    break;
                } else if (confirmationAnswer.toLowerCase().equals("nej")) {
                    break;
                } else {
                    System.out.println("Venligst svar 'ja' eller 'nej'");
                }
            } while (true);
        }
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

    public void sortMembers() {
        //Menu items
        System.out.println("\nDu kan sortere ud fra følgende:");
        System.out.println("1. Navn");
        System.out.println("2. Alder");
        System.out.println("3. Aktiv og passiv");
        System.out.println("4. Bruger-ID");
        System.out.println("5. Afslut sortering");
        int menuChoice = userInterface.getIntInput("Indtast kommando (1-5): ", 1, 6, "Indtast venligst et tal mellem 1-5");
        if (menuChoice != 5) {
            userInterface.getController().sortMembers(menuChoice);
            printMembers();
        }
    };
}