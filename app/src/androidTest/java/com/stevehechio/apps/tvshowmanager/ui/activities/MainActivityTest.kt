package com.stevehechio.apps.tvshowmanager.ui.activities

import org.junit.Assert.*
import androidx.test.core.app.ActivityScenario

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test

import org.junit.runner.RunWith




/**
 * Created by stevehechio on 9/5/21
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    @Test
    fun appLaunchesWithoutCrash() {
        ActivityScenario.launch(MainActivity::class.java)
    }
}