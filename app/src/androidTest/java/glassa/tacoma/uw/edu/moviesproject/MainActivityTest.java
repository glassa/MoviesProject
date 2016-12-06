package glassa.tacoma.uw.edu.moviesproject;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Yunhang on 2016/12/3.
 * PLEASE LOGIN BEFORE TEST
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
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
    public void testFillowersButton() {
        onView(withId(R.id.followers_button))
                .perform(click());
    }

    @Test
    public void testFollowingButton() {
        onView(withId(R.id.following_button))
                .perform(click());
    }

    @Test
    public void testRattingButton() {
        onView(withId(R.id.ratings_button))
                .perform(click());
    }
}
