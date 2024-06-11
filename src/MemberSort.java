import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.util.Scanner;

public class MemberSort {
    public static void showAll() throws JSONException {
        Scanner sc = new Scanner(System.in);
        System.out.println("================[VIEW ALL MEMBER MENU]==============");
        System.out.println("1. Sort by Username");
        System.out.println("2. Sort by Member Name");
        System.out.println("3. Return to admin menu");
        System.out.print  ("Select sort menu (1-3): ");

        while (!sc.hasNextInt()) {
            System.out.println(">>> Message: That's not a number!");
            System.out.print  ("Select sort menu (1-3): ");
            sc.next();
        }
        int menuSelect = sc.nextInt();
        sc.nextLine();

        if(menuSelect==1) {
            stringSort("username");
        }
        else if(menuSelect==2) {
            stringSort("name");
        }
        else if(menuSelect==3) {
            Menu.adminMenu();
        }
        else {
            System.out.println(">>> Message: Menu unavailable");
            showAll();
        }
        System.out.println("Press enter to admin menu... ");
        sc.nextLine();
    }

    public static void stringSort(String sortType) {
        try {
            // Step 1: Parse the JSON file
            JSONTokener tokener = new JSONTokener(new FileReader(".\\JSONfolder\\memberLoginData.json"));
            JSONArray jsonArray = new JSONArray(tokener);

            // Step 2: Sort the JSON objects using bubble sort
            for (int i = 0; i < jsonArray.length() - 1; i++) {
                for (int j = 0; j < jsonArray.length() - i - 1; j++) {
                    JSONObject obj1 = jsonArray.getJSONObject(j);
                    JSONObject obj2 = jsonArray.getJSONObject(j + 1);
                    String objectName1 = null;
                    String objectName2 = null;

                    if(sortType.equals("username")) {
                        objectName1 = obj1.getString("username");
                        objectName2 = obj2.getString("username");
                    }
                    if(sortType.equals("name")) {
                        objectName1 = obj1.getString("name");
                        objectName2 = obj2.getString("name");
                    }
                    assert objectName2 != null;
                    if (objectName1.compareToIgnoreCase(objectName2) > 0) {
                        // Swap the objects
                        jsonArray.put(j, obj2);
                        jsonArray.put(j + 1, obj1);
                    }
                }
            }
            System.out.println("----------------------{RESULT}----------------------");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                System.out.println("Name     : " +jsonObject.getString("name"));
                System.out.println("Username : " +jsonObject.getString("username"));
                System.out.println("Password : " +jsonObject.getString("password"));
                System.out.println("----------------------------------------------------");
            }
            System.out.println("Total: " + jsonArray.length() + " Members found.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
