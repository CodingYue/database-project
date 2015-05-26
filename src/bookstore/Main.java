package bookstore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

/**
 * Created by yy on 5/22/15.
 */
public class Main {
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

                String[] register_args = new String[5];

                System.out.print("Login Name : ");
                register_args[0] = "'" + in.next() + "'";
                System.out.print("Password : ");
                register_args[1] = "'" + in.next() + "'";
                System.out.print("Full Name : ");
                register_args[2] = "'" + in.next() + "'";
                System.out.print("Address : ");
                register_args[3] = "'" + in.next() + "'";
                System.out.print("Phone Number : ");
                register_args[4] = "'" + in.next() + "'";

                Registration reg = new Registration();

                if (reg.Register(con, register_args)) {
                    System.out.println("Registering OK");
                } else {
                    System.out.println("Your login name has been registered, Please input another one");
                }

            }

            System.out.print("Welcome to BOOKSTORE, enter any key to continue : ");
            String useless = in.next();
        }

        con.close();

    }
}
