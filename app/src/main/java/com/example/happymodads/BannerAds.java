package com.example.happymodads;

import android.content.Context;
import android.widget.LinearLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class BannerAds {
    private AdView adView;
    private Context context;
    private LinearLayout adContainer;

    public BannerAds(Context context, LinearLayout adContainer) {
        this.context = context;
        this.adContainer = adContainer;
    }

    public void initBanner() {
        // Create an ad view
        adView = new AdView(context);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(AdConstants.BANNER_AD_UNIT_ID);

        // Add the ad view to the container
        adContainer.addView(adView);

        // Create an ad request
        AdRequest adRequest = new AdRequest.Builder().build();

        // Load the ad
        adView.loadAd(adRequest);
    }

    public void showBanner() {
        if (adView != null) {
            adView.setVisibility(View.VISIBLE);
        }
    }

    public void hideBanner() {
        if (adView != null) {
            adView.setVisibility(View.GONE);
        }
    }

    public void destroyBanner() {
        if (adView != null) {
            adView.destroy();
            adView = null;
        }
    }
} 