package bookstore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 5/27/15.
 */
public class Statistics {
    String[] Top_of_book(Connection con, int m) throws Exception {
        PreparedStatement query = con.prepareStatement(
                "SELECT Book.ISBN, SUM(amount) as SUM FROM Orders, Book " +
                "WHERE Orders.ISBN = Book.ISBN " +
                "GROUP BY Book.ISBN " +
                "ORDER BY SUM DESC"
        );
        ResultSet result = query.executeQuery();
        List<String> ret = new ArrayList<String>();
        while (result.next()) {
            ret.add(result.getString("ISBN") + " " + result.getString("SUM"));
            if (ret.size() == m) break;
        }
        String[] array = new String[ret.size()];
        ret.toArray(array);
        return array;
    }
    String[] Top_of_authors(Connection con, int m) throws Exception {
        PreparedStatement query = con.prepareStatement(
                "SELECT WRITTEN.author, SUM(amount) as SUM FROM WRITTEN, Orders, Book " +
                        "WHERE Orders.ISBN = Book.ISBN AND WRITTEN.ISBN = Book.ISBN " +
                        "GROUP BY WRITTEN.author " +
                        "ORDER BY SUM DESC "
        );
        ResultSet result = query.executeQuery();
        List<String> ret = new ArrayList<String>();
        while (result.next()) {
            ret.add(result.getString("author") + " " + result.getString("SUM"));
            if (ret.size() == m) break;
        }
        String[] array = new String[ret.size()];
        ret.toArray(array);
        return array;
    }
    String[] Top_of_publisher(Connection con, int m) throws Exception {
        PreparedStatement query = con.prepareStatement(
                "SELECT Book.publisher, SUM(amount) as SUM FROM Orders, Book " +
                        "WHERE Orders.ISBN = Book.ISBN " +
                        "GROUP BY Book.publisher " +
                        "ORDER BY SUM DESC "
        );
        ResultSet result = query.executeQuery();
        List<String> ret = new ArrayList<String>();
        while (result.next()) {
            ret.add(result.getString("publisher") + " " + result.getString("SUM"));
            if (ret.size() == m) break;
        }
        String[] array = new String[ret.size()];
        ret.toArray(array);
        return array;
    }
}
