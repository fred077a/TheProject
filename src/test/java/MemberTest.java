import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MemberTest {
    @Test
    public void ageFromCpr() {
        String name = "Frederik";
        String cpr = "130898-1111";
        boolean active = false;
        boolean competitive = false;

        Member member = new Member(name, cpr, active, competitive, 0);
        assertEquals(24, member.getAge());
    }


}