package Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Database {
    private ArrayList<Member> members = new ArrayList<>();
    private ArrayList<CompititionResult> compititionResults = new ArrayList<>();
    private boolean changesMade = false;

    public void addUser(Member member) {
        this.members.add(member);
        setChangesMade();
    }

    public ArrayList<Member> getCompetitiveMembers() {
        ArrayList<Member> competitiveMembers = new ArrayList<>();
        for (Member member: members) {
            if (member.getCompetitiveStatus()) {
                competitiveMembers.add(member);
            }
        }
        return competitiveMembers;
    }

    //Todo add setChangesMade(); i edit user
    //Todo add setChangesMade(); i delete user


    // Getter & Setter memberArrayList
    public ArrayList<Member> getMemberList() {
        return this.members;
    }

    public int getLatestNameIdNumber(String newUserFullName) {
        String newUserFirstName = newUserFullName.split(" ")[0];
        int latestNameId = 0;
        for (Member member: members) {
            String fullName = member.getName().toLowerCase();
            String firstName = fullName.split(" ")[0];
            if (firstName.equals(newUserFirstName.toLowerCase())) {
                int uidLength = member.getUid().length();
                int nameId = Integer.parseInt(member.getUid().substring(uidLength-1, uidLength));
                if (nameId > latestNameId) {
                    latestNameId = nameId;
                }
            }
        }
        return latestNameId;
    }

    public ArrayList<Member> searchMembers(int menuChoice, String search) {
        ArrayList<Member> searchResult = new ArrayList<>();
        switch (menuChoice) {
            case 1 -> {
                for (Member member: this.members) {
                    String name = member.getName();
                    if (name.contains(search)) {
                        searchResult.add(member);
                    }
                }
                return searchResult;
            }
            case 2 -> {
                for (Member member: this.members) {
                    int age = member.getAge();
                    if (age == Integer.parseInt(search)) {
                        searchResult.add(member);
                    }
                }
                return searchResult;
            }
            case 3 -> {
                for (Member member: this.members) {
                    String uid = member.getUid();
                    if (uid.contains(search)) {
                        searchResult.add(member);
                    }
                }
                return searchResult;
            }
            case 4 -> {
                if (search.toLowerCase().equals("ja")) {
                    for (Member member: this.members) {
                        if (member.getActive()) {
                            searchResult.add(member);
                        }
                    }
                } else if (search.toLowerCase().equals("nej")) {
                    for (Member member: this.members) {
                        if (!member.getActive()) {
                            searchResult.add(member);
                        }
                    }
                }
                return searchResult;
            }
            case 5 -> {
                for (Member member: this.members) {
                    if (search.toLowerCase().equals("ja")) {
                        if (member.lateOnPayments() > 0) {
                            searchResult.add(member);
                        }
                    } else if (search.toLowerCase().equals("nej")) {
                        if (member.lateOnPayments() == 0) {
                            searchResult.add(member);
                        }
                    }
                }
                return searchResult;
            }
            default -> {
                return searchResult;
            }
        }
    }

    public void sortMembers(int choice) {
        Collections.sort(members, new Comparator<Member>() {
            @Override
            public int compare(Member m1, Member m2) {
                switch (choice) {
                    case 1 -> {
                        return String.valueOf(m1.getName()).compareTo(String.valueOf(m2.getName()));
                    }
                    case 2 -> {
                        return String.valueOf(m1.getAge()).compareTo(String.valueOf(m2.getAge()));
                    }
                    case 3 -> {
                        return Boolean.valueOf(m1.getActive()).compareTo(Boolean.valueOf(m2.getActive()));
                    }
                    case 4 -> {
                        return m1.getUid().compareTo(m2.getUid());
                    }
                    default -> {
                        return String.valueOf(m1.getName()).compareTo(String.valueOf(m2.getName()));
                    }
                }
            }
        });
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

    public Member getMemberFromSearch(String search) {
        Member result = new Member("", "", false, false, "", 0);
        for (Member member: this.members) {
            String uid = member.getUid();
            if (uid.equals(search)) {
                return member;
            }
        }
        return result;
    }

    public void deleteMember(Member deleteMember) {
        members.remove(deleteMember);
        setChangesMade();
    }
}
