package Controller;

import Data.Database;
import Data.FileHandler;
import Data.Member;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Controller {
    private Database database = new Database();
    private FileHandler fileHandler = new FileHandler();

    public void createUser(String fullName, String birthday, boolean active, boolean competitive, int previousPayment) throws FileNotFoundException {
        int latestNameId = database.getLatestNameIdNumber(fullName);
        database.addUser(new Member(fullName, birthday, active, competitive, latestNameId, previousPayment));
        saveMemberData();
    }

    public void sortMembers(int choice) {
        database.sortMembers(choice);
    }

    public ArrayList<Member> searchMembers(int menuChoice, String search) {
        return database.searchMembers(menuChoice, search);
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

    public Member getMemberFromUid(String uid) {
        return database.getMemberFromSearch(uid);
    }

    public ArrayList<Member> getCompetitiveMembers() {
        return database.getCompetitiveMembers();
    }

    public void loadMemberData() throws FileNotFoundException {
        ArrayList<Member> members = fileHandler.loadMemberList();
        database.setMemberArrayList(members);
    }


    public void deleteMember(Member deleteMember) throws FileNotFoundException {
        database.deleteMember(deleteMember);
        saveMemberData();
    }



}
