package bookstore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by yy on 5/14/15.
 */
public class Usefulness_ratings {
    boolean rate(Connection con, String[] args) throws Exception {
        String user = args[0];
        String feedback_id = args[1];

        PreparedStatement query = con.prepareStatement("SELECT * FROM Feedback WHERE id = "
                        + feedback_id + "login_name = " + user
        );
        ResultSet result = query.executeQuery();
        if (result != null) {
            result.close();
            return false;
        }

        String tmpStatement = "INSERT INTO evaluate VALUE(";
        for (int i = 0; i < args.length; ++i) {
            tmpStatement += args[i];
            if (i+1 < args.length) tmpStatement += ", ";
            else tmpStatement += ")";
        }

        PreparedStatement insert = con.prepareStatement(tmpStatement);
        insert.executeUpdate();

        return true;
    }
}
