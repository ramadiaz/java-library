import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.util.Scanner;

public class BookSort {
    public static void showAll() {
        Scanner sc = new Scanner(System.in);

        System.out.println("================[VIEW ALL BOOKS MENU]===============");
        System.out.println("1. Sort by Title");
        System.out.println("2. Sort by ID");
        System.out.println("3. Sort by Type");
        System.out.println("4. Sort by Author");
        System.out.println("5. Sort by Release Year");
        System.out.println("6. Sort by Publisher");
        System.out.println("7. Return to previous menu");
        System.out.print  ("Select sort menu (1-7): ");

        while (!sc.hasNextInt()) {
            System.out.println(">>> Message: That's not a number!");
            System.out.print  ("Select sort menu (1-7): ");
            sc.next();
        }
        int menuSelect = sc.nextInt();
        sc.nextLine();

        if(menuSelect==1) {
            stringSort("bookName");
        }
        else if(menuSelect==2) {
            intSort   ("bookCode");
        }
        else if(menuSelect==3) {
            stringSort("bookType");
        }
        else if(menuSelect==4) {
            stringSort("author");
        }
        else if(menuSelect==5) {
            intSort   ("releaseYear");
        }
        else if(menuSelect==6) {
            stringSort("publisher");
        }
        else if(menuSelect==7) {
            
        }
        else {
            System.out.println(">>> Message: Menu unavailable!");
            showAll();
        }
        
        System.out.println("Press enter to return to previous menu... ");
        sc.nextLine();
        

    }
    public static void stringSort(String sortType) {
        try {
            // Step 1: Parse the JSON file
            JSONTokener tokener = new JSONTokener(new FileReader(".\\JSONfolder\\booksData.json"));
            JSONArray jsonArray = new JSONArray(tokener);

            // Step 2: Sort the JSON objects using bubble sort
            for (int i = 0; i < jsonArray.length() - 1; i++) {
                for (int j = 0; j < jsonArray.length() - i - 1; j++) {
                    JSONObject obj1 = jsonArray.getJSONObject(j);
                    JSONObject obj2 = jsonArray.getJSONObject(j + 1);
                    String objectName1 = null;
                    String objectName2 = null;

                    if(sortType.equals("bookName")) {
                        objectName1 = obj1.getString("bookName");
                        objectName2 = obj2.getString("bookName");
                    }
                    if(sortType.equals("bookType")) {
                        objectName1 = obj1.getString("bookType");
                        objectName2 = obj2.getString("bookType");
                    }
                    if(sortType.equals("author")) {
                        objectName1 = obj1.getString("author");
                        objectName2 = obj2.getString("author");
                    }
                    if(sortType.equals("publisher")) {
                        objectName1 = obj1.getString("publisher");
                        objectName2 = obj2.getString("publisher");
                    }
                    assert objectName2 != null;
                    if (objectName1.compareTo(objectName2) > 0) {
                        // Swap the objects
                        jsonArray.put(j, obj2);
                        jsonArray.put(j + 1, obj1);
                    }
                }
            }

            // Step 3: Display the sorted objects
            printResult(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void intSort(String sortType) {
        try {
            JSONTokener tokener = new JSONTokener(new FileReader(".\\JSONfolder\\booksData.json"));
            JSONArray jsonArray = new JSONArray(tokener);

            // Step 2: Sort the JSON objects using bubble sort
            for (int i = 0; i < jsonArray.length() - 1; i++) {
                for (int j = 0; j < jsonArray.length() - i - 1; j++) {
                    JSONObject obj1 = jsonArray.getJSONObject(j);
                    JSONObject obj2 = jsonArray.getJSONObject(j + 1);
                    int objectName1 = 0;
                    int objectName2 = 0;

                    if(sortType.equals("bookCode")) {
                        objectName1 = obj1.getInt("bookCode");
                        objectName2 = obj2.getInt("bookCode");
                    }
                    if(sortType.equals("releaseYear")) {
                        objectName1 = obj1.getInt("releaseYear");
                        objectName2 = obj2.getInt("releaseYear");
                    }

                    if (objectName1 > objectName2) {
                        // Swap the objects
                        jsonArray.put(j, obj2);
                        jsonArray.put(j + 1, obj1);
                    }
                }
            }

            // Step 3: Display the sorted objects
            printResult(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printResult(JSONArray jsonArray) throws JSONException {
        System.out.println("----------------------{RESULT}----------------------");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println("Book ID        : " +jsonObject.getInt   ("bookCode"));
            System.out.println("Book Title     : " +jsonObject.getString("bookName"));
            System.out.println("Book Type      : " +jsonObject.getString("bookType"));
            System.out.println("Author         : " +jsonObject.getString("author"));
            System.out.println("Release Year   : " +jsonObject.getInt   ("releaseYear"));
            System.out.println("Publisher      : " +jsonObject.getString("publisher"));
            System.out.println("Status         : " +jsonObject.getString("status"));
            if(jsonObject.getString("status").equals("borrowed")) {
                System.out.println("Borrowed Until : " + jsonObject.getString("borrowedUntil"));
            }
            System.out.println("----------------------------------------------------");
        }
        System.out.println(">>> Total: " + jsonArray.length() + " Books found.");
    }
}
