/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputPackage;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import userPackage.User;
import userPackage.UserType;

/**
 *
 * @author donncha
 */
public class InputTest {
    
    public InputTest() {
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
     * Test of login method, of class Input.
     */
    @Test
    public void testLogin() {
        System.out.println("login");
        String email = "112372501@umail.ucc.ie";
        String password = "123";
        Input instance = new Input();
        boolean expResult = true;
        boolean result = instance.login(email, password);
        assertEquals(expResult, result);

    }

    /**
     * Test of getUserDetailsStudent method, of class Input.
     */
    @Test
    public void testGetUserDetailsStudent() {
        System.out.println("getUserDetailsOfStudent");
        String email = "112372501@umail.ucc.ie";
        UserType expResult = UserType.STUDENT;
        UserType result = Input.getUserDetails(email).getUserType();
        assertEquals(expResult, result);  
        
        String email1 = "cmc@cs.ucc.ie";
        UserType expResult1 = UserType.STUDENT;
        UserType result1 = Input.getUserDetails(email1).getUserType();
        assertThat(expResult1, not(result1));
        
        String email2 =  "admin@umail.ucc.ie";
        UserType expResult2 = UserType.STUDENT;
        UserType result2 = Input.getUserDetails(email2).getUserType();
        assertThat(expResult2, not(result2));
    }
    
    /**
     * Test of getUserDetailsLecture method, of class Input.
     */
    @Test
    public void testGetUserDetailsLecture() {
        System.out.println("getUserDetailsOfStudent");
        String email = "cmc@cs.ucc.ie";
        UserType expResult = UserType.LECTURER;
        UserType result = Input.getUserDetails(email).getUserType();
        assertEquals(expResult, result);
        
        String email2 =  "admin@umail.ucc.ie";
        UserType expResult2 = UserType.LECTURER;
        UserType result2 = Input.getUserDetails(email2).getUserType();
        assertThat(expResult2, not(result2));
        
        String email1 = "112372501@umail.ucc.ie";
        UserType expResult1 = UserType.LECTURER;
        UserType result1 = Input.getUserDetails(email1).getUserType();
        assertThat(expResult1, not(result1)); 
    }
    
    /**
     * Test of getUserDetails method, of admin class Input.
     */
    @Test
    public void testGetUserDetailsAdmin() {
        String email2 =  "admin@umail.ucc.ie";
        UserType expResult2 = UserType.ADMIN;
        UserType result2 = Input.getUserDetails(email2).getUserType();
        assertEquals(expResult2, result2);
        
         String email1 = "112372501@umail.ucc.ie";
        UserType expResult1 = UserType.ADMIN;
        UserType result1 = Input.getUserDetails(email1).getUserType();
        assertThat(expResult1, not(result1));
        
        String email3 = "cmc@cs.ucc.ie";
        UserType expResult3 = UserType.ADMIN;
        UserType result3 = Input.getUserDetails(email3).getUserType();
        assertThat(expResult3, not(result3));
    } 
    
}
