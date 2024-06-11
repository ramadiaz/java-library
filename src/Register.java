import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Register {
    public static void member() {
        try {
            JSONTokener tokener   = new JSONTokener(new FileReader(".\\JSONfolder\\memberLoginData.json"));
            JSONArray   jsonArray = new JSONArray(tokener);
            Scanner     sc        = new Scanner(System.in);
            String      username;

            System.out.println("===============[MEMBER REGISTER MENU]===============");

            System.out.print("Enter name     : ");
            String name = sc.nextLine();

            do {
                System.out.print  ("Enter username : ");
                username = sc.nextLine();

                if (checkUsernameExists(jsonArray, username)) {
                    System.out.println(">>> Message: Username already exists!");
                }
            } while (checkUsernameExists(jsonArray, username));

            System.out.print("Enter password : ");
            String password = sc.nextLine();

            JSONObject newMember = new JSONObject();
            newMember.put("name", name);
            newMember.put("username", username);
            newMember.put("password", password);

            jsonArray.put(newMember);

            FileWriter fileWriter = new FileWriter(".\\JSONfolder\\memberLoginData.json");
            fileWriter.write(jsonArray.toString());
            fileWriter.close();

            System.out.println(">>> Message: Member registration successful!");

            System.out.println("Press enter to return to main menu... ");
            sc.nextLine();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public static void admin() {
        try {
            JSONTokener tokener   = new JSONTokener(new FileReader(".\\JSONfolder\\adminLoginData.json"));
            JSONArray   jsonArray = new JSONArray(tokener);
            String      username;

            System.out.println("================[ADMIN REGISTER MENU]===============");

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter name     : ");
            String name = sc.nextLine();
            System.out.print("Enter email    : ");
            String email = sc.nextLine();

            do {
                System.out.print  ("Enter username : ");
                username = sc.nextLine();

                if (checkUsernameExists(jsonArray, username)) {
                    System.out.println(">>> Message: Username already exists!");
                }
            } while (checkUsernameExists(jsonArray, username));

            System.out.print("Enter password : ");
            String password = sc.nextLine();

            JSONObject newAdmin = new JSONObject();
            newAdmin.put("name", name);
            newAdmin.put("email", email);
            newAdmin.put("username", username);
            newAdmin.put("password", password);

            jsonArray.put(newAdmin);

            FileWriter fileWriter = new FileWriter(".\\JSONfolder\\adminLoginData.json");
            fileWriter.write(jsonArray.toString());
            fileWriter.close();

            System.out.println(">>> Message: Admin registration successful.");

            System.out.println("Press enter to return to admin menu... ");
            sc.nextLine();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkUsernameExists(JSONArray jsonArray, String username) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            if (obj.getString("username").equals(username)) {
                return true; // Username already exists
            }
        }
        return false; // Username does not exist
    }

}
