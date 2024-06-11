import org.json.JSONException;
import java.util.Scanner;

public class Menu {
    public static void Start(){
        Scanner sc = new Scanner(System.in);

        System.out.println("=============[WELCOME TO LIBRARY MANAGEMENT SYSTEM]============");
        System.out.println("1. Member login menu (for more access)");
        System.out.println("2. Member register menu");
        System.out.println("3. Search book");
        System.out.println("4. View book details (using book ID)");
        System.out.println("5. List all books");
        System.out.println("6. Admin login (for library management)");
        System.out.println("7. Exit");
        System.out.print  ("Select the main menu (1-7): ");

        while (!sc.hasNextInt()) {
            System.out.println(">>> Message: That's not a number!");
            System.out.print  ("Select the main menu (1-7): ");
            sc.next();
        }
        int menuSelect = sc.nextInt();
        sc.nextLine();

        if(menuSelect==1) {
            Login.memberLogin();
        }
        else if(menuSelect==2) {
            Register.member();
        }
        else if(menuSelect==3) {
            BookSearch.stringSearch();
        }
        else if(menuSelect==4) {
            BookSearch.searchID();
        }
        else if(menuSelect==5) {
            BookSort.showAll();
        }
        else if(menuSelect==6) {
            Login.adminLogin();
        }
        else if(menuSelect==7) {
            System.exit(0);
        }
        else{
            System.out.println(">>> Message: Menu not available!");
        }
        Start();
    }

    public static void memberMenu() throws JSONException {
        Scanner sc = new Scanner(System.in);

        System.out.println("=================[LIBRARY MEMBER MENU]================");
        System.out.println("1. Search book");
        System.out.println("2. View book details (using book ID)");
        System.out.println("3. List all books");
        System.out.println("4. Borrow book");
        System.out.println("5. Return book");
        System.out.println("6. Renew book expiration date");
        System.out.println("7. Favorite book");
        System.out.println("8. Logout");
        System.out.print  ("Select the menu (1-7): ");

        while (!sc.hasNextInt()) {
            System.out.println(">>> Message: That's not a number!");
            System.out.print  ("Select the menu (1-7): ");
            sc.next();
        }
        int menuSelect = sc.nextInt();
        sc.nextLine();

        if(menuSelect==1) {
            BookSearch.stringSearch();
        }
        else if(menuSelect==2) {
            BookSearch.searchID();
        }
        else if(menuSelect==3) {
            BookSort.showAll();
        }
        else if(menuSelect==4) {
            BookUpdate.borrow();
        }
        else if(menuSelect==5) {
            BookUpdate.returnBook();
        }
        else if(menuSelect==6) {
            BookUpdate.renew();
        }
        else if(menuSelect ==7) {
            MemberTempMemory.favoriteBooks();
        }
        else if(menuSelect ==8) {
            Start();
        } else {
            System.out.println(">>> Message: Menu not available!");
        }
        memberMenu();

    }

    public static void adminMenu() throws JSONException {
        Scanner sc = new Scanner(System.in);
        System.out.println("================[LIBRARY ADMIN MENU]================");
        System.out.println("1. Add book");
        System.out.println("2. Edit book details");
        System.out.println("3. Delete book");
        System.out.println("4. View all books detail");
        System.out.println("5. View all library member detail");
        System.out.println("6. Delete library member");
        System.out.println("7. Register new library admin");
        System.out.println("8. Logout");
        System.out.print  ("Select the menu (1-8): ");

        while (!sc.hasNextInt()) {
            System.out.println(">>> Message: That's not a number!");
            System.out.print  ("Select the menu (1-8): ");
            sc.next();
        }
        int menuSelect = sc.nextInt();
        sc.nextLine();

        if(menuSelect==1) {
            BookManagement.addBook();
        }
        else if(menuSelect==2) {
            BookManagement.update();
        }
        else if(menuSelect==3) {
            BookManagement.delete();
        }
        else if(menuSelect==4) {
            BookSort.showAll();
        }
        else if(menuSelect==5) {
            MemberSort.showAll();
        }
        else if(menuSelect==6) {
            MemberManagement.delete();
        }
        else if(menuSelect==7) {
            Register.admin();
        }
        else if(menuSelect==8) {
            Start();
        }
        else {
            System.out.println(">>> Message: Menu unavailable!");
        }
        adminMenu();
    }
}
