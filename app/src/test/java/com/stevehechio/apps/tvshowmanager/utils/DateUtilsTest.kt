package com.stevehechio.apps.tvshowmanager.utils

import org.junit.Assert.*
import org.junit.Test

/**
 * Created by stevehechio on 9/3/21
 */
class DateUtilsTest{
    @Test
    fun formatGMTDateStr() {
        val date = "2014-11-07T00:00:00.000Z"
        val dateString: String = DateUtils.formatGMTDateStr(date)
        assertEquals("07 November 2014",dateString)
    }
}