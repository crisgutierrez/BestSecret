package com.example.bestsecret.ui

import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.bestsecret.R
import com.example.bestsecret.launchFragmentInHiltContainer
import com.example.bestsecret.ui.view.ProductDetailsFragment
import com.example.bestsecret.utils.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class ProductDetailsFragmentTest {

    private val CORRECT_PRODUCT_ITEM_ID = 1
    private val CORRECT_PRODUCT_NO_DEAL_STAMP_ITEM_ID = 3
    private val CORRECT_PRODUCT_NO_STOCK_ITEM_ID = 18

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

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
    fun isProductDetailsOpeningCorrectly_test() {
        // SETUP
        val bundle = Bundle()
        bundle.putSerializable("productId", CORRECT_PRODUCT_ITEM_ID)

        val scenario = launchFragmentInHiltContainer<ProductDetailsFragment>(
            fragmentArgs = bundle,
            factory = FragmentFactory()
        )

        // VERIFY
        onView(withId(R.id.product_details_container))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun isDealStampWhenDiscountIsHigh_test() {
        // SETUP
        val bundle = Bundle()
        bundle.putSerializable("productId", CORRECT_PRODUCT_ITEM_ID)

        val scenario = launchFragmentInHiltContainer<ProductDetailsFragment>(
            fragmentArgs = bundle,
            factory = FragmentFactory()
        )

        // VERIFY
        onView(withId(R.id.product_details_discount))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun isDealStampGoneWhenDiscountIsLow_test() {
        // SETUP
        val bundle = Bundle()
        bundle.putSerializable("productId", CORRECT_PRODUCT_NO_DEAL_STAMP_ITEM_ID)

        val scenario = launchFragmentInHiltContainer<ProductDetailsFragment>(
            fragmentArgs = bundle,
            factory = FragmentFactory()
        )

        // VERIFY
        onView(withId(R.id.product_details_discount))
            .check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun isAddCartVisible_test() {
        // SETUP
        val bundle = Bundle()
        bundle.putSerializable("productId", CORRECT_PRODUCT_ITEM_ID)

        val scenario = launchFragmentInHiltContainer<ProductDetailsFragment>(
            fragmentArgs = bundle,
            factory = FragmentFactory()
        )

        // VERIFY
        onView(withId(R.id.product_details_add_cart_button))
            .check(matches(withText("Add to cart")))
    }

    @Test
    fun isNotVisibleWhenProductHasNoStockVisible_test() {
        // SETUP
        val bundle = Bundle()
        bundle.putSerializable("productId", CORRECT_PRODUCT_NO_STOCK_ITEM_ID)

        val scenario = launchFragmentInHiltContainer<ProductDetailsFragment>(
            fragmentArgs = bundle,
            factory = FragmentFactory()
        )

        // VERIFY
        onView(withId(R.id.product_details_add_cart_button))
            .check(matches(not(withText("Add to cart"))))
    }

    @Test
    fun isProductMarkAsFavorite_test() {
        // SETUP
        val bundle = Bundle()
        bundle.putSerializable("productId", CORRECT_PRODUCT_ITEM_ID)

        val scenario = launchFragmentInHiltContainer<ProductDetailsFragment>(
            fragmentArgs = bundle,
            factory = FragmentFactory()
        )

        // VERIFY
        onView(withId(R.id.product_details_favorite))
            .check(ViewAssertions.matches(not(ViewMatchers.isSelected())))

        // Click
        onView(withId(R.id.product_details_favorite))
            .perform(click())

        // VERIFY
        onView(withId(R.id.product_details_favorite))
            .check(ViewAssertions.matches(ViewMatchers.isSelected()))
    }

}