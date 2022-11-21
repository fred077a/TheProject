import java.util.ArrayList;

public class Database {
    ArrayList<Member> memberArrayList = new ArrayList<>();
    ArrayList<CompititionResult> compititionResultArrayList = new ArrayList<>();
    
    // Getter & Setter memberArrayList

    public ArrayList<Member> getMemberArrayList() {
        return memberArrayList;
    }

    public void setMemberArrayList(ArrayList<Member> memberArrayList) {
        this.memberArrayList = memberArrayList;
    }

    
    // Getter & Setter CompetitionResultArrayList
    
    public ArrayList<CompititionResult> getCompititionResultArrayList() {
        return compititionResultArrayList;
    }

    public void setCompititionResultArrayList(ArrayList<CompititionResult> compititionResultArrayList) {
        this.compititionResultArrayList = compititionResultArrayList;
    }
}
