package bookstore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

/**
 * Created by yy on 5/14/15.
 */
public class NewBook {
    void newbook(Connection con, String args[], String[] authors) throws Exception {

        String tmpStatement = "INSERT INTO Book VALUES(";
        for (int i = 0; i < args.length; ++i) {
            tmpStatement += args[i];
            if (i+1 < args.length) tmpStatement += ", ";
            else tmpStatement += ")";
        }

        PreparedStatement insert = con.prepareStatement(tmpStatement);
        insert.executeUpdate();
        for (int i = 0; i < authors.length; ++i) {
            tmpStatement = "INSERT INTO WRITTEN VALUES(" + authors[i] + ", " + args[0] + ")";
            insert = con.prepareStatement(tmpStatement);
            insert.executeUpdate();
        }
    }
}
