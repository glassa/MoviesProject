package glassa.tacoma.uw.edu.moviesproject;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Tony on 11/21/2016.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest

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
    public void testRegister() {
        Random random = new Random();
        //Generate an email address
        String email = "email" + (random.nextInt(4) + 1)
                + (random.nextInt(9) + 1) + (random.nextInt(7) + 1)
                + (random.nextInt(4) + 1) + (random.nextInt(100) + 1)
                + "@uw.edu";
        onView(withId(R.id.login_edit_text)).perform(typeText(email));
        onView(withId(R.id.pass_edit_text)).perform(typeText("test123"));

    }

}
