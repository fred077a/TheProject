import UI.AccountantUserInterface;
import UI.UserInterface;
import org.junit.Test;
import java.io.FileNotFoundException;

public class AccountantTest {
    @Test
    public void calculateAmount() throws FileNotFoundException {
        UserInterface userInterface = new UserInterface();
        AccountantUserInterface ui = new AccountantUserInterface(userInterface);
        userInterface.loadData();
        ui.totalSubscription();
    }
}