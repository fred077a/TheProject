package Data;

import java.time.LocalDate;

public class CompetitionResult extends Result {
    private String competitionTitle;
    private int placement;

    public CompetitionResult(String competitionTitle, int placement, Enum disciplineTitle, String userId, double resultTime, LocalDate date){
        super(disciplineTitle, resultTime, date, userId);
        this.competitionTitle = competitionTitle;
        this.placement = placement;
    }

}
