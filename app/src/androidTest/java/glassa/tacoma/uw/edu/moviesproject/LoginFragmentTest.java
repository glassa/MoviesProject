package glassa.tacoma.uw.edu.moviesproject;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import glassa.tacoma.uw.edu.moviesproject.util.SharedPreferencesHelper;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static glassa.tacoma.uw.edu.moviesproject.R.id.login_fragment;
import static glassa.tacoma.uw.edu.moviesproject.R.layout.fragment_tab5_settings;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by Yunhang on 2016/12/3.
 * PLEASE LOGOUT BEFORE TEST
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginFragmentTest {
    private String email;
    /**
     * A JUnit {@link Rule @Rule} to launch your activity under test.
     * Rules are interceptors which are executed for each test method and will run before
     * any of your setup code in the {@link @Before} method.
     * <p>
     * {@link ActivityTestRule} will create and launch of the activity for you and also expose
     * the activity under test. To get a reference to the activity you can use
     * the {@link ActivityTestRule#getActivity()} method.
     */

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void set() {
        Random random = new Random();
        //Generate an email address
        email = "email" + (random.nextInt(7) + 1)
                + (random.nextInt(8) + 1) + (random.nextInt(9) + 1)
                + (random.nextInt(100) + 1) + (random.nextInt(4) + 1)
                + "@uw.edu";
    }
    @Test
    public void testARegister() {

        onView(withId(R.id.register_button_initial))
                .perform(click());

        onView(withId(R.id.register_user_edit_text))
                .perform(typeText(email));
        onView(withId(R.id.register_password1_edit_text))
                .perform(typeText("test1@#"));
        onView(withId(R.id.register_password2_edit_text))
                .perform(typeText("test1@#"));
        onView(withId(R.id.register_button_final))
                .perform(click());

    }

}
