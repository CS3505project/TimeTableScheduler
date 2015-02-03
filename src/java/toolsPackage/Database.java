package toolsPackage;
 
/**
 *
 * @author Shiny & John O Riordan
 */
import java.sql.*;
import java.io.*;
import java.util.Properties;
 
public class Database {
    private Statement statementObject;
    private Connection connectionObject;
    
    private String dbserver;
    private String DSN;
    private String username;
    private String password;
    private boolean setup = false;
   
    public String setup(String dbserver, String DSN, String username, String password)
    {
        this.dbserver = dbserver;
        this.DSN = DSN;
        this.username = username;
        this.password = password;
        String URL = "jdbc:mysql://" + dbserver + "/" + DSN;
       
        initialiseDriver("com.mysql.jdbc.Driver");
        
        return createDbConnection(URL, username, password);
    } 
    
    private String createDbConnection(String URL, String username, String password) {
        try {
            // Establish connection to database
            connectionObject = DriverManager.getConnection(URL, username, password);
            setup = true;
        } catch (SQLException exceptionObject) {
            writeLogSQL(URL + " caused error " + exceptionObject.getMessage() + " Error dbclass.setup.2");
            return("Problem with setting up " + URL + " Error dbclass.setup.2 PLEASE report this error");
        }
        return "";
    }
    
    private void initialiseDriver(String driver) {
        try {// Initialiase drivers
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception exceptionObject) {
            writeLogSQL("Driver caused error " + exceptionObject.getMessage() + " Error dbclass.setup.1. ");
            System.err.println("Failed to load JDBC/ODBC driver. Error dbclass.setup.1 PLEASE report this error");
        }
    }
 
    public boolean issetup()
    {
        return setup;
    }
    
    public void updatePropertiesFile(String fileName, String driver, String dbserver, String DSN, String username, String password)
    {
        Properties props = openPropertiesFile(fileName);
        
        if (driver != null) {
            props.setProperty("jdbc.driver", driver);
        }
        if (dbserver != null) {
            props.setProperty("jdbc.dbserver", dbserver);
        }
        if (DSN != null) {
            props.setProperty("jdbc.DSN", DSN);
        }
        if (username != null) {
            props.setProperty("jdbc.username", username);
        }
        if (password != null) {
            props.setProperty("jdbc.password", password);
        }
    }
    
    private Properties openPropertiesFile(String fileName) {
        Properties props = new Properties();
        try {
            FileInputStream in = new FileInputStream(fileName);
            props.load(in);
        } catch (Exception exceptionObject) {
            writeLogSQL(fileName + " caused error " + exceptionObject.getMessage() + " Error dbclass.setup.3. ");
            System.err.println("Failed to load properties file. Error dbclass.setup.3 PLEASE report this error");
        }
        return props;
    }
    
    public String setupFromPropertiesFile(String fileName)
    {
        Properties props = openPropertiesFile(fileName);

        dbserver = props.getProperty("jdbc.dbserver");
        DSN = props.getProperty("jdbc.DSN");
        String URL = "jdbc:mysql://" + dbserver + "/" + DSN;
        username = props.getProperty("jdbc.username");
        if (username == null) {
            username = "";
        }
        password = props.getProperty("jdbc.password");
        if (password == null) {
            password = "";
        }
        String driver = props.getProperty("jdbc.driver");
        if (driver != null) {
            initialiseDriver(driver);
        }
        
        return createDbConnection(URL, username, password);
    }
   
    public void close()
    {
        try {
            connectionObject.close();
        } catch (SQLException exceptionObject) {
            System.out.println("Problem with closing up ");
            writeLogSQL("closing caused error " + exceptionObject.getMessage());
        }
    }
 
    public boolean insert(String SQLinsert)
    {
        try {
            // Setup statement object
            statementObject = connectionObject.createStatement();
 
            // execute SQL commands to insert data
            statementObject.executeUpdate(SQLinsert);
            writeLogSQL(SQLinsert + " Executed OK");
            return true;
        } catch (SQLException exceptionObject) {
            System.out.println(SQLinsert + " - Problem is : " + exceptionObject.getMessage());
            writeLogSQL(SQLinsert + " caused error " + exceptionObject.getMessage());
        }
        return false;
    } // End Insert
    
