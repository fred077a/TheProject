package Controller;
import Data.*;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {
    private Database database = new Database();
    private FileHandler fileHandler = new FileHandler();

    public void createUser(String fullName, String birthday, boolean active, boolean competitive, int previousPayment) throws FileNotFoundException {
        database.addUser(fullName, birthday, active, competitive, previousPayment);
        saveMemberData();
    }

    public void sortMembers(int choice) {
        database.sortMembers(choice);
    }

    public ArrayList<Member> searchMembers(int menuChoice, String search) {
        return database.searchMembers(menuChoice, search);
    }

    public void addResult(Enum disciplineTitle, double resultTime, String userId, LocalDate date) {
        database.addResult(disciplineTitle, resultTime, date, userId);
    }

    public void addResult(Enum disciplineTitle, double resultTime, String userId, LocalDate date, String competitionTitle, int placement) {
        database.addResult(disciplineTitle, resultTime, userId, date, competitionTitle, placement);
    }

    public ArrayList<Result> getTop5(Enum disciplineTitle, boolean isSenior, boolean isCompetition) {
        return database.getTop5(disciplineTitle, isSenior, isCompetition);
    }

    public boolean userExists(String userId) {
        return database.userExists(userId);
    }

    public ArrayList<Member> getMembers() {
        return this.database.getMemberList();
    }

    public void saveMemberData() throws FileNotFoundException {
        ArrayList<Member> members = database.getMemberList();
        fileHandler.saveMemberList(members);
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

    public void loadResults() throws FileNotFoundException {
        ArrayList<Result> results = fileHandler.loadResult();
        database.addResults(results);
    }

    public void saveResults() throws FileNotFoundException {
        ArrayList<Result> results = database.getResults();
        fileHandler.saveResult(results);
    }

    public ArrayList<Member> getTeam(boolean isSenior) {
        return database.getTeam(isSenior);
    }
}
