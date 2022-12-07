package kodeklubben.delfinen.data;

import java.time.LocalDate;

public class CompetitionResult extends Result {
    private String competitionTitle;
    private int placement;

    public CompetitionResult(String competitionTitle, int placement, Enum disciplineTitle, String userId, double resultTime, LocalDate date){
        super(disciplineTitle, resultTime, date, userId);
        this.competitionTitle = competitionTitle;
        this.placement = placement;
    }

    public String getCompetitionTitle() {
        return competitionTitle;
    }

    public int getPlacement() {
        return placement;
    }

    @Override
    public String toString() {
        return "Disciplintitel: '" + super.getDisciplineTitle() +
                "', Tid: " + super.getResultTime() +
                "', Dato: " + super.getDate() +
                "', Bruger-ID: '" + super.getUserId() +
                "', Placering: '" + this.placement +
                "', Stævnenavn: '" + this.competitionTitle + "'";
    }
}
