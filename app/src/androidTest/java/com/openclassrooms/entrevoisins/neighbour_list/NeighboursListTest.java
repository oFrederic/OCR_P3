
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;
import com.openclassrooms.entrevoisins.utils.ProfileViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static final int NEIGHBOUR_ITEMS_COUNT = 12;
    private static final int FAVORITES_ITEM_COUNT = 5;
    private static final String DESCRIPTION = "A propos de moi";
    private static final String USER_NAME = "Caroline";

    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule<>(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item.
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown.
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2.
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed()))
                .check(withItemCount(NEIGHBOUR_ITEMS_COUNT));
        // When : Perform a click on a delete icon, use a custom ViewAction.
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : The number of element is 11.
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed()))
                .check(withItemCount(NEIGHBOUR_ITEMS_COUNT - 1));
    }

    /**
     * When we click on the avatar, the profile intent is showing.
     */
    @Test
    public void myNeighboursList_profileAction_shouldShowProfileIntent() {
        // Perform a click on avatar at position 0, use a custom ViewAction.
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new ProfileViewAction()));
        // We check if we match the TextView with "A propos de moi".
        onView(withId(R.id.activity_user_details_about_me_txt))
                .check(matches(withText(DESCRIPTION)));
    }

    /**
     * When we click on the avatar "Caroline", the profile intent is showing the correct Profile.
     */
    @Test
    public void myNeighboursList_profileAction_shouldShowCorrectProfile() {
        // Perform a click on avatar at position 0 "Caroline", use a custom ViewAction.
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new ProfileViewAction()));
        // We check if we match the TextView with "Caroline".
        onView(withId(R.id.activity_user_details_name1_txt))
                .check(matches(withText(USER_NAME)));
    }

    /**
     * We ensure that our favorites list is displaying correct data.
     */
    @Test
    public void myNeighboursList_favoriteList_shouldDeleteOneFavoriteProfile() {
        // Perform a swipe left to access the favorites list.
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(swipeLeft());
        // Check that the list has 5 favorite user.
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed()))
                .check(withItemCount(FAVORITES_ITEM_COUNT));
        // Perform a click on the delete icon of user position 1, use a custom ViewAction.
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Check that the list has 4 favorite user.
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed()))
                .check(withItemCount(FAVORITES_ITEM_COUNT - 1));
    }
}