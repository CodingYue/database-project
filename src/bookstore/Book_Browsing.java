package bookstore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by yy on 5/14/15.
 */
public class Book_Browsing {
    String TABLE(Connection con, String[] args, String[] argsv) throws Exception {
        String tmpStatement = "SELECT * FROM Book WHERE ";
        for (int i = 0; i < args.length; ++i) {
            if (argsv[i] == "") continue;
            tmpStatement += args[i] + "=" + argsv[i] + " ";
        }
        tmpStatement += "ORDER BY year_of_publication";
        PreparedStatement query = con.prepareStatement(tmpStatement);
        ResultSet result = query.executeQuery();

        String ret = "";

        while (result.next()) {
            ret += result + "\n";
        }
        return ret;
    }

    double all_avg(Connection con, String user, String[] args, String[] argv) throws Exception {
        String tmpStatement = "SELECT avg(Feedback.score) FROM Feedback WHERE " +
                "Feedback.ISBN in (SELECT * FROM Book WHERE ";
        for (int i = 0; i < args.length; ++i) {
            tmpStatement += "Book." + args[i] + "=" + argv[i] + " ";
        }
        tmpStatement += ")";

        PreparedStatement query = con.prepareStatement(tmpStatement);

        ResultSet result = query.executeQuery();
        if (result == null) return 0.0;
        return result.getDouble("avg");
    }

    double limit_avg(Connection con, String user, String[] args, String[] argv) throws Exception {
        String tmpStatement = "SELECT avg(Feedback.score) FROM Feedback WHERE " +
                "Feedback.login_name = "+ user + " Feedback.ISBN in (SELECT ISBN FROM Book,  WHERE ";
        for (int i = 0; i < args.length; ++i) {
            tmpStatement += "Book." + args[i] + "=" + argv[i] + " ";
        }
        tmpStatement += ")";
        PreparedStatement query = con.prepareStatement(tmpStatement);

        ResultSet result = query.executeQuery();
        if (result == null) return 0.0;
        return result.getDouble("avg");
    }

}
