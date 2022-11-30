package Data;

import java.time.LocalDate;

public class Result {
    private Enum disciplineTitle;
    private double resultTime;
    private LocalDate date;
    private String userId;

    public Result(Enum disciplineTitle, double resultTime, LocalDate date, String userId){
        this.disciplineTitle = disciplineTitle;
        this.resultTime = resultTime;
        this.date = date;
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Enum getDisciplineTitle() {
        return this.disciplineTitle;
    }

    public double getResultTime() {
        return this.resultTime;
    }

    @Override
    public String toString() {
        return "Disciplintitel: " + disciplineTitle +
                ", Tid: " + resultTime +
                ", Dato: " + date +
                ", Bruger-ID: '" + userId + "'";
    }
}
