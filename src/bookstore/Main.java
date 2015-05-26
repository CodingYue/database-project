package bookstore;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.awt.print.Book;
import java.rmi.server.ExportException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

/**
 * Created by yy on 5/22/15.
 */
public class Main {
    private static void register(Connection con, Scanner in) throws Exception {
        String[] register_args = new String[5];

        System.out.print("Login Name : ");
        register_args[0] = "'" + in.nextLine() + "'";
        System.out.print("Password : ");
        register_args[1] = "'" + in.nextLine() + "'";
        System.out.print("Full Name : ");
        register_args[2] = "'" + in.nextLine() + "'";
        System.out.print("Address : ");
        register_args[3] = "'" + in.nextLine() + "'";
        System.out.print("Phone Number : ");
        register_args[4] = "'" + in.nextLine() + "'";

        Registration reg = new Registration();

        if (reg.Register(con, register_args)) {
            System.out.println("Registering OK");
        } else {
            System.out.println("Your login name has been registered, Please input another one");
        }

    }
    private static void order(Connection con, Scanner in) throws Exception {
        System.out.print("Login Name : ");
        String username = "'" + in.nextLine() + "'";
        System.out.print("Date : ");
        String date = "'" + in.nextLine() + "'";

        System.out.print("How many Books do you want to order? : ");
        int n = in.nextInt();
        String[] ISBN = new String[n];
        int[] amount = new int[n];
        in.nextLine();
        for (int i = 0; i < n; ++i) {
            String str = in.nextLine();
            String[] hehe = str.split(" ");
            ISBN[i] = "'" + hehe[0] + "'";
            if (hehe.length == 2) {
                amount[i] = Integer.parseInt(hehe[1]);
            } else {
                amount[i] = 1;
            }

        }
        Ordering order = new Ordering();
        double price = order.ask_price(con, ISBN, amount);
        System.out.printf("%.2f\n", price);

        for (int i = 0; i < n; ++i) {
            String[] order_args = new String[4];
            order_args[0] = username;
            order_args[1] = ISBN[i];
            order_args[2] = date;
            order_args[3] = String.valueOf(amount[i]);
            order.ordering(con, order_args);
        }

    }
    private static void newbook(Connection con, Scanner in) throws Exception {

        String[] args = new String[8];

        in.nextLine();
        System.out.print("ISBN : ");
        args[0] = "'" + in.nextLine() + "'";
        System.out.print("Title : ");
        args[1] = "'" + in.nextLine() + "'";
        System.out.print("Price : ");
        args[2] = in.nextLine();
        System.out.print("format : ");
        args[3] = "'" + in.nextLine() + "'";
        System.out.print("keywords : ");
        args[4] = "'" + in.nextLine() + "'";
        System.out.print("year of publication : ");
        args[5] = "'" + in.nextLine() + "'";
        System.out.print("publisher : ");
        args[6] = "'" + in.nextLine() + "'";
        System.out.print("court : ");
        args[7] = in.nextLine();
        NewBook operation = new NewBook();
        operation.newbook(con, args);
    }
    private static void arrival_of_more_copies(Connection con, Scanner in) throws Exception {
        int n = in.nextInt();
        in.nextLine();
        String[] ISBN = new String[n];
        int[] copies = new int[n];
        for (int i = 0; i < n; ++i) {
            String[] hehe = in.nextLine().split(" ");
            ISBN[i] = "'" + hehe[0] + "'";
            copies[i] = Integer.parseInt(hehe[1]);
        }
        Arrive_of_copis operator = new Arrive_of_copis();
        operator.Arrive(con, ISBN, copies);
    }
    private static void feedback_recordings(Connection con, Scanner in) throws Exception {
        String[] args = new String[4];
        in.nextLine();
        for (int i = 0; i < 3; ++i) args[i] = "'" + in.nextLine() + "'";
        args[3] = in.nextLine();
        Feedback_recordings operation = new Feedback_recordings();
        operation.recording(con, args);
    }
    private static void usefulness_ratings(Connection con, Scanner in) throws Exception {
        String[] args = new String[3];
        in.nextLine();
        args[0] = "'" + in.nextLine() + "'";
        args[1] = in.nextLine();
        args[2] = in.nextLine();
        Usefulness_ratings operation = new Usefulness_ratings();
        operation.rate(con, args);
    }
    private static void trust_recordings(Connection con, Scanner in) throws Exception {
        String user = "'" + in.next() + "'";
        int n = in.nextInt();
        in.nextLine();

        String[] args = new String[n];
        String[] trust = new String[n];

        for (int i = 0; i < n; ++i) {
            String[] hehe = in.nextLine().split(" ");
            args[i] = "'" + hehe[0] + "'";
            trust[i] = hehe[1];
        }
        Trust_recordings operation = new Trust_recordings();
        operation.records(con, user, args, trust);
    }
    private static void book_browsing(Connection con, Scanner in) throws Exception {

        String user = "'" + in.next() + "'";
        char op = in.next().charAt(0);

        int limit_n = in.nextInt();
        in.nextLine();


        String[] args = new String[limit_n];
        String[] argsv = new String[limit_n];

        for (int i = 0; i < limit_n; ++i) {
            String[] hehe = in.nextLine().split("=");
            args[i] = hehe[0];
            argsv[i] = "'" + hehe[1] + "'";
        }
        Book_Browsing operation = new Book_Browsing();
        if (op == 'a') {
            System.out.println(operation.TABLE(con, args, argsv));
        }
        if (op == 'b') {
            System.out.println(operation.all_avg(con, user, args, argsv));
        }
        if (op == 'c') {
            System.out.println(operation.limit_avg(con, user, args, argsv));
        }

    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);

        Connection con = (new Connector()).con;


        while (true) {


            System.out.print("Please input your operation :\n" +
                    "1) Registration\n" +
                    "2) Ordering\n" +
                    "3) New book\n" +
                    "4) Arrival of more copies\n" +
                    "5) Feedback recordings\n" +
                    "6) Usefulness ratings\n" +
                    "7) Trust recordings\n" +
                    "8) Book Browsing\n" +
                    "9) Useful feedbacks\n" +
                    "10) Buying suggestions\n" +
                    "11) Two degrees of separation\n" +
                    "12) Statistics\n" +
                    "13) User awards\n");
            System.out.print("What do you want to do ? : ");
            int op = in.nextInt();
            if (op == -1) {
                break;
            }

            if (op == 1) {
                register(con, in);
            }

            if (op == 2) {
                order(con, in);
            }

            if (op == 3) {
                newbook(con, in);
            }

            if (op == 4) {
                arrival_of_more_copies(con, in);
            }

            if (op == 5) {
                feedback_recordings(con, in);
            }

            if (op == 6) {
                usefulness_ratings(con, in);
            }

            if (op == 7) {
                trust_recordings(con, in);
            }

            if (op == 8) {
                book_browsing(con, in);
            }

            System.out.print("Welcome to BOOKSTORE, enter any key to continue : ");
            String useless = in.next();
        }

        con.close();

    }
}
