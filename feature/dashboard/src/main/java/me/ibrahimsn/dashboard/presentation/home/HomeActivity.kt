package me.ibrahimsn.dashboard.presentation.home

import android.content.Intent
import android.util.DisplayMetrics
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_home.*
import me.ibrahimsn.core.presentation.Constants
import me.ibrahimsn.core.presentation.base.BaseActivity
import me.ibrahimsn.core.presentation.extension.setNonPersonalized
import me.ibrahimsn.core.presentation.model.ConsentManager
import me.ibrahimsn.dashboard.R
import me.ibrahimsn.dashboard.presentation.settings.SettingsActivity

class HomeActivity : BaseActivity() {

    private lateinit var adView: AdView

    private val adSize: AdSize
        get() {
            val outMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(outMetrics)

            val density = outMetrics.density

            var adWidthPixels = adContainer.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
        }

    override fun layoutRes() = R.layout.activity_home

    override fun menuRes() = R.menu.menu_home

    override fun toolbar(): Toolbar? = toolbar

    override fun showBackButton(): Boolean = true

    override fun initView() {
        toolbar.setupWithNavController(findNavController(R.id.navHost))

        MobileAds.initialize(this)
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(listOf(Constants.ADMOB_TEST_DEVICE)).build()
        )

        adView = AdView(this)
        adView.adUnitId = Constants.BANNER_AD_UNIT_ID
        adView.adSize = adSize
        adContainer.addView(adView)

        val adRequest = AdRequest.Builder()
        ConsentManager.getConsentStatus(this, true) { isPersonalized ->
            if (!isPersonalized) adRequest.setNonPersonalized()
            adView.loadAd(adRequest.build())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
