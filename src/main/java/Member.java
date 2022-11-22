import java.time.LocalDate;
import java.time.Period;

public class Member {
    private String fullName;
    private String cpr; //make sure it has a specific amount of characters fx. 010199-1234
    private boolean active;
    private boolean competitive;

    public Member(String fullName, String cpr, boolean active, boolean competitive) {
        this.fullName = fullName;
        this.cpr = cpr;
        this.active = active;
        this.competitive = competitive;
    }

    public String getName() {
        return this.fullName;
    }

    public void setName(String name) {
        this.fullName = name;
    }

    public String getCpr() {
        return this.cpr;
    }

    public int getAge() {
        //splits cpr, 010199-1475 -> 010199
        String cprDateOfBirth = this.cpr.split("-")[0];

        //gets part of strings and parses them into integers.
        int year = Integer.parseInt(cprDateOfBirth.substring(4,6));
        int month = Integer.parseInt(cprDateOfBirth.substring(2,4));
        int day = Integer.parseInt(cprDateOfBirth.substring(0,2));

        //gets difference in years between then and now. Then it returns the result.
        LocalDate dateOfBirth = LocalDate.of(year+1900, month, day);
        int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
        if (age < 100) {
            return age;
        } else {
            return age - 100;
        }

    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
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
