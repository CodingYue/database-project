package bookstore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by yy on 5/14/15.
 */
public class Book_Browsing {

    static String[] book = {"ISBN", "Title", "price", "format", "keywords", "year_of_publication", "" +
            "publisher", "court"};

    String TABLE(Connection con, String[] args, String[] argsv) throws Exception {
        String tmpStatement = "SELECT * FROM Book WHERE ";
        for (int i = 0; i < args.length; ++i) {
            if (argsv[i] == "") continue;
            tmpStatement += args[i] + " = " + argsv[i];
            if (i+1 < args.length) tmpStatement += " AND ";
        }
        tmpStatement += " ORDER BY year_of_publication";
    //    System.out.println(tmpStatement.toString());
        PreparedStatement query = con.prepareStatement(tmpStatement);
        ResultSet result = query.executeQuery();

        String ret = "";

        while (result.next()) {
            for (int i = 0; i < 8; ++i) ret += result.getString(book[i]) + " ";
            ret += "\n";
        }
        return ret;
    }

    double all_avg(Connection con, String user, String[] args, String[] argv) throws Exception {
        String tmpStatement = "SELECT avg(Feedback.rating) as avg FROM Feedback WHERE " +
                "Feedback.ISBN in (SELECT Book.ISBN FROM Book WHERE ";
        for (int i = 0; i < args.length; ++i) {
            if (argv[i] == "") continue;
            tmpStatement += args[i] + " = " + argv[i];
            if (i+1 < args.length) tmpStatement += " AND ";
        }
        tmpStatement += ")";

        //System.out.println(tmpStatement);

        PreparedStatement query = con.prepareStatement(tmpStatement);

        ResultSet result = query.executeQuery();
        if (!result.next()) return 0.0;
        return result.getDouble("avg");
    }

    double limit_avg(Connection con, String user, String[] args, String[] argv) throws Exception {
        String tmpStatement = "SELECT avg(Feedback.rating) as avg FROM Feedback WHERE " +
                "Feedback.login_name in " +
                "( SELECT login_name2 AS login_name FROM trusting_records WHERE login_name1 = "+ user + ")" +
                " AND " +
                " Feedback.ISBN in (SELECT ISBN FROM Book WHERE ";

        for (int i = 0; i < args.length; ++i) {
            if (argv[i] == "") continue;
            tmpStatement += args[i] + " = " + argv[i];
            if (i+1 < args.length) tmpStatement += " AND ";
        }
        tmpStatement += ")";

    //    System.out.println(tmpStatement);

        PreparedStatement query = con.prepareStatement(tmpStatement);

        ResultSet result = query.executeQuery();
        if (!result.next()) return 0.0;
        return result.getDouble("avg");
    }

}
