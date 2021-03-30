package com.example.bestsecret.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.bestsecret.R
import com.example.bestsecret.ui.adapter.ProductsAdapter
import com.example.bestsecret.ui.view.MainActivity
import com.example.bestsecret.utils.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class ProductsFragmentTest {

    private val LIST_ITEM_IN_TEST = 3

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        hiltRule.inject()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun isProductsFragmentVisible_onAppLaunch_test() {
        // VERIFY
        onView(withId(R.id.product_recycler_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun selectListItem_isProductDetailFragmentVisible_test() {
        // We should add a sleep here but Idling is not working fine.
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        // Click
        onView(withId(R.id.product_recycler_view))
            .perform(actionOnItemAtPosition<ProductsAdapter.ViewHolder>(LIST_ITEM_IN_TEST, click()))

        // VERIFY
        onView(withId(R.id.product_details_container))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun backNavigation_toProductsFragment_test() {
        // We should add a sleep here but Idling is not working fine.
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        // Click
        onView(withId(R.id.product_recycler_view))
            .perform(actionOnItemAtPosition<ProductsAdapter.ViewHolder>(LIST_ITEM_IN_TEST, click()))

        // VERIFY
        onView(withId(R.id.product_details_container))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        pressBack()

        // VERIFY
        onView(withId(R.id.product_recycler_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}