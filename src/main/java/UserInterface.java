import java.util.Scanner;

public class UserInterface {
    private Controller controller = new Controller();
    private Scanner brugerInput = new Scanner(System.in);

    public void start() {

        //Program infomation
        System.out.println("Velkommen til Delfinens medlemsdatabase");
        System.out.println("Du har nu følgende valgmuligheder");
        System.out.println("");

        //Formand
        System.out.println("1: Opret nyt medlem");
        System.out.println("2: Redigere medlem");
        System.out.println("3: Slet medlem");
        System.out.println("4: Sortere medlemmer");
        System.out.println("5: Søge og sorter");
        System.out.println("7: Filtrere medlemmer");

        //kassere
        System.out.println("7: Induviduel Kontingent");
        System.out.println("8: Forventede kontingenter");
        System.out.println("9: Medlemmer i restance");

        //træner
        System.out.println("10: Top 5 Svømmere");
        System.out.println("11: Registrér resultat");

        int menuChoice = brugerInput.nextInt();

        switch (menuChoice) {

            case 1: {
                //createNewMember();
                break;
            }
            case 2: {
                //editMember();
                break;
            }
            case 3: {
                //deleteMember();
                break;
            }
            case 4: {
                //sortMember();
                break;
            }
            case 5: {
                //searchAndSortMember();
                break;
            }
            case 6: {
                //filterMember();
                break;
            }
            case 7: {
                //individualContingent();
                break;
            }
            case 8: {
                //expectedContingent();
                break;
            }
            case 9: {
                //memberInArrear();
                break;
            }
            case 10: {
                //top5();
                break;
            }
            case 11: {
                //addResult();
                break;
            }

        }




    }
}
