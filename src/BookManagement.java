import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class BookManagement {
    public static void addBook() {
        try {
            JSONArray jsonArray = readJSONFile();
            Scanner sc = new Scanner(System.in);
            int bookCode;

            System.out.println("================[ADD BOOK MENU]===============");
            do {
                System.out.print  ("Enter Book ID      : ");
                while (!sc.hasNextInt()) {
                    System.out.println(">>> Message: That's not a number!");
                    System.out.print  ("Enter Book ID      : ");
                    sc.next();
                }
                bookCode = sc.nextInt();

                if (checkIdExists(jsonArray, bookCode)) {
                    System.out.println(">>> Message: ID already exists!");
                }
            } while (checkIdExists(jsonArray, bookCode));
            sc.nextLine();

            System.out.print("Enter book Title   : ");
            String bookName = sc.nextLine();
            System.out.print("Enter book Type    : ");
            String bookType = sc.nextLine();
            System.out.print("Enter Author       : ");
            String author = sc.nextLine();
            System.out.print("Enter Release Year : ");
            while (!sc.hasNextInt()) {
                System.out.println(">>> Message: That's not a number!");
                System.out.print  ("Enter Release Year : ");
                sc.next();
            }
            int releaseYear = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter publisher    : ");
            String publisher = sc.nextLine();

            JSONObject newBook = new JSONObject();
            newBook.put("bookCode", bookCode);
            newBook.put("bookName", bookName);
            newBook.put("bookType", bookType);
            newBook.put("author", author);
            newBook.put("releaseYear", releaseYear);
            newBook.put("publisher", publisher);
            newBook.put("status", "available");
            newBook.put("borrowedUntil", "-");

            jsonArray.put(newBook);

            writeJSONFile(jsonArray);

            System.out.println(">>> Message: New book added successfully.");
            System.out.println("Click enter to return to admin menu... ");
            sc.nextLine();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void update() throws JSONException {
        Scanner sc = new Scanner(System.in);

        System.out.println("===================[BOOK EDIT MENU]==================");
        System.out.println("1. Edit Title");
        System.out.println("2. Edit Type");
        System.out.println("3. Edit Author");
        System.out.println("4. Edit Release Year");
        System.out.println("5. Edit Publisher");
        System.out.println("6. Return to admin menu");
        System.out.print  ("Select the edit menu (1-6): ");

        while (!sc.hasNextInt()) {
            System.out.println(">>> Message: That's not a number!");
            System.out.print  ("Select the edit menu (1-6): ");
            sc.next();
        }
        int menuSelect = sc.nextInt();
        sc.nextLine();

        if(menuSelect==6) Menu.adminMenu();

        System.out.print("Enter book ID: ");

        while (!sc.hasNextInt()) {
            System.out.println(">>> Message: That's not a number!");
            System.out.print("Enter book ID: ");
            sc.next();
        }
        int bookID = sc.nextInt();
        sc.nextLine();

        JSONArray jsonArray = readJSONFile();

        boolean bookFound = false;

        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject book = jsonArray.getJSONObject(i);

            if (book.getInt("bookCode") == bookID) {
                System.out.println("Book Title   : " + book.getString("bookName"));
                System.out.println("Book Type    : " + book.getString("bookType"));
                System.out.println("Author       : " + book.getString("author"));
                System.out.println("Release Year : " + book.getInt   ("releaseYear"));
                System.out.println("Publisher    : " + book.getString("publisher"));
                System.out.print  ("Continue editing this book? (Y/N): ");
                String confirm = sc.nextLine();

                if(confirm.equalsIgnoreCase("y")) {
                    switch (menuSelect) {
                        case 1 -> {
                            System.out.print("Enter new Title: ");
                            String newTitle = sc.nextLine();
                            book.put("bookName", newTitle);
                            writeJSONFile(jsonArray);
                        }
                        case 2 -> {
                            System.out.print("Enter new Type: ");
                            String newType = sc.nextLine();
                            book.put("bookType", newType);
                            writeJSONFile(jsonArray);
                        }
                        case 3 -> {
                            System.out.print("Enter new Author: ");
                            String newAuthor = sc.nextLine();
                            book.put("author", newAuthor);
                            writeJSONFile(jsonArray);
                        }
                        case 4 -> {
                            System.out.print("Enter new Release year: ");
                            int newYear = sc.nextInt();
                            book.put("releaseYear", newYear);
                            writeJSONFile(jsonArray);
                        }
                        case 5 -> {
                            System.out.print("Enter new Publisher: ");
                            String newPublisher = sc.nextLine();
                            book.put("publisher", newPublisher);
                            writeJSONFile(jsonArray);
                        }
                    }
                } else {
                    update();
                }

                bookFound = true;
                break;
            }
        }

        if (!bookFound) System.out.println(">>> Message: Book with ID " + bookID + " does not exist.");
        System.out.println("Press enter to return to admin menu... ");
        sc.nextLine();
    }

    public static void delete() throws JSONException {
        System.out.println("===================[BOOK DELETE]==================");
        System.out.println("Enter book ID (Enter 0 for return to admin menu)");
        System.out.print  ("Book ID: ");

        Scanner sc = new Scanner(System.in);
        while (!sc.hasNextInt()) {
            System.out.println(">>> Message: That's not a number!");
            System.out.print  ("Book ID: ");
            sc.next();
        }
        int bookID = sc.nextInt();
        sc.nextLine();

        if(bookID==0) Menu.adminMenu();

        JSONArray jsonArray = readJSONFile();

        boolean bookFound = false;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject book = jsonArray.getJSONObject(i);
            if (book.getInt("bookCode") == bookID) {
                System.out.println("Book Title   : " + book.getString("bookName"));
                System.out.println("Author       : " + book.getString("author"));
                System.out.println("Release Year : " + book.getInt   ("releaseYear"));
                System.out.print  ("Continue deleting this book? (Y/N): ");
                String confirm = sc.nextLine();

                if(confirm.equalsIgnoreCase("y")){
                    jsonArray.remove(i);
                    bookFound = true;

                    System.out.println(">>> Message: Book with ID " + bookID + " deleted successfully");
                    writeJSONFile(jsonArray);
                    break;
                } else {
                    delete();
                }
            }
        }

        if(!bookFound) System.out.println(">>> Message: Book with ID " + bookID + " does not exist.");
        System.out.println("Press enter to return to admin menu... ");
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

    private static boolean checkIdExists(JSONArray jsonArray, int ID) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            if (obj.getInt("bookCode") == ID) {
                return true; // Username already exists
            }
        }
        return false; // Username does not exist
    }
}
