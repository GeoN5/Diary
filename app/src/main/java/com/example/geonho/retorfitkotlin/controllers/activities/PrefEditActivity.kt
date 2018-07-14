package com.example.geonho.retorfitkotlin.controllers.activities

import android.os.Bundle
import android.preference.PreferenceFragment
import android.app.Activity
import com.example.geonho.retorfitkotlin.R


class PrefEditActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentManager.beginTransaction().replace(android.R.id.content, SettingsFragment()).commit()
    }

    class SettingsFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.prefs)
        }
    }

}