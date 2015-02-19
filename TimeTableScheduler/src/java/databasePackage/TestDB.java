import java.sql.ResultSet;
import timeTablePackage.ScheduledTimeTable;
import toolsPackage.Database;
import toolsPackage.Hash;

/**
    Tests a database installation by creating and querying
    a sample table. Call this program as
    java -classpath driver_class_path;. TestDB propertiesFile
*/
public class TestDB {
    public static void main(String[] args) throws Exception {
        Database db = new Database();
        // for local use only outside college network with putty
        //db.setup("127.0.0.1:3310", "2016_kmon1", "kmon1", "augeheid");
       
        //for use in college network
        db.setup("cs1.ucc.ie:3306", "2016_kmon1", "kmon1", "augeheid");

        try {

            ResultSet result = db.select("SELECT passwordHash FROM User WHERE email = \"112372501@umail.ucc.ie\";");
            while (result.next()) {
                System.out.println(result.getString("passwordHash"));
            }
        } finally {
        }
        
        System.out.println(Hash.sha1("123"));
        
    }
}