    public ResultSet select(String SQLselect)
    {
        ResultSet result = null;
        try {
            // Setup statement object
            statementObject = connectionObject.createStatement();
 
            // execute SQL commands to select data
            if (statementObject.execute(SQLselect)) {
                result = statementObject.getResultSet();
            }
            writeLogSQL(SQLselect + " Executed OK");
        } catch (SQLException exceptionObject) {
            System.out.println(SQLselect + " - Problem is : " + exceptionObject.getMessage());
            writeLogSQL(SQLselect + " caused error " + exceptionObject.getMessage());
        }
        return result;
    } // End select
    
    public int getNumColumns(ResultSet statementResult) 
    {
        try {
            ResultSetMetaData rsmd = statementResult.getMetaData();
            return rsmd.getColumnCount();
        } catch (Exception e) {
            System.err.println("Problem counting columns: " + e.getMessage());
            writeLogSQL("Error occured counting columns in result set. Message: " + e.getMessage());
        }
        return 0;
    }
    
    public int getNumRows(ResultSet statementResult) 
    {
        int numRows = 0;
        try { 
            // Start solution from http://www.coderanch.com/t/303346/JDBC/java/find-number-rows-resultset
            if (statementResult.last()) {
                numRows = statementResult.getRow();
                statementResult.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
            }
            // End solution from http://www.coderanch.com/t/303346/JDBC/java/find-number-rows-resultset
        } catch (Exception e) {
            System.err.println("Problem counting rows: " + e.getMessage());
            writeLogSQL("Error occured counting rows in result set. Message: " + e.getMessage());
        }
        return numRows;
    }
 
    public String[] selectRow(String SQLquery)
    {
        String Result[];
        // Send an SQL query to a database and return the *single column* result in an array of strings
        try {// Make connection to database
            statementObject = connectionObject.createStatement();
 
            ResultSet statementResult = statementObject.executeQuery(SQLquery); //Should connection be left open?
 
            ResultSetMetaData rsmd = statementResult.getMetaData();
            int nrOfColumns = rsmd.getColumnCount();
 
            Result = new String[nrOfColumns];
 
            statementResult.next();
           
            int currentCounter = 0;
 
            while (currentCounter < nrOfColumns) { // While there are rows to process
                // Get the first cell in the current row
                Result[currentCounter] = statementResult.getString(currentCounter+1);
                currentCounter++;
            }
            // Close the link to the database when finished
           
        } catch (Exception e) {
            System.err.println("Select problems with SQL " + SQLquery);
            System.err.println("Select problem is " + e.getMessage());
            Result = new String[0]; //Need to setup result array to avoid initialisation error
            writeLogSQL(SQLquery + " caused error " + e.getMessage());
        }
        writeLogSQL(SQLquery + "worked ");
        return Result;
    } // End SelectRow
   
    public String[] selectColumn(String SQLquery)
    {
        String Result[];
        // Send an SQL query to a database and return the *single column* result in an array of strings
        try {// Make connection to database
            statementObject = connectionObject.createStatement(); //Should connection be left open?
 
            ResultSet statementResult = statementObject.executeQuery(SQLquery);
 
            // Start solution from http://www.coderanch.com/t/303346/JDBC/java/find-number-rows-resultset
            int rowcount = 0;
            if (statementResult.last()) {
                rowcount = statementResult.getRow();
                statementResult.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
            }
            // End solution from http://www.coderanch.com/t/303346/JDBC/java/find-number-rows-resultset
 
            Result = new String[rowcount];
 
            int currentCounter = 0;
 
            while (statementResult.next()) { // While there are rows to process
                // Get the first cell in the current row
                Result[currentCounter] = statementResult.getString(1);
                currentCounter++;
            }
            // Close the link to the database when finished
        } catch (Exception e) {
            System.err.println("Select problems with SQL " + SQLquery);
            System.err.println("Select problem is " + e.getMessage());
            Result = new String[0]; //Need to setup result array to avoid initialisation error
            writeLogSQL(SQLquery + " caused error " + e.getMessage());
        }
        writeLogSQL(SQLquery + "worked ");
        return Result;
    } // End Select
 
    public void writeLogSQL(String message) {
        PrintStream output;
        try {
            output = new PrintStream(new FileOutputStream("sql-logfile.txt", true));
            output.println(new java.util.Date() + " " + message);
            System.out.println(new java.util.Date() + " " + message);
            output.close();
        } catch (IOException ieo) {
            
        }
    } // End writeLog
 
} //End dblib