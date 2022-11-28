package UI;

import java.io.FileNotFoundException;

public class TrainerUserInterface {
    private final UserInterface userInterface;

    public TrainerUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
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
                case 1 -> userInterface.getCompetitiveMembers();
                case 2 -> userInterface.addResult();
                case 3 -> userInterface.getTop5();
                case 4 -> userInterface.searchMembers();
                //case 5 ->
                case 6 -> userInterface.exitProgram();
                default -> System.out.println("Ugyldig kommando");
            }
        } while (true);
    }
}