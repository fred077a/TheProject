import java.util.ArrayList;

public class Controller {
    private Database database = new Database();
    private FileHandler fileHandler = new FileHandler();

    public void createUser(String fullName, String cpr, boolean active, boolean competitive) {
        database.addUser(new Member(fullName, cpr, active, competitive));
    }

    public ArrayList<Member> getMembers() {
        return this.database.getMemberList();
    }




}
