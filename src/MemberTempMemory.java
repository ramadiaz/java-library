import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MemberTempMemory {
    private static List<JSONObject> bookList = new LinkedList<>();
    private static LinkedList<JSONObject> favoriteBooks = new LinkedList<>();

    public static void favoriteBooks() throws JSONException {
        System.out.println("===============[FAVORITE BOOK MENU]================");
        System.out.println("1. Add new favorite books");
        System.out.println("2. Show all favorite books");
        System.out.println("3. Return to member menu");
        System.out.print  ("Select the menu (1-3): ");

        Scanner sc = new Scanner(System.in);
        while (!sc.hasNextInt()) {
            System.out.println(">>> Message: That's not a number!");
            System.out.print  ("Select the menu (1-3): ");
            sc.next();
        }
        int menuSelect = sc.nextInt();
        sc.nextLine();

        if(menuSelect==1) {
            addFavorite();
        }
        else if(menuSelect==2) {
            printFavoriteBooks();
        }
        else if(menuSelect==3) {
            Menu.memberMenu();
        }
        else {
            System.out.println(">>> Message: Menu unavailable!");
        }

        System.out.println("Press enter to continue... ");
        sc.nextLine();
        favoriteBooks();
    }

    public static void addFavorite() throws JSONException {
        readJSONtoLinkedList();

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter book ID: ");
        while (!sc.hasNextInt()) {
            System.out.println(">>> Message: That's not a number!");
            System.out.print  ("Enter book ID: ");
            sc.next();
        }
        int bookCode = sc.nextInt();
        sc.nextLine();

        JSONObject book = findBookByCode(bookCode);
        if (book != null) {
            favoriteBooks.add(book);
            System.out.println(">>> Message: Book with ID " + bookCode + " added to favorite list successfully.");
        } else {
            System.out.println(">>> Message: Book with ID " + bookCode + " does not exist.");
        }
    }

    private static void printFavoriteBooks() throws JSONException {
        System.out.println("--------------------{RESULT}----------------------");
        for (JSONObject book : favoriteBooks) {
            String bookName = book.getString("bookName");
            String author = book.getString("author");
            int bookCode = book.getInt("bookCode");

            System.out.println("Book ID    : " + bookCode);
            System.out.println("Book title : " + bookName);
            System.out.println("Author     : " + author);
            System.out.println("--------------------------------------------------");
        }
        System.out.println(">>> Total: " + favoriteBooks.size() + " Books found.");
    }

    private static JSONObject findBookByCode(int bookCode) throws JSONException {
        for (JSONObject book : bookList) {
            if (book.getInt("bookCode") == bookCode) {
                return book;
            }
        }
        return null;
    }

    private static void readJSONtoLinkedList() {
        try {
            String jsonData = new String(Files.readAllBytes(Paths.get(".\\JSONfolder\\booksData.json")));
            JSONArray jsonArray = new JSONArray(jsonData);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject book = jsonArray.getJSONObject(i);
                bookList.add(book);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
