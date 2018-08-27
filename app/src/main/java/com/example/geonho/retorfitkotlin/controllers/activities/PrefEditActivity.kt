package com.example.geonho.retorfitkotlin.controllers.activities

import android.os.Bundle
import android.preference.PreferenceFragment
import android.preference.PreferenceActivity
import com.example.geonho.retorfitkotlin.R
import com.example.geonho.retorfitkotlin.SharedPreferenceUtil


class PrefEditActivity : PreferenceActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentManager.beginTransaction().replace(android.R.id.content, SettingsFragment()).commit()

    }

    class SettingsFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.prefs)
            SharedPreferenceUtil.removePreferences(context,"token")
        }
    }

}