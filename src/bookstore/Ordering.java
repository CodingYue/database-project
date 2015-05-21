package bookstore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

/**
 * Created by yy on 5/14/15.
 */
public class Ordering {
    double ordering(Connection con, String[] ISBN) throws Exception {

        double total = 0;

        for (int i = 0; i < ISBN.length; ++i) {

            PreparedStatement query = con.prepareStatement("SELECT * from Book Where Book.ISBN = " + ISBN[i]);
            ResultSet result = query.executeQuery();
            if (result == null) continue;
            total += result.getDouble("price");

            result.close();
        }

        return total;
    }
}
