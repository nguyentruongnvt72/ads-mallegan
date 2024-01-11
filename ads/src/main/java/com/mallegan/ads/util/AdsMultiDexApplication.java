package com.mallegan.ads.util;

import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AdsMultiDexApplication extends MultiDexApplication {


    private GoogleMobileAdsConsentManager googleMobileAdsConsentManager;

    private final AtomicBoolean isMobileAdsInitializeCalled = new AtomicBoolean(false);
    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.BUILD_DEBUG = buildDebug();
        Log.i("Application", " run debug: " + AppUtil.BUILD_DEBUG);
//        Admob.getInstance().initAdmod(this, getListTestDeviceId());

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        googleMobileAdsConsentManager =
                GoogleMobileAdsConsentManager.getInstance(getApplicationContext());
        googleMobileAdsConsentManager.gatherConsent(
                ,
                consentError -> {
                    if (consentError != null) {
                        // Consent not obtained in current session.
//                        Log.w(
//                                TAG,
//                                String.format(
//                                        "%s: %s",
//                                        consentError.getErrorCode(),
//                                        consentError.getMessage()));
                    }

//                    startGame();

                    if (googleMobileAdsConsentManager.canRequestAds()) {
                        initializeMobileAdsSdk();
                    }

                    if (googleMobileAdsConsentManager.isPrivacyOptionsRequired()) {
                        // Regenerate the options menu to include a privacy setting.
//                        invalidateOptionsMenu();
                    }
                });

        // This sample attempts to load ads using consent obtained in the previous session.
        if (googleMobileAdsConsentManager.canRequestAds()) {
            initializeMobileAdsSdk();
        }
        if(enableAdsResume()) {
            AppOpenManager.getInstance().init(this, getOpenAppAdId());
        }
    }

    public abstract boolean enableAdsResume();
    public abstract List<String> getListTestDeviceId();
    public abstract String getOpenAppAdId();
    public abstract Boolean buildDebug();

    private void initializeMobileAdsSdk() {
        if (isMobileAdsInitializeCalled.getAndSet(true)) {
            return;
        }

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(
                this,
                new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {
                        // Load an ad.
//                        loadAd();
                    }
                });
    }
}
