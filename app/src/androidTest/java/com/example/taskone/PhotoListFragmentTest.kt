package com.example.taskone

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth
import com.google.gson.Gson
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class PhotoListFragmentTest {

    /*
        NavController navigates to the photo fragment successfully on first RecyclerView item clicked
     */
    @Test
    fun clickOnPhoto_navigateToPhotoListFragment() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val titleScenario = launchFragmentInContainer<PhotoListFragment>()

        titleScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.navigation_main)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<PhotoListAdapter.PhotoListViewHolder>(
                0,
                ViewActions.click()
            )
        )

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.photoFragment)
    }

    /*
        check if last URL of 'dog_urls.json' is loaded correctly
     */
    @Test
    fun getUrlsFromAssets_worksCorrectly() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val json =
            context.resources.openRawResource(R.raw.dog_urls).bufferedReader().use { it.readText() }
        val gson = Gson()
        val urls = gson.fromJson(
            json,
            DogUrls::class.java
        ).urls

        val lastUrl = "https://images.dog.ceo/breeds/hound-walker/n02089867_2039.jpg"

        Truth.assertThat(urls.last()).isEqualTo(lastUrl)
    }
}