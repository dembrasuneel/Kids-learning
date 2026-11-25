package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.CurriculumTrack
import com.example.data.model.SchoolClass
import com.example.data.repository.CurriculumRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read app name from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Little Genius", appName)
    }

    @Test
    fun `curriculum repository provides expected subjects per class`() {
        val repo = CurriculumRepository()
        assertEquals(14, repo.getSubjectsForClass(SchoolClass.PREP_1, CurriculumTrack.ISLAMIC_STUDIES).size)
        assertEquals(14, repo.getSubjectsForClass(SchoolClass.PREP_2, CurriculumTrack.ISLAMIC_STUDIES).size)
        assertEquals(7, repo.getSubjectsForClass(SchoolClass.CLASS_1, CurriculumTrack.ISLAMIC_STUDIES).size)
        assertEquals(8, repo.getSubjectsForClass(SchoolClass.CLASS_3, CurriculumTrack.ETHICS).size)
        assertEquals(9, repo.getSubjectsForClass(SchoolClass.CLASS_6, CurriculumTrack.ETHICS).size)
    }
}
