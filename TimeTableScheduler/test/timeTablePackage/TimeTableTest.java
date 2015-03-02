/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeTablePackage;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
public class TimeTableTest {
    
    public TimeTableTest() {
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
     * Test of getPreSetTimeTable method, of class TimeTable.
     */
    @Test
    public void testGetPreSetTimeTable() {
        System.out.println("getPreSetTimeTable");
        TimeTable expResult = TimeTable.getPreSetTimeTable();
        TimeTable result = TimeTable.getPreSetTimeTable();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of conflictWithEvents method, of class TimeTable.
     */
    @Test
    public void testConflictWithEvents_3args() {
        System.out.println("conflictWithEvents");
        Calendar cal = Calendar.getInstance(Locale.FRANCE);
        cal.setTime(new Date());
        //Date date = new java.sql.Date("2015-03-02");// "2015-03-02";
        Time time = Time.valueOf("10:00:00");
        int duration = 1;
        TimeTable instance = TimeTable.getPreSetTimeTable();
        instance.setDisplayWeek("2015-03-02");
        instance.setupTimeSlots();
        instance.initialiseTimeTable("9");
        boolean expResult = true;
        boolean result = instance.conflictWithEvents(new Date(), time, duration);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of conflictWithEvents method, of class TimeTable.
     */
    @Test
    public void testConflictWithEvents_4args() {
        System.out.println("conflictWithEvents");
        Date date = null;
        Time time = null;
        int duration = 0;
        int maxPriority = 0;
        TimeTable instance = null;
        boolean expResult = false;
        boolean result = instance.conflictWithEvents(date, time, duration, maxPriority);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStartTime method, of class TimeTable.
     */
    @Test
    public void testSetStartTime() {
        System.out.println("setStartTime");
        EventTime startTime = null;
        TimeTable instance = null;
        instance.setStartTime(startTime);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEndTime method, of class TimeTable.
     */
    @Test
    public void testSetEndTime() {
        System.out.println("setEndTime");
        EventTime endTime = null;
        TimeTable instance = null;
        instance.setEndTime(endTime);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStartDay method, of class TimeTable.
     */
    @Test
    public void testSetStartDay() {
        System.out.println("setStartDay");
        Day startDay = null;
        TimeTable instance = null;
        instance.setStartDay(startDay);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEndDay method, of class TimeTable.
     */
    @Test
    public void testSetEndDay() {
        System.out.println("setEndDay");
        Day endDay = null;
        TimeTable instance = null;
        instance.setEndDay(endDay);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initialiseTimeTable method, of class TimeTable.
     */
    @Test
    public void testInitialiseTimeTable_List() {
        System.out.println("initialiseTimeTable");
        List<String> usersToMeet = null;
        TimeTable instance = null;
        instance.initialiseTimeTable(usersToMeet);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initialiseTimeTable method, of class TimeTable.
     */
    @Test
    public void testInitialiseTimeTable_String() {
        System.out.println("initialiseTimeTable");
        String userID = "";
        TimeTable instance = null;
        instance.initialiseTimeTable(userID);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createTimeTable method, of class TimeTable.
     */
    @Test
    public void testCreateTimeTable() {
        System.out.println("createTimeTable");
        EventType filterEvent = null;
        boolean hideDetail = false;
        TimeTable instance = null;
        String expResult = "";
        String result = instance.createTimeTable(filterEvent, hideDetail, hideDetail);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nextSuggestedTimeSlot method, of class TimeTable.
     */
    @Test
    public void testNextSuggestedTimeSlot() {
        System.out.println("nextSuggestedTimeSlot");
        int meetingDuration = 0;
        int maxPriority = 0;
        boolean clearPrevious = false;
        TimeTable instance = null;
        boolean expResult = false;
        boolean result = instance.nextSuggestedTimeSlot(meetingDuration, maxPriority, clearPrevious);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clearSuggestedTimeSlots method, of class TimeTable.
     */
    @Test
    public void testClearSuggestedTimeSlots() {
        System.out.println("clearSuggestedTimeSlots");
        TimeTable instance = null;
        instance.clearSuggestedTimeSlots();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
