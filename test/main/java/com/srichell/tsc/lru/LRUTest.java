package com.srichell.tsc.lru;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by sridhar on 6/8/17.
 */
public class LRUTest {
    LRU<LRUEntry, String, String> lru;

    public LRU<LRUEntry, String, String> getLru() {
        return lru;
    }

    @BeforeMethod
    public void setUp() throws Exception {
        lru = new LRU<LRUEntry, String, String>(3);

        // Fill the LRU to capacity
        getLru().push(new LRUEntry("USA", "Washington DC"));
        getLru().push(new LRUEntry("England", "London"));
        getLru().push(new LRUEntry("Japan", "Tokyo"));
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test(threadPoolSize = 3, invocationCount = 6, timeOut = 1000)
    public void testEvictionFromLRUMultiThreaded() throws Exception {
        getLru().push(new LRUEntry("Canada", "Toronto"));
        // Make sure the earliest entry is Absent
        String value = getLru().lookup("USA");
        Assert.assertEquals((value == null), true);
    }

    @Test
    public void testLookup() throws Exception {
        getLru().push(new LRUEntry("India", "New Delhi"));

        String value = getLru().lookup("India");
        Assert.assertEquals((value == "New Delhi"), true);

    }

}