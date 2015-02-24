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
     * Test of md2 method, of class Hash.
     */
    @Test
    public void testMd2() {
        System.out.println("md2");
        String message = "123";
        String expResult = "ef1fedf5d32ead6b7aaf687de4ed1b71";
        String result = Hash.md2(message);
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

    /**
     * Test of sha224 method, of class Hash.
     */
    @Test
    public void testSha224() {
        System.out.println("sha224");
        String message = "";
        String expResult = "";
        String result = Hash.sha224(message);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sha256 method, of class Hash.
     */
    @Test
    public void testSha256() {
        System.out.println("sha256");
        String message = "123";
        String expResult = "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3";
        String result = Hash.sha256(message);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of sha384 method, of class Hash.
     */
    @Test
    public void testSha384() {
        System.out.println("sha384");
        String message = "123";
        String expResult = "9a0a82f0c0cf31470d7affede3406cc9aa8410671520b727044eda15b4c25532a9b5cd8aaf9cec4919d76255b6bfb00f";
        String result = Hash.sha384(message);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of sha512 method, of class Hash.
     */
    @Test
    public void testSha512() {
        System.out.println("sha512");
        String message = "123";
        String expResult = "3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2";
        String result = Hash.sha512(message);
        assertEquals(expResult, result);
        
    }
    
}
