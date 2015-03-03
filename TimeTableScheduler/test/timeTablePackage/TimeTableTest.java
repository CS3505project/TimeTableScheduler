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
        TimeTable instance = TimeTable.getPreSetTimeTable();
        instance.setDisplayWeek("2015-03-02");
        instance.setupTimeSlots();
        instance.initialiseTimeTable("9");
        instance.getStartDay();
        instance.getEndDay();
        instance.getEndTime();
        instance.getStartTime();
        //expResult =;
        
//        TimeTable instance2 = TimeTable.getPreSetTimeTable();
//        instance2.setDisplayWeek("2015-03-02");
//        instance2.setupTimeSlots();
//        instance2.initialiseTimeTable("9");
//        instance2.getEndDay();
//        instance2.getEndTime();
//        instance2.getStartDay();
//        instance2.getStartTime();
                
        System.out.println(instance.getStartTime());
        for(int i =0; i < Day.numDays ;i++){
            
            
            for(int j =0; j < EventTime.numHours ;j++){
              
                //fix later
                if(instance.getEvents(i, j).get(0) != null){
               instance.getEvents(i, j).get(0).getEventPriority().getPriority();
               System.out.println("hi its broke "+instance.getEvents(i, j).get(0).getEventPriority().getPriority());
                }
               assertEquals("9:00",instance.getStartTime().toString());
               assertEquals("17:00",instance.getEndTime().toString());
               assertEquals("Friday",instance.getEndDay().toString());
               assertEquals("Monday",instance.getStartDay().toString());
               
               
               //System.out.println("hi "+instance.getEvents(i, j).equals(instance2.getEvents(i, j)));
               //assertEquals(true,  instance.getEvents(i, j).equals(instance2.getEvents(i, j)));
               
              // System.out.println("dddddddddddddd"+instance2.getEvents(i, j));
              
            }
        }
        
//         expResult = TimeTable.getPreSetTimeTable()();
//        String result = TimeTable.getPreSetTimeTable().toString();
//        assertEquals(expResult, result);
        
    }

    /**
     * Test of conflictWithEvents method, of class TimeTable.
     */
    @Test
    public void testConflictWithEvents_3args() {
        System.out.println("conflictWithEvents");
       
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
        
        Time time = Time.valueOf("10:00:00");
        int duration = 1;
        int maxPriority = 3;
        TimeTable instance = TimeTable.getPreSetTimeTable();
        instance.setDisplayWeek("2015-03-02");
        instance.setupTimeSlots();
        instance.initialiseTimeTable("9");
        boolean expResult = true;
        boolean result = instance.conflictWithEvents(new Date(), time, duration, maxPriority);
        assertEquals(expResult, result);
       
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
        //String result = instance.createTimeTable(filterEvent, hideDetail, hideDetail);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nextSuggestedTimeSlot method, of class TimeTable.
     */
    @Test
    public void testNextSuggestedTimeSlot() {
        System.out.println("nextSuggestedTimeSlot");
        int meetingDuration = 1;
        int maxPriority = 3;
        boolean clearPrevious = false;
        TimeTable instance = TimeTable.getPreSetTimeTable();
        instance.setDisplayWeek("2015-03-02");
        instance.setupTimeSlots();
        instance.initialiseTimeTable("9");
        boolean expResult = true;
        boolean result = instance.nextSuggestedTimeSlot(meetingDuration, maxPriority, clearPrevious);
        assertEquals(expResult, result);
    }

    /**
     * Test of clearSuggestedTimeSlots method, of class TimeTable.
     */
    @Test
    public void testClearSuggestedTimeSlots() {
        System.out.println("clearSuggestedTimeSlots");
        TimeTable instance = TimeTable.getPreSetTimeTable();
        instance.setDisplayWeek("2015-03-02");
        instance.setupTimeSlots();
        instance.initialiseTimeTable("9");
        instance.clearSuggestedTimeSlots();
     
        
    }
    
}
