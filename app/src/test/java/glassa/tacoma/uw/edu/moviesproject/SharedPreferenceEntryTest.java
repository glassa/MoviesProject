package glassa.tacoma.uw.edu.moviesproject;

import android.accounts.Account;

import org.junit.Before;
import org.junit.Test;

import glassa.tacoma.uw.edu.moviesproject.util.SharedPreferenceEntry;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Yunhang on 2016/12/5.
 */

public class SharedPreferenceEntryTest {
    private SharedPreferenceEntry myTest;
    @Before
    public void set() {
        myTest = new SharedPreferenceEntry (true, "test1");
    }

    @Test
    public void testSharedPreferenceEntryConstructor() {
        assertNotNull(myTest);
    }

    @Test
    public void testIsLoggedIn() {
        boolean res = myTest.isLoggedIn();
        assertTrue(res);
    }

    @Test
    public void testGetUsername() {
        String res = myTest.getUsername();
        assertEquals(res, "test1");
    }
}
