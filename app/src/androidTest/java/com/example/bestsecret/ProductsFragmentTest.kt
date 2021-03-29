package com.example.bestsecret

import androidx.fragment.app.FragmentFactory
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.bestsecret.ui.view.ProductsFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class ProductsFragmentTest {

    @get:Rule()
    var hiltRule = HiltAndroidRule(this)

    lateinit var scenario: Unit


    @Before
    fun init() {
        hiltRule.inject()
        scenario = launchFragmentInHiltContainer<ProductsFragment> (factory = FragmentFactory())
    }

    @Test
    fun viewProductFragmentTest() {
        // VERIFY
        onView(withId(R.id.product_recycler_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}