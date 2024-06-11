import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class MemberManagement {
    public static void delete() throws JSONException {
        System.out.println("===================[MEMBER DELETE]==================");
        System.out.println("Enter Member username (Enter 0 for return to admin menu)");
        System.out.print  ("Username: ");

        Scanner sc = new Scanner(System.in);
        String username = sc.nextLine();

        if(username.equals("0")) Menu.adminMenu();

        JSONArray jsonArray = readJSONFile();

        boolean memberFound = false;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject member = jsonArray.getJSONObject(i);
            if (username.equals(member.getString("username"))) {
                System.out.println("Member name: " + member.getString("name"));
                System.out.print  ("Continue deleting this member? (Y/N): ");
                String confirm = sc.nextLine();

                if(confirm.equalsIgnoreCase("y")){
                    jsonArray.remove(i);
                    memberFound = true;

                    System.out.println(">>> Message: Member with username " + username + " deleted successfully");
                    writeJSONFile(jsonArray);
                    break;
                } else {
                    delete();
                }
            }
        }

        if(!memberFound) System.out.println(">>> Message: Member with username " + username + " does not exist.");
        System.out.println("Press enter to return to admin menu... ");
        sc.nextLine();


    }

    private static JSONArray readJSONFile() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(".\\JSONfolder\\memberLoginData.json")));
            return new JSONArray(content);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private static void writeJSONFile(JSONArray jsonArray) {
        try (FileWriter fileWriter = new FileWriter(".\\JSONfolder\\memberLoginData.json")) {
            fileWriter.write(jsonArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
