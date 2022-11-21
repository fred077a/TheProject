import java.time.LocalDate;

public class CompititionResult extends Result {
    protected String competitionTitle;
    protected int placement;

    public CompititionResult(String competitionTitle, int placement, String disciplineTitle, double resultTime, LocalDate date){
        super(disciplineTitle, resultTime, date);
        this.competitionTitle = competitionTitle;
        this.placement = placement;


    }


}
