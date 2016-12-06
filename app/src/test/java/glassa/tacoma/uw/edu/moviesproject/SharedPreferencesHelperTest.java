package glassa.tacoma.uw.edu.moviesproject;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import glassa.tacoma.uw.edu.moviesproject.util.SharedPreferenceEntry;
import glassa.tacoma.uw.edu.moviesproject.util.SharedPreferencesHelper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;


/**
 * Created by yunhang on 12/23/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class SharedPreferencesHelperTest {

    private static final boolean TEST_LOGGED_IN = true;
    private static final String TEST_EMAIL = "test@email.com";
    private SharedPreferenceEntry mSharedPreferenceEntry;
    private SharedPreferencesHelper mMockSharedPreferencesHelper;
    private SharedPreferencesHelper mMockBrokenSharedPreferencesHelper;

    @Mock
    SharedPreferences mMockSharedPreferences;
    @Mock
    SharedPreferences mMockBrokenSharedPreferences;
    @Mock
    SharedPreferences.Editor mMockEditor;
    @Mock
    SharedPreferences.Editor mMockBrokenEditor;
    @Before
    public void initMocks() {
        mSharedPreferenceEntry = new SharedPreferenceEntry(TEST_LOGGED_IN,
                TEST_EMAIL);

        mMockSharedPreferencesHelper = createMockSharedPreference();

        mMockBrokenSharedPreferencesHelper = createBrokenMockSharedPreference();
    }

    @Test
    public void sharedPreferencesHelper_SaveAndReadPersonalInformation() {
        boolean success = mMockSharedPreferencesHelper.savePersonalInfo(mSharedPreferenceEntry);

        assertThat("Checking that SharedPreferenceEntry.save... returns true",
                success, is(true));

        SharedPreferenceEntry savedSharedPreferenceEntry =
                mMockSharedPreferencesHelper.getLoginInfo();

        assertThat("Checking that SharedPreferenceEntry.isLogged has been persisted and read correctly",
                mSharedPreferenceEntry.isLoggedIn(),
                is(equalTo(savedSharedPreferenceEntry.isLoggedIn())));
        assertThat("Checking that SharedPreferenceEntry.email has been persisted and read "
                        + "correctly",
                mSharedPreferenceEntry.getUsername(),
                is(equalTo(savedSharedPreferenceEntry.getUsername())));
    }

    @Test
    public void sharedPreferencesHelper_SavePersonalInformationFailed_ReturnsFalse() {
        boolean success =
                mMockBrokenSharedPreferencesHelper.savePersonalInfo(mSharedPreferenceEntry);
        assertThat("Makes sure writing to a broken SharedPreferencesHelper returns false", success,
                is(false));
    }

    private SharedPreferencesHelper createMockSharedPreference() {
        when(mMockSharedPreferences.getBoolean(eq(SharedPreferencesHelper.KEY_LOGGED_IN), anyBoolean()))
                .thenReturn(mSharedPreferenceEntry.isLoggedIn());
        when(mMockSharedPreferences.getString(eq(SharedPreferencesHelper.KEY_USERNAME), anyString()))
                .thenReturn(mSharedPreferenceEntry.getUsername());

        when(mMockEditor.commit()).thenReturn(true);
        when(mMockSharedPreferences.edit()).thenReturn(mMockEditor);
        return new SharedPreferencesHelper(mMockSharedPreferences);
    }

    private SharedPreferencesHelper createBrokenMockSharedPreference() {
        when(mMockBrokenEditor.commit()).thenReturn(false);
        when(mMockBrokenSharedPreferences.edit()).thenReturn(mMockBrokenEditor);
        return new SharedPreferencesHelper(mMockBrokenSharedPreferences);
    }
}