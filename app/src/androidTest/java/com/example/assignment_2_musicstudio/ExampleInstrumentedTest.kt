import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.assignment_2_musicstudio.MainActivity
import com.example.assignment_2_musicstudio.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testConditionSelection() {
        // Select "New" in the RadioGroup
        onView(withId(R.id.radio_new)).perform(click())

        // Check that the "New" option is selected
        onView(withId(R.id.radio_new)).check(matches(isChecked()))

        // Check that "Used" option is not selected
        onView(withId(R.id.radio_used)).check(matches(isNotChecked()))
    }
    @Test
    fun testAttributeSelection() {
        // Click on an attribute chip (e.g., "Acoustic")
        onView(withText("Acoustic")).perform(click())

        // Verify the chip is selected
        onView(withText("Acoustic")).check(matches(isChecked()))

        // Click on another chip (e.g., "Electric")
        onView(withText("Electric")).perform(click())

        // Verify that both chips can be selected
        onView(withText("Electric")).check(matches(isChecked()))
    }
    @Test
    fun testInstrumentCondition() {
        // Launch the activity
        activityRule.scenario.onActivity { activity ->
            // Perform actions or assertions here
        }
        // Select the "Used" radio button
        onView(withId(R.id.radio_used)).perform(click())

        // Verify that the "Used" option is selected
        onView(withId(R.id.radio_used)).check(matches(isChecked()))

        // Now select the "New" radio button
        onView(withId(R.id.radio_new)).perform(click())

        // Verify that the "New" option is selected
        onView(withId(R.id.radio_new)).check(matches(isChecked()))

        // Optionally, you can also verify that "Used" is no longer selected
        onView(withId(R.id.radio_used)).check(matches(isNotChecked()))
    }
    @Test
    fun testNextButtonCyclesThroughItems() {
        // Launch the activity
        activityRule.scenario.onActivity { activity ->
            // Perform actions or assertions here
        }

        // Click the next button
        onView(withId(R.id.next_button)).perform(click())

        // Verify that the displayed item's name or another attribute changes
        onView(withId(R.id.item_name)).check(matches(withText("Expected Next Item Name"))) // Replace with actual item name

        // Optionally, verify that other attributes change (e.g., price or image)
        onView(withId(R.id.item_price)).check(matches(withText("500 credits"))) // Adjust as needed
    }
    @Test
    fun testBorrowButtonWithRequiredAttribute() {
        // Launch the activity
        activityRule.scenario.onActivity { activity ->
            // Perform actions or assertions here
        }

        // Attempt to click the borrow button without selecting any condition
        onView(withId(R.id.borrow_button)).perform(click())

        // Check that an error message is displayed or borrowing is not allowed
        // Replace with actual behavior, whether it's a Toast, Snackbar, or Error Text
        onView(withText("Please select an instrument condition")).check(matches(isDisplayed()))

        // Now select "Used" as the condition
        onView(withId(R.id.radio_used)).perform(click())

        // Click the borrow button again
        onView(withId(R.id.borrow_button)).perform(click())

        // Assuming clicking borrow takes the user to the next screen, verify a transition or price update
        onView(withId(R.id.detail_seekbar_value)).check(matches(withText("100 credits"))) // Adjust as needed
    }

}
