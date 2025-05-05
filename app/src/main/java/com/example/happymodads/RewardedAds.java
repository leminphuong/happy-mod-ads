package com.example.happymodads;

import android.content.Context;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;

public class RewardedAds {
    private RewardedAd rewardedAd;
    private Context context;
    private RewardedAdListener listener;

    public interface RewardedAdListener {
        void onRewarded(RewardItem reward);
        void onRewardedAdClosed();
        void onRewardedAdFailedToLoad();
    }

    public RewardedAds(Context context, RewardedAdListener listener) {
        this.context = context;
        this.listener = listener;
        MobileAds.initialize(context);
    }

    public void initRewardedAd() {
        rewardedAd = new RewardedAd(context, AdConstants.REWARDED_AD_UNIT_ID);

        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad loaded successfully
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                listener.onRewardedAdFailedToLoad();
            }
        };

        // Create an ad request
        AdRequest adRequest = new AdRequest.Builder().build();

        // Load the rewarded ad
        rewardedAd.loadAd(adRequest, adLoadCallback);
    }

    public void showRewardedAd() {
        if (rewardedAd != null && rewardedAd.isLoaded()) {
            RewardedAdCallback adCallback = new RewardedAdCallback() {
                @Override
                public void onRewardedAdOpened() {
                    // Ad opened
                }

                @Override
                public void onRewardedAdClosed() {
                    listener.onRewardedAdClosed();
                    initRewardedAd(); // Reload the ad
                }

                @Override
                public void onUserEarnedReward(RewardItem reward) {
                    listener.onRewarded(reward);
                }

                @Override
                public void onRewardedAdFailedToShow(int errorCode) {
                    // Ad failed to show
                }
            };

            rewardedAd.show(adCallback);
        } else {
            initRewardedAd();
        }
    }
} 