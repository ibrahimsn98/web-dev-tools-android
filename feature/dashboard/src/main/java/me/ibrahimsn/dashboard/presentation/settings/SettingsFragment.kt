package me.ibrahimsn.dashboard.presentation.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import me.ibrahimsn.core.presentation.Constants
import me.ibrahimsn.core.presentation.model.ConsentManager
import me.ibrahimsn.dashboard.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val consentPreference = findPreference<SwitchPreference>("key_consent_status_value")
        val privacyPreference = findPreference<Preference>("key_privacy_policy")

        ConsentManager.getConsentStatus(context!!, false) {
            consentPreference?.isChecked = it
        }

        consentPreference?.setOnPreferenceChangeListener { _, newValue ->
            ConsentManager.setConsentStatus(context!!, newValue as Boolean)
            true
        }

        privacyPreference?.setOnPreferenceClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.PRIVACY_URL)))
            true
        }
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}
