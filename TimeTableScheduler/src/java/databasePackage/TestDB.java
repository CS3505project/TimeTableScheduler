import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
    Tests a database installation by creating and querying
    a sample table. 
*/
public class TestDB {
    public static void main(String[] args) throws Exception {
        SimpleDataSource.init("database.properties");

        Connection conn = SimpleDataSource.getConnection();
        try {
            Statement stat = conn.createStatement();

            ResultSet result = stat.executeQuery("SELECT * FROM test.User");
            while (result.next()) {
                System.out.println(result.getString("email"));
            }
        } finally {
            conn.close();
        }
    }
}
