import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.util.Scanner;

public class Login {
    public static void memberLogin() {
        try {
            // Step 1: Parse the JSON file
            JSONTokener tokener = new JSONTokener(new FileReader(".\\JSONfolder\\memberLoginData.json"));
            JSONArray jsonArray = new JSONArray(tokener);

            // Step 2: Prompt the user for username and password
            Scanner sc = new Scanner(System.in);

            System.out.println("================[MEMBER LOGIN MENU]===============");
            System.out.print  ("Enter username: ");
            String username = sc.nextLine();
            System.out.print  ("Enter password: ");
            String password = sc.nextLine();

            // Step 3: Check if the entered credentials match any account
            boolean isLoginSuccessful = false;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject account = jsonArray.getJSONObject(i);
                String storedUsername = account.getString("username");
                String storedPassword = account.getString("password");

                if (username.equals(storedUsername) && password.equals(storedPassword)) {
                    String name = account.getString("name");
                    System.out.println("Login successful. Welcome, " + name + "!");
                    isLoginSuccessful = true;
                    Menu.memberMenu();
                    break;
                }
            }

            if (!isLoginSuccessful) {
                System.out.println("Invalid username or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void adminLogin() {
        try {
            // Step 1: Parse the JSON file
            JSONTokener tokener = new JSONTokener(new FileReader(".\\JSONfolder\\adminLoginData.json"));
            JSONArray jsonArray = new JSONArray(tokener);

            // Step 2: Prompt the user for username and password
            Scanner sc = new Scanner(System.in);

            System.out.println("================[ADMIN LOGIN MENU]===============");
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            System.out.print("Enter password: ");
            String password = sc.nextLine();

            // Step 3: Check if the entered credentials match any account
            boolean isLoginSuccessful = false;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject account = jsonArray.getJSONObject(i);
                String storedUsername = account.getString("username");
                String storedPassword = account.getString("password");

                if (username.equals(storedUsername) && password.equals(storedPassword)) {
                    String name = account.getString("name");
                    System.out.println("Login successful. Welcome, " + name + "!");
                    isLoginSuccessful = true;
                    Menu.adminMenu();
                    break;
                }
            }

            if (!isLoginSuccessful) {
                System.out.println("Invalid username or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
