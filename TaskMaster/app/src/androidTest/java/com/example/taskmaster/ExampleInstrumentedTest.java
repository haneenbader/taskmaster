package com.example.taskmaster;

import android.content.Context;

import androidx.test.espresso.Espresso;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.not;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    // test when change setting name it displayed on the home page
    @Test
    public void usernameHomePage() {
        // 1- press setting btn at home
        onView(withId(R.id.hometosetting)).perform(click());
        // 2- fill the field on setting page
        onView(withId(R.id.edittextusername))
                .perform(typeText("Haneen"), closeSoftKeyboard());
        // 3- click save btn on setting page
        onView(withId(R.id.savesetting)).perform(click());
        // 4- press back
        Espresso.pressBackUnconditionally();
        // 5- see the result on home page
        onView(withId(R.id.textViewusername)).check(matches(withText("Haneen’s tasks")));
    }

    // test if button on Home page is displayed
    @Test
    public void SettingButton() {
        // 1- press the setting btn on home page
        onView(withId(R.id.hometosetting)).perform(click());
        // 2- check if the setting text field is displayed
        onView(withId(R.id.edittextusername)).check(matches(isDisplayed()));


    }

    // test if button Add Task  on home page is displayed
    @Test
    public void AddTaskButton() {
        // 1- press the Add Task btn on home page
        onView(withId(R.id.addtaskhome)).perform(click());
        // 2- check if the title -body- state text field is displayed
        onView(withId(R.id.tastTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.taskDescription)).check(matches(isDisplayed()));
        onView(withId(R.id.taskState)).check(matches(isDisplayed()));
    }
}