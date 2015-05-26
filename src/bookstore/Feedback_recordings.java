package bookstore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by yy on 5/14/15.
 */
public class Feedback_recordings {
    boolean recording(Connection con, String args[]) throws Exception {
        String ISBN = args[1];
        String user = args[0];
        PreparedStatement query = con.prepareStatement(
                "SELECT * from Feedback WHERE " +
                        "ISBN = " + ISBN + " AND " + "" +
                        " login_name = " + user
        );

        ResultSet result = query.executeQuery();
        if (result.next()) {
            result.close();
            return false;
        }

        query = con.prepareStatement(
                "SELECT COUNT(*) FROM Feedback"
        );

        result = query.executeQuery();

        int feedback_id = 0;

        while (result.next()) {
            feedback_id = result.getInt("COUNT(*)");
        }
        feedback_id++;

        String tmpStatement = "INSERT INTO Feedback VALUES(";
        tmpStatement += String.valueOf(feedback_id) + ", ";
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
