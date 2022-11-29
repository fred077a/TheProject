package Data;

import java.time.LocalDate;
import java.time.Period;

public class Member {
    private String fullName;
    private String birthday; //make sure it has a specific amount of characters fx. 010199-1234
    private boolean active;
    private boolean competitive;
    private String uid;
    private int previousPayment;
    //todo new class Subscription




    public Member(String fullName, String birthday, boolean active, boolean competitive, String uid, int previousPayment) {
        this.fullName = fullName;
        this.birthday = birthday;
        this.active = active;
        this.competitive = competitive;
        this.uid = uid;
        this.previousPayment = previousPayment;
    }

    public boolean isSenior() {
        return getAge() >= 18;
    }

    public int getPreviousPayment() {
        return this.previousPayment;
    }

    public int lateOnPayments() {
        int currentYear = LocalDate.now().getYear();
        return currentYear - this.previousPayment;
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

    public boolean getCompetitiveStatus() {
        return this.competitive;
    }

    public void setCompetitiveStatus(boolean competitive) {
        this.competitive = competitive;
    }

    public String getUid() {
        return this.uid;
    }

    public String competetiveStatusToString(){
        if (getCompetitiveStatus() == true) {
            return "ja";
        } else {
            return "nej";
        }
    }

    public String activeStatusToString(){
        if (getActive() == true) {
            return "ja";
        } else {
            return "nej";
        }
    }

    public double getSubscriptionAmount() {
        if (this.active) {
            int age = getAge();
            if (age < 18) {
                //juniors
                return 1000;
            } else {
                if (age > 60) {
                    //seniors over 60.
                    return 1600*0.75;
                } else {
                    //senior but not over 60.
                    return 1600;
                }
            }
        } else {
            //passive member
            return 500;
        }
    }






}
