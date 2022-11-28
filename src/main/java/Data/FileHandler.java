package Data;

import UI.DisciplineTitles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    private File file = new File("data/members.csv");
    private File file2 = new File("data/results.csv");


    public void saveMemberList(ArrayList<Member> members) throws FileNotFoundException {
        PrintStream output = new PrintStream(this.file);
        for (Member member: members) {
            output.print(member.getName());
            output.print(";");
            output.print(member.getBirthday());
            output.print(";");
            output.print(member.getActive());
            output.print(";");
            output.print(member.getCompetitiveStatus());
            output.print(";");
            output.print(member.getUid());
            output.print(";");
            output.print(member.getPreviousPayment());
            output.println();
        }
        output.flush();
        output.close();
    }


    public ArrayList<Member> loadMemberList() throws FileNotFoundException {
        Scanner scanner = new Scanner(this.file);
        String data = "-nothing yet-";
        ArrayList<Member> members = new ArrayList<>();
        while(!data.isBlank() && scanner.hasNextLine()) {
            data = scanner.nextLine();
            String[] lines = data.split("\\n");
            for (int i = 0; i < lines.length; i++) {
                String[] attributes = lines[i].split(";");
                members.add(
                        new Member(
                                attributes[0], //full name
                                attributes[1], //birthday
                                Boolean.parseBoolean(attributes[2]), //active
                                Boolean.parseBoolean(attributes[3]), //competitive
                                attributes[4], //uid
                                Integer.parseInt(attributes[5]) //previousPayment
                        )
                );
            }
        }
        return members;
    }

    public void saveResult(ArrayList<Result> results) throws FileNotFoundException {
        PrintStream output = new PrintStream(this.file2);
        for (Result result: results) {
            if (result instanceof CompetitionResult) {
                output.print(((CompetitionResult) result).getCompetitionTitle());
                output.print(";");
                output.print(((CompetitionResult) result).getPlacement());
                output.print(";");
                output.print(result.getDisciplineTitle());
                output.print(";");
                output.print(result.getUserId());
                output.print(";");
                output.print(result.getResultTime());
                output.print(";");
                output.println(result.getDate());
            } else {
                output.print(result.getDisciplineTitle());
                output.print(";");
                output.print(result.getUserId());
                output.print(";");
                output.print(result.getResultTime());
                output.print(";");
                output.println(result.getDate());
            }
        }
        output.flush();
        output.close();
    }

    public ArrayList<Result> loadResult() throws FileNotFoundException {
        Scanner scanner = new Scanner(this.file2);
        String data = "-nothing yet-";
        ArrayList<Result> results = new ArrayList<>();
        while(!data.isBlank() && scanner.hasNextLine()) {
            data = scanner.nextLine();
            String[] lines = data.split("\\n");
            for (int i = 0; i < lines.length; i++) {
                String[] attributes = lines[i].split(";");
                if (attributes.length == 4) {
                    results.add(
                            new Result(
                                    DisciplineTitles.valueOf(attributes[0]), // disciplineTitle
                                    Double.parseDouble(attributes[2]), // resultTime
                                    LocalDate.parse(attributes[3]), // date
                                    attributes[1] // userId
                            )
                    );
                } else if (attributes.length == 6) {
                    results.add( //String competitionTitle, int placement, Enum disciplineTitle, String userId, double resultTime, LocalDate date
                            new CompetitionResult(
                                    attributes[0], //competitionTitle
                                    Integer.parseInt(attributes[1]), //placement
                                    DisciplineTitles.valueOf(attributes[2]),// disciplineTitle
                                    attributes[3], //user id
                                    Double.parseDouble(attributes[4]), // resultTime
                                    LocalDate.parse(attributes[5]) // date
                            )
                    );
                } else {
                    System.out.println("nothing");
                }
            }
        }
        return results;
    }
}
