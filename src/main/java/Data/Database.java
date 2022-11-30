package Data;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Database {
    private ArrayList<Member> members = new ArrayList<>();
    private ArrayList<Result> results = new ArrayList<>();

    public void addUser(String fullName, String birthday, boolean active, boolean competitive, int previousPayment ) {
        int latestNameId = getLatestNameIdNumber(fullName);
        String newUserFirstName = fullName.split(" ")[0];
        String uid = newUserFirstName.toLowerCase() + (latestNameId + 1);
        Member member = new Member( fullName,  birthday,  active,  competitive,  uid,  previousPayment);

        this.members.add(member);
    }

    public boolean userExists(String userId) {
        for (Member member: members) {
            if (member.getUid().equals(userId.toLowerCase())) {
                return true;
            }
        }
        return false;
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

    public void addResults(ArrayList<Result> results) {
        for (Result result: results) {
            this.results.add(result);
        }
    }

    public ArrayList<Member> getMemberList() {
        return this.members;
    }

    public int findMember(String userId) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getUid().equals(userId)) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Result> getTop5(Enum disciplineTitle, boolean isSenior, boolean isCompetition) {
        ArrayList<Result> matchingResults = new ArrayList<Result>();
        //finds matches for discipline
        for (Result result: results) {
            String userId = result.getUserId();
            int index = findMember(userId);
            if (index != -1) {
                int age = members.get(index).getAge();
                boolean senior =  age >= 18;
                boolean disciplineMatch = result.getDisciplineTitle().equals(disciplineTitle);
                boolean competitionResult = result instanceof CompetitionResult;
                if (disciplineMatch && isSenior == senior && isCompetition == competitionResult) {
                    matchingResults.add(result);
                }
            }
        }
        //making top5 list
        ArrayList<Result> top5 = new ArrayList<>();

        //runs through the matches AKA all relevant results (same discipline)
        for (int i = 0; i < matchingResults.size(); i++) {
            Result result = matchingResults.get(i);
            if (includeMembers(result.getUserId(), top5)) {
                //if person from result is already in top 5:
                //getting result time and checks if this time is better.
                double resultTime = result.getResultTime();
                int index = newTimeBetter(resultTime, top5);
                if (index != -1) {
                    //result is better and replaces old
                    //System.out.println("Found" + top5.get(index));
                    top5.set(index, result);
                }
            } else {
                top5.add(matchingResults.get(i));
            }
        }

        //sorts the top 5
        Collections.sort(top5, new Comparator<Result>() {
            @Override
            public int compare(Result r1, Result r2) {
                return String.valueOf(r1.getResultTime()).compareTo(String.valueOf(r2.getResultTime()));
            }
        });

        return new ArrayList(top5.subList(0, top5.size() <= 5? top5.size() : 5));
    }

    public int newTimeBetter(double resultTime, ArrayList<Result> results) {
        for (int i = 0; i < results.size(); i++) {
            if (resultTime < results.get(i).getResultTime()) {
                return i;
            }
        }
        return -1;
    }

    public boolean includeMembers(String userID, ArrayList<Result> matchingResults) {
        for (Result result: matchingResults) {
            if (result.getUserId().equals(userID)) {
                return true;
            }
        }
        return false;
    }

    public void addTrainingResult(Enum disciplineTitle, double resultTime, LocalDate date, String userId) {
        Result result = new Result(disciplineTitle, resultTime, date, userId);
        results.add(result);
    }

    public void addCompetitionResult(
            Enum disciplineTitle,
            double resultTime,
            String userId,
            LocalDate date,
            String competitionTitle,
            int placement) {
        CompetitionResult result = new CompetitionResult(competitionTitle, placement, disciplineTitle, userId, resultTime, date);
        results.add(result);
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

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setCompititionResultArrayList(ArrayList<Result> competitionResultArrayList) {
        this.results = competitionResultArrayList;
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
    }

    public ArrayList<Member> getTeam(boolean isSenior) {
        ArrayList<Member> team = new ArrayList<>();
        for (Member member: members) {
            int age = member.getAge();
            boolean isCompetitive = member.getCompetitiveStatus();
            boolean seniorMember = age >= 18;
            if (seniorMember == isSenior && isCompetitive) {
                team.add(member);
            }
        }
        return team;
    }
}
