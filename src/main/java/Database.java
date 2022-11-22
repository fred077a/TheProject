import java.util.ArrayList;

public class Database {
    private ArrayList<Member> members = new ArrayList<>();
    private ArrayList<CompititionResult> compititionResults = new ArrayList<>();

    public void addUser(Member member) {
        members.add(member);
    }

    // Getter & Setter memberArrayList
    public ArrayList<Member> getMemberList() {
        return this.members;
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
}
