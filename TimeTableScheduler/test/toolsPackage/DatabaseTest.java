/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolsPackage;

import java.sql.ResultSet;
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
        String dbserver = "127.0.0.1:3310";
        String DSN = "2016_dol8";
        String username = "dol8";
        String password = "zahriexo";
        
        Database instance = new Database();
        
        String expResult = "";
        
        String result = instance.setup(dbserver, DSN, username, password);
        
        assertEquals(expResult, result);
        
        
    }

    /**
     * Test of issetup method, of class Database.
     */
    @Test
    public void testIssetup() {
        System.out.println("issetup");
        Database instance = new Database();
        boolean expResult = false;
        boolean result = instance.issetup();
        assertEquals(expResult, result);
        
        String dbserver = "127.0.0.1:3310";
        String DSN = "2016_dol8";
        String username = "dol8";
        String password = "zahriexo";
        String result1 = instance.setup(dbserver, DSN, username, password);
        
        boolean expResult2 = true;
        boolean result2 = instance.issetup();
        assertEquals(expResult2, result2);
        
        
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of close method, of class Database.
     */
    @Test
    public void testClose() {
        System.out.println("close");
        String dbserver = "127.0.0.1:3310";
        String DSN = "2016_dol8";
        String username = "dol8";
        String password = "zahriexo";
        Database instance = new Database();
        String result = instance.setup(dbserver, DSN, username, password);
        // return true if connection attempt is sucessful 
        
        boolean expResult1 = true;
        boolean result1 = instance.issetup();
        assertEquals(expResult1, result1);
        
        //try to close database 
        instance.close();
         //if database is cloded issetup will be set to fasle
        
         boolean expResult2 = false;
         boolean result2 = instance.issetup();
         assertEquals(expResult2, result2);
        
    }

}
