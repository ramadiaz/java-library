import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Scanner;

public class BookUpdate {

    public static void borrow() throws JSONException {
        System.out.println("===================[BORROW BOOK MENU]==================");
        System.out.println("Enter the book ID (enter 0 for return to member menu)");
        System.out.print  ("Book ID: ");

        Scanner sc = new Scanner(System.in);
        while (!sc.hasNextInt()) {
            System.out.println(">>> Message: That's not a number!");
            System.out.print  ("Book ID: ");
            sc.next();
        }
        int bookId = sc.nextInt();
        sc.nextLine();

        if(bookId==0) Menu.memberMenu();

        JSONArray jsonArray = readJSONFile();

        boolean bookFound = false;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject book = jsonArray.getJSONObject(i);
            if (book.getInt("bookCode") == bookId) {
                String status = book.getString("status");
                if (status.equals("available")) {
                    System.out.println("Book Title   : " + book.getString("bookName"));
                    System.out.println("Author       : " + book.getString("author"));
                    System.out.println("Release Year : " + book.getInt   ("releaseYear"));
                    System.out.print  ("Continue borrowing this book? (Y/N): ");
                    String confirm = sc.nextLine();

                    if(confirm.equalsIgnoreCase("y")){
                        book.put("status", "borrowed");

                        LocalDate borrowedUntil = LocalDate.now().plusWeeks(1);
                        book.put("borrowedUntil", borrowedUntil.toString());

                        writeJSONFile(jsonArray);
                        System.out.println(">>> Message: Book with ID " + bookId + " has been successfully borrowed.");
                        System.out.println("             Please pick up the book at the borrowing counter.");
                        System.out.println("             Please return the book before " + borrowedUntil + ".");
                    } else {
                        borrow();
                    }

                } else {
                    String borrowedExp = book.getString("borrowedUntil");

                    System.out.println(">>> Message: Book with ID " + bookId + " is not available for borrowing.");
                    System.out.println("             This book borrowed until " + borrowedExp);
                }

                bookFound = true;
                break;
            }
        }

        if (!bookFound) System.out.println(">>> Message: Book with ID " + bookId + " does not exist.");
        System.out.println("Press enter to return to member menu... ");
        sc.nextLine();
    }

    public static void returnBook() throws JSONException {
        Scanner sc = new Scanner(System.in);
        System.out.println("===================[RETURN BOOK MENU]==================");
        System.out.println("Enter the book ID (enter 0 for return to member menu)");
        System.out.print  ("Book ID: ");
        while (!sc.hasNextInt()) {
            System.out.println(">>> Message: That's not a number!");
            System.out.print  ("Book ID: ");
            sc.next();
        }
        int bookId = sc.nextInt();
        sc.nextLine();

        if(bookId==0) Menu.memberMenu();

        JSONArray jsonArray = readJSONFile();

        boolean bookFound = false;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject book = jsonArray.getJSONObject(i);
            if (book.getInt("bookCode") == bookId) {
                String status = book.getString("status");
                if (status.equals("borrowed")) {
                    System.out.println("Book Title   : " + book.getString("bookName"));
                    System.out.println("Author       : " + book.getString("author"));
                    System.out.println("Release Year : " + book.getInt   ("releaseYear"));
                    System.out.print  ("Continue returning this book? (Y/N): ");
                    String confirm = sc.nextLine();

                    if(confirm.equalsIgnoreCase("y")){
                        book.put("status", "available");
                        book.put("borrowedUntil", "-");

                        writeJSONFile(jsonArray);
                        System.out.println(">>> Message: Book with ID " + bookId + " has been successfully returned.");
                        System.out.println("             Thank you for returning the book");
                    } else {
                        returnBook();
                    }


                } else {
                    System.out.println(">>> Message: Book with ID " + bookId + " not on the list of borrowed books.");
                }
                bookFound = true;
                break;
            }
        }

        if (!bookFound) System.out.println(">>> Message: Book with ID " + bookId + " does not exist.");
        System.out.println("Press enter to return to member menu... ");
        sc.nextLine();
    }

    public static void renew() throws JSONException {
        Scanner sc = new Scanner(System.in);
        System.out.println("===================[RENEW EXPIRATION BOOK MENU]==================");
        System.out.println("Enter the book ID (enter 0 for return to member menu)");
        System.out.print  ("Book ID: ");
        while (!sc.hasNextInt()) {
            System.out.println(">>> Message: That's not a number!");
            System.out.print  ("Book ID: ");
            sc.next();
        }
        int bookId = sc.nextInt();
        sc.nextLine();

        if(bookId==0) Menu.memberMenu();

        JSONArray jsonArray = readJSONFile();

        boolean bookFound = false;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject book = jsonArray.getJSONObject(i);
            if (book.getInt("bookCode") == bookId) {
                String status = book.getString("status");
                if (status.equals("borrowed")) {
                    System.out.println("Book Title   : " + book.getString("bookName"));
                    System.out.println("Author       : " + book.getString("author"));
                    System.out.println("Release Year : " + book.getInt   ("releaseYear"));
                    System.out.print  ("Continue renew borrow date for this book? (Y/N): ");
                    String confirm = sc.nextLine();

                    if(confirm.equalsIgnoreCase("y")){
                        LocalDate borrowedUntil = LocalDate.now().plusWeeks(1);
                        book.put("borrowedUntil", borrowedUntil.toString());

                        writeJSONFile(jsonArray);
                        System.out.println(">>> Message: Book with ID " + bookId + " has been successfully renewed until " + borrowedUntil + ".");
                    } else {
                        renew();
                    }

                } else {
                    System.out.println(">>> Message: Book with ID " + bookId + " not on the list of borrowed books.");
                }
                bookFound = true;
                break;
            }
        }

        if (!bookFound) System.out.println(">>> Message: Book with ID " + bookId + " does not exist.");
        System.out.println("Press enter to return to member menu... ");
        sc.nextLine();
    }

    private static JSONArray readJSONFile() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(".\\JSONfolder\\booksData.json")));
            return new JSONArray(content);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private static void writeJSONFile(JSONArray jsonArray) {
        try (FileWriter fileWriter = new FileWriter(".\\JSONfolder\\booksData.json")) {
            fileWriter.write(jsonArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
