package databasePackage;

import java.sql.ResultSet;
import toolsPackage.Database;

/**
    Tests a database installation by creating and querying
    a sample table. Call this program as
    java -classpath driver_class_path;. TestDB propertiesFile
*/
public class TestDB {
    public static void main(String[] args) throws Exception {
        Database db = Database.getSetupDatabase();

        try {

            ResultSet result = db.select("SELECT passwordHash FROM User WHERE email = \"112372501@umail.ucc.ie\";");
            while (result.next()) {
                System.out.println(result.getString("passwordHash"));
            }
        } finally {
        }
        
    }
       
}