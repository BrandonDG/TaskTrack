package com.brandon.tasktrack;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Brandon on 2017-12-06.
 */

@RunWith(AndroidJUnit4.class)
public class InterfaceTest {

    @Rule
    public ActivityTestRule<TaskActivity> mActivityRule =
            new ActivityTestRule<>(TaskActivity.class);

    @Test
    public void ensureAddTaskWorks() {
        onView(withId(R.id.main_addtaskbutton)).perform(click());
        onView(withId(R.id.newTaskModal_nametextbox)).perform(typeText("Swimming"), closeSoftKeyboard());
        onView(withId(R.id.newTaskModal_typetextbox)).perform(typeText("Exercise"), closeSoftKeyboard());
        onView(withId(R.id.newTaskModal_fromtimetextbox)).perform(typeText("1"), closeSoftKeyboard());
        onView(withId(R.id.newTaskModal_totimetextbox)).perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.newTaskModal_addbutton)).perform(click());
        Matchers m = new Matchers();
        onView(withId(R.id.tasklist)).check(ViewAssertions.matches(m.withAtLeastListSize(1)));
    }

    public static class Matchers {
        public Matcher<View> withAtLeastListSize (final int size) {
            return new TypeSafeMatcher<View>() {
                @Override public boolean matchesSafely (final View view) {
                    return ((ListView) view).getCount () > size;
                }

                @Override public void describeTo (final Description description) {
                    description.appendText ("ListView should have " + size + " items");
                }
            };
        }
    }
}
