package com.anec.timecalc.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.anec.timecalc.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}
