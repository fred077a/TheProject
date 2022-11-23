package Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    private File file = new File("data/members.csv");


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
                                attributes[4] //uid
                        )
                );
            }
        }
        return members;
    }


}
