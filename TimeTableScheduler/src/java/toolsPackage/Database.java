package toolsPackage;
 
/**
 * Handles database connections and executing queries
 * 
 * @author Shiny & John O Riordan
 */
import java.sql.*;
import java.io.*;
import java.util.Properties;

/**
 * Utility class to handle interactions with the database.
 * 
 * @author John O Riordan (Modified version of Colin McCormack's file)
 */
public class Database {
    private Statement statementObject;
    private Connection connectionObject;
    
    private String dbserver;
    private String DSN;
    private String username;
    private String password;
    private boolean setup = false;

    public Database() {
        this.statementObject = null;
        this.connectionObject = null;
        this.dbserver = "";
        this.DSN = "";
        this.username = "";
        this.password = "";
    }
    
    public static Database getSetupDatabase() {
        Database db = new Database();
        // for local use only outside college network with putty
        //db.setup("127.0.0.1:3310", "2016_dol8", "dol8", "zahriexo");
        //db.setup("127.0.0.1:3310", "2016_kmon1", "kmon1", "augeheid");
       
        //for use in college network
        db.setup("cs1.ucc.ie:3306", "2016_kmon1", "kmon1", "augeheid");
        return db;
    }
    
   
    /**
     * Setup the connection to the database.
     * 
     * @param dbserver Hostname/IP address of the server
     * @param DSN The name of the database
     * @param username The username to login to the database
     * @param password The password to login to the database
     * @return Error message if any errors occured
     */
    public String setup(String dbserver, String DSN, String username, String password)
    {
        this.dbserver = dbserver;
        this.DSN = DSN;
        this.username = username;
        this.password = password;
        String URL = "jdbc:mysql://" + dbserver + "/" + DSN;
       
        //initialise the driver for a mysql databse
        initialiseDriver("com.mysql.jdbc.Driver");
        
        return createDbConnection(URL, username, password);
    } 
    
    /**
     * Establishes a connection with the database.
     * 
     * @param URL The url for the database
     * @param username The username to login with
     * @param password The password to login with
     * @return Error message if any errors occured
     */
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
    
    /**
     * Initialises the driver for the database
     * 
     * @param driver The driver to initialise
     */
    private void initialiseDriver(String driver) {
        try {// Initialiase drivers
            Class.forName(driver);
        } catch (Exception exceptionObject) {
            writeLogSQL("Driver caused error " + exceptionObject.getMessage() + " Error dbclass.setup.1. ");
            System.err.println("Failed to load JDBC/ODBC driver. Error dbclass.setup.1 PLEASE report this error");
        }
    }
 
    /**
     * Indicates whether there is a connection to the database
     * 
     * @return True if there is connection and false otherwise
     */
    public boolean issetup()
    {
        return setup;
    }
    
    /**
     * Update the contents of the database properties file.
     * To prevent changing an existing value, use null
     * 
     * @param fileName Path to the properties file
     * @param driver New driver value
     * @param dbserver New database hostname/ip address of the server
     * @param DSN New database name
     * @param username New username value
     * @param password New password value
     */
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
    
    /**
     * Opens the properties file and returns it. May return null if 
     * unsuccessful attempt to open the file
     * 
     * @param fileName The path to the file
     * @return The properties file, may be null
     */
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
    
    /**
     * Setup a database connection using the values in the properties file
     * 
     * @param fileName The path to the file
     * @return Error message if any errors occured
     */
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
   
    /**
     * Closes the connection to the database
     */
    public void close()
    {
        try {
            if (connectionObject != null) {
                connectionObject.close();
                setup = false;
            }
        } catch (SQLException exceptionObject) {
            System.err.println("Problem with closing up ");
            writeLogSQL("closing caused error " + exceptionObject.getMessage());
        }
    }
 
    /**
     * Execute an update statement in the database.
     * Executes INSERT, DELETE and UPDATE queries
     * 
     * @param SQLinsert The sql query
     * @return True if query was successful
     */
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
            System.err.println(SQLinsert + " - Problem is : " + exceptionObject.getMessage());
            writeLogSQL(SQLinsert + " eorror caused while trying to insert " + exceptionObject.getMessage());
        }
        return false;
    } // End Insert
    
    /**
     * Executes a statement in the database that returns values.
     * Executes SELECT queries
     * 
     * @param SQLselect The sql query
     * @return The result set returned by executing the query
     */
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
        } catch (SQLException exceptionObject) {
            System.err.println(SQLselect + " - Problem is : " + exceptionObject.getMessage());
            writeLogSQL(SQLselect + "error while trying to select" + exceptionObject.getMessage());
        }
        return result;
    } // End select
    
    /**
     * Returns the number of columns in the result set.
     * 
     * @param statementResult The result set
     * @return Number of columns
     */
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
    
    /**
     * Returns the number of rows in the result set
     * 
     * @param statementResult The result set
     * @return Number of rows
     */
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
 
    /**
     * Send an SQL query to a database and return the single row result in an array of strings
     * 
     * @param SQLquery The sql query to execute
     * @return Single row result of the query in an array of strings
     */
    public String[] selectRow(String SQLquery)
    {
        String Result[];
        // Send an SQL query to a database and return the *single row* result in an array of strings
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
   
    /**
     * Send an SQL query to a database and return the single column result in an array of strings
     * 
     * @param SQLquery The sql query to execute
     * @return Single column result of the query in an array of strings
     */
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
 
    /**
     * Write the message to the log file
     * 
     * @param message Message to write to the file
     */
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