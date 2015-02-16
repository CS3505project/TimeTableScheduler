/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userPackage;

import java.sql.Date;
import java.sql.Time;
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
public class LecturerTest {
    
    public LecturerTest() {
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
     * Test of addPractical method, of class Lecturer.
     */
    @Test
    public void testAddPractical() {
        System.out.println("addPractical");
        String moduleCode = "cs3305";
        String semester = "1";
        int weekDay = 3;
        Time time = null;
        String room = "g21";
        Date startDate = null;
        Date endDate = null;
        Lecturer instance = null;
        boolean expResult = true;
        boolean result = instance.addPractical(moduleCode, semester, weekDay, time, room, startDate, endDate);
        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of cancelPracticalOrLecture method, of class Lecturer.
     */
    @Test
    public void testCancelPracticalOrLecture() {
        System.out.println("cancelPracticalOrLecture");
        String moduleCode = "";
        Date date = null;
        Time time = null;
        String description = "";
        Lecturer instance = null;
        boolean expResult = true;
        boolean result = instance.cancelPracticalOrLecture(moduleCode, date, time, description);
        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
}
