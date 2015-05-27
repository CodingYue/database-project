package bookstore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by yy on 5/27/15.
 */
public class Two_degrees_of_separation {

    int what_degrees(Connection con, String authorA, String authorB) throws Exception {

        String tmpStatement = "SELECT * FROM WRITTEN as A, WRITTEN as B " +
                              "WHERE A.ISBN = B.ISBN " + " AND "+
                              "A.author = " + authorA + " AND " +
                              "B.author = " + authorB;

       // System.out.println(tmpStatement.toString());

        PreparedStatement query = con.prepareStatement(tmpStatement);
        ResultSet result = query.executeQuery();
        if (result.next()) return 1;

        String tmpStatement1 = "(SELECT B.author as author1 FROM WRITTEN as A, WRITTEN as B " +
                "WHERE A.ISBN = B.ISBN " + " AND "+
                "A.author = " + authorA + ") as H1";
        String tmpStatement2 = "(SELECT B.author as author2 FROM WRITTEN as A, WRITTEN as B " +
                "WHERE A.ISBN = B.ISBN " + " AND "+
                "A.author = " + authorB + ") as H2";
        tmpStatement = "SELECT * FROM " + tmpStatement1 + ", " + tmpStatement2 + " WHERE H1.author1 = H2.author2";

        //System.out.println(tmpStatement.toString());

        //System.out.println(tmpStatement);
        query = con.prepareStatement(tmpStatement);
        result = query.executeQuery();
        if (result.next()) return 2;

        return 0;
    }


}
