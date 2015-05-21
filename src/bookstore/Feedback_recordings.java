package bookstore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by yy on 5/14/15.
 */
public class Feedback_recordings {
    boolean recording(Connection con, String args[]) throws Exception {
        String ISBN = args[0];
        String user = args[1];
        PreparedStatement query = con.prepareStatement(
                "SELECT * from Feedback WHERE ISBN = " + ISBN
                + " login_name = " + user
        );

        ResultSet result = query.executeQuery();
        if (result != null) {
            result.close();
            return false;
        }

        String tmpStatement = "INSERT INTO Feedback VALUES(";
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
