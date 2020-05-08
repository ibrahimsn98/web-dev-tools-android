package me.ibrahimsn.core.presentation.model

import android.content.Context
import com.google.ads.consent.*
import me.ibrahimsn.core.domain.source.LocalDataSource
import me.ibrahimsn.core.presentation.Constants
import java.net.MalformedURLException
import java.net.URL

object ConsentManager {

    private var preferences: LocalDataSource.Preferences? = null

    fun setupPreferences(preferences: LocalDataSource.Preferences?) {
        this.preferences = preferences
    }

    fun setConsentStatus(context: Context, consentStatus: Boolean) {
        val consentInformation = ConsentInformation.getInstance(context).apply {
            addTestDevice(Constants.ADMOB_TEST_DEVICE)
        }
        consentInformation.consentStatus = if (consentStatus) {
            ConsentStatus.PERSONALIZED
        } else {
            ConsentStatus.NON_PERSONALIZED
        }
        preferences?.setConsentStatus(consentStatus)
    }

    fun getConsentStatus(context: Context, displayForm: Boolean, isPersonalized: (Boolean) -> Unit) {
        if (preferences?.hasConsentStatus() == true) {
            isPersonalized.invoke(preferences?.getConsentStatus() == true)
            return
        }

        val consentInformation = ConsentInformation.getInstance(context).apply {
            addTestDevice(Constants.ADMOB_TEST_DEVICE)
        }

        consentInformation.requestConsentInfoUpdate(
            arrayOf(Constants.PUBLISHER_ID),
            object : ConsentInfoUpdateListener {
                override fun onConsentInfoUpdated(consentStatus: ConsentStatus) {
                    if (ConsentInformation.getInstance(context).isRequestLocationInEeaOrUnknown) {
                        when (consentStatus) {
                            ConsentStatus.UNKNOWN -> {
                                if (displayForm) {
                                    displayConsentForm(context, isPersonalized)
                                } else {
                                    isPersonalized.invoke(false)
                                }
                            }
                            ConsentStatus.PERSONALIZED -> {
                                isPersonalized.invoke(true)
                                preferences?.setConsentStatus(true)
                            }
                            ConsentStatus.NON_PERSONALIZED -> {
                                isPersonalized.invoke(false)
                                preferences?.setConsentStatus(false)
                            }
                        }
                    } else {
                        isPersonalized.invoke(true)
                        preferences?.setConsentStatus(true)
                    }
                }

                override fun onFailedToUpdateConsentInfo(errorDescription: String) {
                    isPersonalized.invoke(false)
                }
            }
        )
    }

    private fun displayConsentForm(context: Context, isPersonalized: (Boolean) -> Unit?) {
        var form: ConsentForm? = null
        var privacyUrl: URL? = null

        try {
            privacyUrl = URL(Constants.PRIVACY_URL)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

        form = ConsentForm.Builder(context, privacyUrl)
            .withListener(object : ConsentFormListener() {
                override fun onConsentFormLoaded() {
                    form?.show()
                }

                override fun onConsentFormOpened() {
                    // no-op
                }

                override fun onConsentFormClosed(
                    consentStatus: ConsentStatus,
                    userPrefersAdFree: Boolean
                ) {
                    isPersonalized.invoke(consentStatus == ConsentStatus.PERSONALIZED)
                }

                override fun onConsentFormError(errorDescription: String) {
                    isPersonalized.invoke(false)
                }
            })
            .withPersonalizedAdsOption()
            .withNonPersonalizedAdsOption()
            .build()

        form?.load()
    }
}
