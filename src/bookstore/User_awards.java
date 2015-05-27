package bookstore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 5/27/15.
 */
public class User_awards {
    String[] trust(Connection con, int m) throws Exception {
        PreparedStatement query = con.prepareStatement(
                "SELECT Customer.login_name, SUM(trust) as SUM FROM Customer, trusting_records " +
                "WHERE Customer.login_name = trusting_records.login_name2 " +
                "GROUP BY Customer.login_name " +
                "ORDER BY SUM DESC "
        );

        //System.out.println(query);

        ResultSet result = query.executeQuery();
        List<String> ret = new ArrayList<String>();
        boolean first = true;
        while (result.next()) {
            int x = result.getInt("SUM");
            if (x < 0 && first) {
                first = false;
                query = con.prepareStatement(
                        "SELECT Customer.login_name FROM Customer "+
                        "WHERE NOT Customer.login_name in (SELECT trusting_records.login_name2 as login_name FROM trusting_records " +
                                "WHERE trusting_records.login_name2 = Customer.login_name) "
                );
                ResultSet all_user = query.executeQuery();
                while (all_user.next()) {
                    ret.add(all_user.getString("login_name") + " 0");
                    if (ret.size() == m) break;
                }
            }
            if (ret.size() == m) break;
            ret.add(result.getString("login_name") + " " + result.getString("SUM"));
            if (ret.size() == m) break;
        }
        result.close();
        String[] array = new String[ret.size()];
        ret.toArray(array);
        return array;
    }
    String[] usefulness(Connection con, int m) throws Exception {
        PreparedStatement query = con.prepareStatement(
                "SELECT Customer.login_name, avg(score) as avg FROM Customer, feedback, evaluate " +
                "WHERE Customer.login_name = feedback.login_name AND feedback.feedback_id = evaluate.feedback_id " +
                "GROUP BY Customer.login_name " +
                "ORDER BY avg DESC "
        );
        ResultSet result = query.executeQuery();

        List<String> ret = new ArrayList<String>();
        while (result.next()) {
            if (ret.size() == m) break;
            ret.add(result.getString("login_name") + " " + result.getString("avg"));
        }

        query = con.prepareStatement(
                "SELECT Customer.login_name FROM Customer " +
                "WHERE NOT Customer.login_name in(" +
                "SELECT feedback.login_name FROM feedback, evaluate " +
                "WHERE feedback.feedback_id = evaluate.feedback_id )"
        );
        result = query.executeQuery();
        while (result.next()) {
            if (ret.size() == m) break;
            ret.add(result.getString("login_name") + " 0.000");
        }
        String[] array = new String[ret.size()];
        ret.toArray(array);
        result.close();
        return array;
    }
}
