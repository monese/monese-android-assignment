package com.monese.assignment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.monese.assignment.R
import com.monese.assignment.ui.launches.LaunchesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AssignmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.assignment_activity)
    }
}