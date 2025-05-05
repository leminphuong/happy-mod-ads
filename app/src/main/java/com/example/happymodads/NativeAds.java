package com.example.happymodads;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

public class NativeAds {
    private Context context;
    private UnifiedNativeAd nativeAd;
    private View adContainer;

    public NativeAds(Context context, View adContainer) {
        this.context = context;
        this.adContainer = adContainer;
        MobileAds.initialize(context);
    }

    public void initNativeAd() {
        AdLoader.Builder builder = new AdLoader.Builder(context, AdConstants.NATIVE_AD_UNIT_ID);

        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                nativeAd = unifiedNativeAd;
                populateNativeAdView(unifiedNativeAd);
            }
        });

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Handle the failure
            }
        }).build();

        // Create an ad request
        AdRequest adRequest = new AdRequest.Builder().build();

        // Load the native ad
        adLoader.loadAd(adRequest);
    }

    private void populateNativeAdView(UnifiedNativeAd nativeAd) {
        // Inflate the native ad layout
        UnifiedNativeAdView adView = (UnifiedNativeAdView) LayoutInflater.from(context)
                .inflate(R.layout.native_ad_layout, null);

        // Set the media view
        adView.setMediaView(adView.findViewById(R.id.ad_media));

        // Set other ad assets
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // Populate the native ad view
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        ((ImageView) adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStarRatingView().setVisibility(View.VISIBLE);
            ((RatingBar) adView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
        }

        // Add the native ad view to the container
        ((ViewGroup) adContainer).removeAllViews();
        ((ViewGroup) adContainer).addView(adView);
    }

    public void destroyNativeAd() {
        if (nativeAd != null) {
            nativeAd.destroy();
            nativeAd = null;
        }
    }
} 