import kodeklubben.delfinen.ui.AccountantUserInterface;
import kodeklubben.delfinen.ui.UserInterface;
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