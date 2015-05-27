package bookstore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yy on 5/26/15.
 */
public class Useful_feedbacks {
    static String[] name = {"feedback_id", "login_name", "ISBN", "made_date", "rating", "avg"};
    public String Top_feedbacks(Connection con, String ISBN, int K) throws Exception {
        PreparedStatement query = con.prepareStatement("" +
                "SELECT feedback.*, avg(score) AS avg " +
                "FROM feedback, evaluate " +
                "WHERE feedback.ISBN = " + ISBN + " and feedback.feedback_id = evaluate.feedback_id " +
                "GROUP BY feedback.feedback_id " +
                "ORDER BY avg DESC");

        ResultSet result = query.executeQuery();
        String ret = "";
        int tot = 0;
        while (result.next()) {
            for (int i = 0; i < 6; ++i) ret += result.getString(name[i]) + ", ";
            ret += "\n";
            if (++tot == K) break;
        }
        query = con.prepareStatement("" +
                "SELECT * FROM feedback "+
                "WHERE feedback.ISBN = " + ISBN + " AND NOT feedback_id in(SELECT feedback_id FROM evaluate)"
        );
        result = query.executeQuery();
        while (result.next()) {
            for (int i = 0; i < 5; ++i) ret += result.getString(name[i]) + ", ";
            ret += "0.0000\n";
        }
        return ret;
    }
}
