package bookstore;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by yy on 5/14/15.
 */
public class Trust_recordings {
    void records(Connection con, String user, String[] args, String[] trust) throws Exception{
        for (int i = 0; i < args.length; ++i) {
            PreparedStatement insert = con.prepareStatement("INSERT INTO Trusting_records VALUES("
                    + user + ", " + args[i] + ", " + trust[i] + ")"
            );
        }
    }
}
