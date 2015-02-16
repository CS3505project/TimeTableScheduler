/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasePackage;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author donncha
 */
public class DatabaseTest {
    
    public DatabaseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setup method, of class Database.
     */
    @Test
    public void testSetup() {
        System.out.println("setup");
         //connect to the database
        String dbserver = "127.0.0.1:3310";
        String DSN = "2016_kmon1";
        String username = "kmon1";
        String password = "augeheid";
        Database instance = new Database();
        
        //if the result returns "" test sucessful otherwise its failed
        String expResult = "";
        String result = instance.setup(dbserver, DSN, username, password);
        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of issetup method, of class Database.
     */
    @Test
    public void testIssetup() {
        System.out.println("issetup");
        Database instance = new Database();
        
        boolean expResult1 = false;
        boolean result1 = instance.issetup();
        assertEquals(expResult1, result1);
        //connect to the database
        System.out.println("setup");
        String dbserver = "127.0.0.1:3310";
        String DSN = "2016_kmon1";
        String username = "kmon1";
        String password = "augeheid";
        String expResult = "";
        String result = instance.setup(dbserver, DSN, username, password);
        // return true if connection attempt is sucessful 
        boolean expResult2 = true;
        boolean result2 = instance.issetup();
        assertEquals(expResult2, result2);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of Close method, of class Database.
     */
    @Test
    public void testClose() {
        System.out.println("Close");
        //connect to the database
        String dbserver = "127.0.0.1:3310";
        String DSN = "2016_kmon1";
        String username = "kmon1";
        String password = "augeheid";
        Database instance = new Database();
        String result = instance.setup(dbserver, DSN, username, password);
       // return true if connection attempt is sucessful 
        boolean expResult1 = true;
        boolean result1 = instance.issetup();
        assertEquals(expResult1, result1);
        
        //try to close database 
        instance.Close();
        //if database is cloded issetup will be set to fasle
         boolean expResult2 = false;
         boolean result2 = instance.issetup();
         assertEquals(expResult2, result2);
        
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of Insert method, of class Database.
     */
    @Test
    public void testInsert() {
        System.out.println("Insert");
        String SQLinsert = "";
        
        String dbserver = "127.0.0.1:3310";
        String DSN = "2016_kmon1";
        String username = "kmon1";
        String password = "augeheid";
        Database instance = new Database();
        String result = instance.setup(dbserver, DSN, username, password);
        // check if database is set up 
        boolean expResult1 = true;
        boolean result1 = instance.issetup();
        assertEquals(expResult1, result1);
        
        instance.Insert(SQLinsert);
        
        String expResult2 = "";
//        boolean result2 = instance.Insert(SQLinsert);
//        assertEquals(expResult2, result2);
        
        
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of SelectRow method, of class Database.
     */
    @Test
    public void testSelectRow() {
        System.out.println("SelectRow");
        
        String SQLquery = "";
        String dbserver = "127.0.0.1:3310";
        String DSN = "2016_kmon1";
        String username = "kmon1";
        String password = "augeheid";
        
        Database instance = new Database();
        
        String[] expResult = null;
        String[] result = instance.SelectRow(SQLquery);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of SelectColumn method, of class Database.
     */
    @Test
    public void testSelectColumn() {
        System.out.println("SelectColumn");
        String SQLquery = "";
        Database instance = new Database();
        String[] expResult = null;
        String[] result = instance.SelectColumn(SQLquery);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeLogSQL method, of class Database.
     */
    @Test
    public void testWriteLogSQL() {
        System.out.println("writeLogSQL");
        String message = "";
        Database instance = new Database();
        instance.writeLogSQL(message);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
}
