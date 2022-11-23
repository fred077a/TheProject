import java.time.LocalDate;
import java.time.Period;

public class Member {
    private String fullName;
    private String birthday; //make sure it has a specific amount of characters fx. 010199-1234
    private boolean active;
    private boolean competitive;
    private String uid;
    //TODO: restance system

    public Member(String fullName, String birthday, boolean active, boolean competitive, int nameMatches) {
        this.fullName = fullName;
        this.birthday = birthday;
        this.active = active;
        this.competitive = competitive;
        String newUserFirstName = fullName.split(" ")[0];
        this.uid = newUserFirstName.toLowerCase() + (nameMatches + 1);
    }

    public Member(String fullName, String birthday, boolean active, boolean competitive, String uid) {
        this.fullName = fullName;
        this.birthday = birthday;
        this.active = active;
        this.competitive = competitive;
        this.uid = uid;
    }

    public String getName() {
        return this.fullName;
    }

    @Override
    public String toString() {
        return "Navn: '" + fullName + '\'' +
                ", Fødselsdag: '" + birthday + '\'' +
                ", Alder: '" + getAge() + '\'' +
                ", Aktivt medlem: " + (active? "ja" : "nej") +
                ", Bruger-ID: " + uid +
                ", Konkurrencesvømmer: " + (competitive? "ja" : "nej");
    }

    public void setName(String name) {
        this.fullName = name;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public int getAge() {
        //gets part of strings and parses them into integers.
        int year = Integer.parseInt(birthday.substring(4,8));
        int month = Integer.parseInt(birthday.substring(2,4));
        int day = Integer.parseInt(birthday.substring(0,2));

        //gets difference in years between then and now. Then it returns the result.
        LocalDate dateOfBirth = LocalDate.of(year, month, day);
        int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
        return age;
    }

    public void setBirthday(String cpr) {
        this.birthday = cpr;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getType() {
        return this.competitive;
    }

    public void setType(boolean competitive) {
        this.competitive = competitive;
    }


}
