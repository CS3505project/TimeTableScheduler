/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolsPackage;

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
public class HashTest {
    
    public HashTest() {
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
     * Test of getHash method, of class Hash.
     */
    @Test
    public void testGetHash() {
        System.out.println("getHash");
        String message = "321";
        String hashType = "MD2";
        String expResult = "dbfe5d929e5729337e5f974d064154fe";
        String result = Hash.getHash(message, hashType);
        assertEquals(expResult, result);
        
    }

    

    /**
     * Test of md5 method, of class Hash.
     */
    @Test
    public void testMd5() {
        System.out.println("md5");
        String message = "123";
        String expResult = "202cb962ac59075b964b07152d234b70";
        String result = Hash.md5(message);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of sha1 method, of class Hash.
     */
    @Test
    public void testSha1() {
        System.out.println("sha1");
        String message = "123";
        String expResult = "40bd001563085fc35165329ea1ff5c5ecbdbbeef";
        String result = Hash.sha1(message);
        assertEquals(expResult, result);
       
    }

}
