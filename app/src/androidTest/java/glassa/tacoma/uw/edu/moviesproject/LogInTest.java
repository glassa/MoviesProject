package glassa.tacoma.uw.edu.moviesproject;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by Tony on 11/21/2016.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LogInTest {

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

    @Test
    public void testARegister() {
        Random random = new Random();
        //Generate an email address
        String email = "email" + (random.nextInt(4) + 1)
                + (random.nextInt(9) + 1) + (random.nextInt(7) + 1)
                + (random.nextInt(4) + 1) + (random.nextInt(100) + 1)
                + "@uw.edu";

        onView(withId(R.id.register_button_initial)).perform(click());

        onView(withId(R.id.register_user_edit_text)).perform(typeText(email));
        onView(withId(R.id.register_password1_edit_text)).perform(typeText("test123"));
        onView(withId(R.id.register_password2_edit_text)).perform(typeText("test123"));

        onView(withId(R.id.register_button_final)).perform(click());

        onView(withText("Registering..."))
                .inRoot(withDecorView(not(is(
                        mActivityRule.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testBTabs() {

//        onView(withId(R.id.login_edit_text)).perform(typeText(email));
//        onView(withId(R.id.pass_edit_text)).perform(typeText("test123"));
    }

}
