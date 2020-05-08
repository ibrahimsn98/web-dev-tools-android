package me.ibrahimsn.core.domain.source

import android.content.SharedPreferences

interface LocalDataSource {

    interface Preferences : LocalDataSource {

        val sharedPreferences: SharedPreferences

        fun isFollowRedirect(): Boolean

        fun getConsentStatus(): Boolean

        fun hasConsentStatus(): Boolean

        fun setConsentStatus(consentStatus: Boolean)

        companion object {
            const val KEY_FOLLOW_REDIRECT = "key_follow_redirect"
            const val KEY_CONSENT_STATUS = "key_consent_status"
            const val KEY_HAS_CONSENT_STATUS = "key_has_consent_status"
        }
    }
}
