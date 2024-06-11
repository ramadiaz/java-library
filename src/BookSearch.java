import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookSearch {
    public static void stringSearch() {
        try {
            Scanner sc = new Scanner(System.in);
            JSONTokener tokener = new JSONTokener(new FileReader(".\\JSONfolder\\booksData.json"));
            JSONArray jsonArray = new JSONArray(tokener);
            int bookFound       = 0;

            System.out.println("====================[BOOK SEARCH]===================");
            System.out.print  ("Enter book title/ type/ author: ");
            String searchQuery = sc.nextLine().toLowerCase();

            List<JSONObject> matchingBooks = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String bookName = jsonObject.getString("bookName").toLowerCase();
                String bookType = jsonObject.getString("bookType").toLowerCase();
                String author   = jsonObject.getString("author").toLowerCase();
                if (bookName.contains(searchQuery) || bookType.contains(searchQuery) || author.contains(searchQuery)) {
                    matchingBooks.add(jsonObject);
                    bookFound++;
                }
            }

            printResult(matchingBooks);
            System.out.println(">>> Total: " + bookFound + " Books found.");
            System.out.println("Press enter to return to main menu...");
            sc.nextLine();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void searchID () {
        try {
            JSONTokener tokener = new JSONTokener(new FileReader(".\\JSONfolder\\booksData.json"));
            JSONArray jsonArray = new JSONArray(tokener);

            Scanner sc = new Scanner(System.in);
            System.out.println("==============[BOOK DETAILS INFORMATION]============");
            System.out.print  ("Enter book ID: ");
            int searchQuery = sc.nextInt();
            sc.nextLine();

            List<JSONObject> matchingBooks = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int bookCode = jsonObject.getInt("bookCode");
                if (bookCode==searchQuery) {
                    matchingBooks.add(jsonObject);
                }
            }

            printResult(matchingBooks);
            System.out.println("Press enter to return to main menu...");
            sc.nextLine();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printResult(List<JSONObject> matchingBooks) throws JSONException {
        System.out.println("----------------------{RESULT}----------------------");
        if (matchingBooks.isEmpty()) {
            System.out.println(">>> Message: No matching books found.");
        } else {
            for (JSONObject jsonObject : matchingBooks) {
                System.out.println("Book ID        : " +jsonObject.getInt   ("bookCode"));
                System.out.println("Book Title     : " +jsonObject.getString("bookName"));
                System.out.println("Book Type      : " +jsonObject.getString("bookType"));
                System.out.println("Author         : " +jsonObject.getString("author"));
                System.out.println("Release Year   : " +jsonObject.getInt   ("releaseYear"));
                System.out.println("Publisher      : " +jsonObject.getString("publisher"));
                System.out.println("Status         : " +jsonObject.getString("status"));
                if(jsonObject.getString("status").equals("borrowed")){
                    System.out.println("Borrowed Until : " +jsonObject.getString("borrowedUntil"));
                }
                System.out.println("----------------------------------------------------");
            }
        }
    }
}
