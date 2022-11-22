import org.junit.Test;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseTest {
    @Test
    public void testAdd() {
        UserInterface userInterface = new UserInterface();
        userInterface.addMember();
        assertEquals(24, 2);
    }

}