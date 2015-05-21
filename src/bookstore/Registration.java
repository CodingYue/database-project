package bookstore;

import java.sql.*;
import java.util.Scanner;

/**
 * Created by yy on 5/14/15.
 */
public class Registration {
    boolean Register(Connection con, String[] args) throws Exception{

        String LoginName = args[0];

        String sql = "select * from Customer where Cuntomer.login_name = '" + LoginName + "'";
        PreparedStatement query = con.prepareStatement(sql);
        String output = "";
        ResultSet result = query.executeQuery(sql);
        if (result != null) {
            result.close();
            return false;
        }

        String tmpStatement = "INSERT INTO Customer VALUES (";
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
