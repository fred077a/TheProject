import java.util.ArrayList;

public class Database {
    private ArrayList<Member> members = new ArrayList<>();
    private ArrayList<CompititionResult> compititionResults = new ArrayList<>();
    private boolean changesMade = false;

    public void addUser(Member member) {
        this.members.add(member);
        setChangesMade();
    }

    //Todo add setChangesMade(); i edit user
    //Todo add setChangesMade(); i delete user


    // Getter & Setter memberArrayList
    public ArrayList<Member> getMemberList() {
        return this.members;
    }

    public int getNameMatches(String newUserFullName) {
        String newUserFirstName = newUserFullName.split(" ")[0];
        int matches = 0;
        for (Member member: members) {
            String fullName = member.getName().toLowerCase();
            String firstName = fullName.split(" ")[0];
            if (firstName.equals(newUserFirstName.toLowerCase())) {
                matches++;
            }
        }
        return matches;
    }

    public void setMemberArrayList(ArrayList<Member> memberArrayList) {
        this.members = memberArrayList;
    }

    

    public ArrayList<CompititionResult> getCompititionResultArrayList() {
        return compititionResults;
    }

    public void setCompititionResultArrayList(ArrayList<CompititionResult> compititionResultArrayList) {
        this.compititionResults = compititionResultArrayList;
    }

    public boolean getChangesMade() {
        return changesMade;
    }

    public void setChangesMade() {
        this.changesMade = true;
    }

    public void deleteMember(Member deleteMember) {
        members.remove(deleteMember);
        setChangesMade();
    }
}
