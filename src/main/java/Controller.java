import java.util.ArrayList;

public class Controller {
    private Database database = new Database();
    private FileHandler fileHandler = new FileHandler();

    public void createUser(String fullName, String birthday, boolean active, boolean competitive) throws FileNotFoundException {
        int nameMatches = database.getNameMatches(fullName);
        database.addUser(new Member(fullName, birthday, active, competitive, nameMatches));
        saveMemberData();
    }



    public ArrayList<Member> getMembers() {
        return this.database.getMemberList();
    }

    public void saveMemberData() throws FileNotFoundException {
        if (database.getChangesMade()) {
            ArrayList<Member> members = database.getMemberList();
            fileHandler.saveMemberList(members);
        }
    }




}
