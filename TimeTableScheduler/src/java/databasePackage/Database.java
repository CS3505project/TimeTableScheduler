package databasePackage;
 
/**
 *
 * @author Shiny
 */
import java.sql.*;
import java.text.*;
import java.io.*;
 
public class Database{
 
    private Statement statementObject;
    private Connection connectionObject;
   
    private String dbserver;
    private String DSN;
    private String username;
    private String password;
    private boolean setup=false;
   
    public String setup(String dbserver, String DSN,String username,String password)
        {
           this.dbserver=dbserver;
           this.DSN=DSN;
           this.username=username;
           this.password=password;
        String URL = "jdbc:mysql://"+dbserver+"/" + DSN;
       
        try {// Initialiase drivers
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception exceptionObject) {
            //write error message
            writeLogSQL(URL + " caused error " + exceptionObject.getMessage()+" Error dbclass.setup.1. ");
            return("Failed to load JDBC/ODBC driver. Error dbclass.setup.1 PLEASE report this error");
        }
        try {
            // Establish connection to database
            connectionObject = DriverManager.getConnection(URL, username, password);
            setup=true;
        } catch (SQLException exceptionObject) {
            //writes log error 
            writeLogSQL(URL + " caused error " + exceptionObject.getMessage()+" Error dbclass.setup.2");
            return("Problem with setting up " + URL+" Error dbclass.setup.2 PLEASE report this error");
        }
 
    return "";
    } // DatabaseConnectorNew constructor
 
 public boolean issetup()
    {
    return setup;
    }
   
 public void Close()
    {
    try {
            // Establish connection to database
            connectionObject.close();
            setup=false;
        }
        catch (SQLException exceptionObject)
        {
            System.out.println("Problem with closing up ");
            writeLogSQL("closing caused error " + exceptionObject.getMessage());
        }
    } //CloseDatabaseConnection
 
   public void Insert(String SQLinsert)
        {
 
        // Setup database connection details
        try {
            // Setup statement object
            statementObject = connectionObject.createStatement();
 
            // execute SQL commands to insert data
            statementObject.executeUpdate(SQLinsert);
            writeLogSQL(SQLinsert +" Executed OK");
            }
        catch (SQLException exceptionObject) {
            System.out.println(SQLinsert+" - Problem is : " + exceptionObject.getMessage());
            writeLogSQL(SQLinsert + " caused error while trying to insert " + exceptionObject.getMessage());
            }
        } // End Insert
 
   public String[] SelectRow(String SQLquery)
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
 
            while (currentCounter<nrOfColumns) // While there are rows to process
            {
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
   
    public String[] SelectColumn(String SQLquery)
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
 
            while (statementResult.next()) // While there are rows to process
            {
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
    } // End Select Column
 
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