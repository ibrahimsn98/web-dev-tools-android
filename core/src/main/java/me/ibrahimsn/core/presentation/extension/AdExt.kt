package me.ibrahimsn.core.presentation.extension

import android.os.Bundle
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest

fun AdRequest.Builder.setNonPersonalized() {
    this.addNetworkExtrasBundle(AdMobAdapter::class.java, Bundle().apply {
        putString("npa", "1")
    })
}
