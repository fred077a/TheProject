import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MemberTest {
    @Test
    public void ageFromCpr() {
        String name = "Frederik";
        String cpr = "130898-1111";
        boolean active = false;
        String type = "Motionist";

        Member member = new Member(name, cpr, active, type);
        assertEquals(24, member.getAge());
    }


}