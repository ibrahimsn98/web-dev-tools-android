object CoreLibraries {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
}

object SupportLibraries {
    const val core = "androidx.core:core-ktx:${Versions.coreVersion}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintVersion}"
    const val preference = "androidx.preference:preference-ktx:${Versions.preferenceVersion}"
}

object Libraries {
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"

    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleVersion}"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycleVersion}"

    const val navigation = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"

    const val koin = "org.koin:koin-core:${Versions.koinVersion}"
    const val koinScope = "org.koin:koin-androidx-scope:${Versions.koinVersion}"
    const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koinVersion}"
    const val koinAndroidExt = "org.koin:koin-androidx-ext:${Versions.koinVersion}"

    const val room = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"

    const val paging = "androidx.paging:paging-runtime:${Versions.pagingVersion}"

    const val firebaseAnalytics = "com.google.firebase:firebase-analytics:${Versions.firebaseAnalyticsVersion}"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics:${Versions.crashlyticsVersion}"
    const val firebaseAds = "com.google.android.gms:play-services-ads:${Versions.firebaseAdsVersion}"
    const val consent = "com.google.android.ads.consent:consent-library:${Versions.consentVersion}"

    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttpVersion}"
    const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}"
}
