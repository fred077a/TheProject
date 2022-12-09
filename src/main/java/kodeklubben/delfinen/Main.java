package kodeklubben.delfinen;
import kodeklubben.delfinen.ui.UserInterface;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        UserInterface ui = new UserInterface();
        ui.start();
    }
}
