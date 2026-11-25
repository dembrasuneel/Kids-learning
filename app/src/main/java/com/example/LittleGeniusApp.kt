package com.example

import android.app.Application
import com.example.data.repository.CurriculumRepository
import com.example.data.repository.DashboardRepository
import com.example.util.SpeechHelper

class LittleGeniusApp : Application() {

    lateinit var curriculumRepository: CurriculumRepository
        private set

    lateinit var dashboardRepository: DashboardRepository
        private set

    lateinit var speechHelper: SpeechHelper
        private set

    override fun onCreate() {
        super.onCreate()
        curriculumRepository = CurriculumRepository()
        dashboardRepository = DashboardRepository(this)
        speechHelper = SpeechHelper(this)
    }

    override fun onTerminate() {
        speechHelper.shutdown()
        super.onTerminate()
    }
}
