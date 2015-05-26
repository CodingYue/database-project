package bookstore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by yy on 5/14/15.
 */
public class Ordering {
    double ask_price(Connection con, String[] ISBN, int[] amount) throws Exception {

        double total = 0;

        for (int i = 0; i < ISBN.length; ++i) {

            PreparedStatement query = con.prepareStatement("SELECT * from Book Where Book.ISBN = " + ISBN[i]);
            ResultSet result = query.executeQuery();
            if (!result.next()) continue;
            total += result.getDouble("price") * amount[i];

            result.close();
        }

        return total;
    }
    void ordering(Connection con, String[] args) throws Exception {

        PreparedStatement query = con.prepareStatement("SELECT * FROM Orders WHERE " +
                "ISBN = " + args[1] + " and " +
                " login_name = " + args[0] + " and " +
                " made_date = " + args[2]);
        System.out.println(query.toString());
        ResultSet result = query.executeQuery();

        if (!result.next()) {
            String tmpStatement = "INSERT INTO Orders VALUES(";
            for (int i = 0; i < args.length; ++i) {
                tmpStatement += args[i];
                if (i+1 < args.length) tmpStatement += ", ";
                else tmpStatement += ")";
            }
            PreparedStatement add = con.prepareStatement(tmpStatement);
            add.executeUpdate();
        } else {
            String tmpStatement = "UPDATE Orders SET amount = amount + " + args[3] + " WHERE " +
                    "ISBN = " + args[0] + " login_name = " + args[1] + " made_date = " + args[2];
            PreparedStatement update = con.prepareCall(tmpStatement);
            update.executeUpdate();
        }

    }
}
