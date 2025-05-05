package com.example.happymodads;

import android.content.Context;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class InterstitialAds {
    private InterstitialAd interstitialAd;
    private Context context;

    public InterstitialAds(Context context) {
        this.context = context;
        MobileAds.initialize(context);
    }

    public void initInterstitial() {
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(AdConstants.INTERSTITIAL_AD_UNIT_ID);

        // Create an ad request
        AdRequest adRequest = new AdRequest.Builder().build();

        // Load the interstitial ad
        interstitialAd.loadAd(adRequest);

        // Set up ad listener
        interstitialAd.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdLoaded() {
                // Ad loaded successfully
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Ad failed to load
            }

            @Override
            public void onAdClosed() {
                // Reload the ad
                initInterstitial();
            }
        });
    }

    public void showInterstitial() {
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            // Ad is not ready yet
            initInterstitial();
        }
    }
} 