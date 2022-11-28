import Controller.Controller;
import Data.CompetitionResult;
import Data.Result;
import UI.DisciplineTitles;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerTest {

    @Test
    public void testAddResult() throws FileNotFoundException {
        Controller controller = new Controller();
        Enum disciplineTitle = DisciplineTitles.BACKCRAWL;
        double resultTime = 20.2;
        String userId = "frederik1";
        LocalDate date = LocalDate.of(2020, 10, 20);
        int placement = 1;
        String competitionTitle = "Cup";
        controller.addResult(disciplineTitle, resultTime, userId, date, competitionTitle, placement);
        ArrayList<Result> results = controller.getTop5(disciplineTitle);
        for (Result result: results) {
            if (result instanceof CompetitionResult) {
                System.out.println("yay");
            } else {
                System.out.println("NAY!!");
            }
        }
        controller.saveResults();
    }

}