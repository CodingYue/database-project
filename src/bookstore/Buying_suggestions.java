package bookstore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 5/26/15.
 */
public class Buying_suggestions {

    String[] Seggestion(Connection con, String ISBN) throws Exception {
        PreparedStatement query = con.prepareStatement("" +
                "SELECT Book.*, sum(Orders.amount) as SUM FROM BOOK, Orders " +
                "WHERE BOOK.ISBN = Orders.ISBN AND BOOk.ISBN != " + ISBN +
                "   AND BOOK.ISBN in (SELECT B.ISBN FROM Orders as A, Orders as B " +
                "                     WHERE A.ISBN= "+ISBN+" AND B.login_name = A.login_name ) " +
                "GROUP BY Book.ISBN " +
                "ORDER BY SUM DESC"
        );

        ResultSet result = query.executeQuery();

        List<String> suggest_ISBN = new ArrayList<String>();


        while (result.next()) {
            suggest_ISBN.add(result.getString("ISBN") + ", " + result.getString("SUM"));
        }
        String[] array = new String[suggest_ISBN.size()];
        suggest_ISBN.toArray(array);
        return array;
    }

}
