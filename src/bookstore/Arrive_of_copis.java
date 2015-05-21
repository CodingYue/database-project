package bookstore;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by yy on 5/14/15.
 */
public class Arrive_of_copis {
    void Arrive(Connection con, String[] ISBN, int[] copis) throws Exception {

        for (int i = 0; i < ISBN.length; ++i) {
            PreparedStatement add = con.prepareStatement(
                    "UPDATE Book SET price = price + " +
                    String.valueOf(copis[i]) +
                    "WHERE Book.ISBN = " + ISBN[i]
            );
            add.executeUpdate();
        }

    }
}
