package kodeklubben.delfinen.controller;
import kodeklubben.delfinen.data.*;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {
    private Database database = new Database();
    private FileHandler fileHandler = new FileHandler();

    public void createUser(String fullName, String birthday, boolean active, boolean competitive, int previousPayment) {
        database.createUser(fullName, birthday, active, competitive, previousPayment);
        saveMemberData();
    }

    public ArrayList<Result> getResults() {
        return database.getResults();
    }

    public int findMemberIndex(String userId) {
        return database.findMemberIndex(userId);
    }

    public boolean listIncludesMembers(String userId, ArrayList<Result> list) {
        return database.listIncludesMembers(userId, list);
    }

    public int searchBetterResult(double resultTime, ArrayList<Result> results) {
        return database.searchBetterResult(resultTime, results);
    }

    public void sortMembers(int choice) {
        database.sortMembers(choice);
    }

    public ArrayList<Member> searchMembers(int menuChoice, String search) {
        return database.searchMembers(menuChoice, search);
    }

    public void addTrainingResult(Enum disciplineTitle, double resultTime, String userId, LocalDate date) {
        database.addTrainingResult(disciplineTitle, resultTime, date, userId);
    }

    public void addCompetitionResult(Enum disciplineTitle, double resultTime, String userId, LocalDate date, String competitionTitle, int placement) {
        database.addCompetitionResult(disciplineTitle, resultTime, userId, date, competitionTitle, placement);
    }

    public boolean userExists(String userId) {
        return database.userExists(userId);
    }

    public ArrayList<Member> getMembers() {
        return this.database.getMemberList();
    }

    public void saveMemberData() {
        ArrayList<Member> members = database.getMemberList();
        fileHandler.saveMemberList(members);
    }

    public Member getMemberFromUid(String uid) {
        return database.getMemberFromSearch(uid);
    }

    public ArrayList<Member> getCompetitiveMembers() {
        return database.getCompetitiveMembers();
    }

    public void loadMemberData() {
        ArrayList<Member> members;
        try {
            members = fileHandler.loadMemberList();
            database.setMemberArrayList(members);
        } catch (Exception exception) {
            System.out.println("Hov, der skete en fejl");
            System.out.println("En fil kunne ikke læses (medlemsdatabase)");
        };
    }

    public void deleteMember(Member deleteMember){
        database.deleteMember(deleteMember);
        saveMemberData();
    }

    public void loadResults() {
        ArrayList<Result> results = new ArrayList<>();
        try {
            results = fileHandler.loadResult();
            database.addResults(results);
        } catch (Exception exception) {
            System.out.println("Hov, der skete en fejl");
            System.out.println("En fil kunne ikke læses (svømmeresultater)");
        }
    }

    public void saveResults() {
        ArrayList<Result> results = database.getResults();
        fileHandler.saveResult(results);
    }

    public ArrayList<Member> getTeam(boolean isSenior) {
        return database.getTeam(isSenior);
    }
}
