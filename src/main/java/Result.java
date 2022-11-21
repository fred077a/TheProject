import java.time.LocalDate;

public class Result {
    protected String disciplineTitle;
    protected double resultTime;
    protected LocalDate date;

    public Result(String disciplineTitle, double resultTime, LocalDate date){
        this.disciplineTitle = disciplineTitle;
        this.resultTime = resultTime;
        this.date = date;
    }

}
