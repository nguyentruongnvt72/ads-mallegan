package com.malleganadslibrary;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mallegan.ads.callback.RewardCallback;
import com.mallegan.ads.callback.InterCallback;
import com.mallegan.ads.service.AdmobApi;
import com.mallegan.ads.util.Admob;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    private FrameLayout native_ads;

    public static String PRODUCT_ID_YEAR = "android.test.purchased";
    public static String PRODUCT_ID_MONTH = "android.test.purchased";
    public static  List<String> listID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        native_ads   = findViewById(R.id.native_ads);
        listID = new ArrayList<>();
        listID.add("getString(R.string.ads_test_inter)");
        listID.add("getString(R.string.ads_test_banner)");
        listID.add(getString(R.string.admod_banner_collap_id));
        Admob.getInstance().loadInlineBanner(this, getString(R.string.ads_test_banner),Admob.BANNER_INLINE_LARGE_STYLE);
        Admob.getInstance().initRewardAds(this,getString(R.string.admod_app_reward_id));
        loadAdInter();
        loadAdsNative();

        findViewById(R.id.clickFGM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity2.class));
            }
        });



        findViewById(R.id.btnClickInter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdmobApi.getInstance().showInterAll(MainActivity.this, new InterCallback() {
                    @Override
                    public void onNextAction() {
                        super.onNextAction();
                        startActivity(new Intent(MainActivity.this,MainActivity3.class));
                    }

                });
            }
        });


        findViewById(R.id.btnClickReward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admob.getInstance().showRewardAds(MainActivity.this,new RewardCallback(){
                    @Override
                    public void onEarnedReward(RewardItem rewardItem) {
                        Toast.makeText(MainActivity.this,"Trả thưởng thành công",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdClosed() {
                        Toast.makeText(MainActivity.this,"Close ads",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdFailedToShow(int codeError) {
                        Toast.makeText(MainActivity.this,"Loa ads err",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });




        findViewById(R.id.btnClickLoadAndShow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admob.getInstance().loadAndShowInter(MainActivity.this,getString(R.string.admod_interstitial_id),0,10000, new InterCallback(){
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        startActivity(new Intent(MainActivity.this,MainActivity2.class));
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError i) {
                        super.onAdFailedToLoad(i);
                        startActivity(new Intent(MainActivity.this,MainActivity2.class));
                    }
                });
            }
        });



        findViewById(R.id.btnBilding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  AppPurchase.getInstance().consumePurchase(PRODUCT_ID_MONTH);
                 AppPurchase.getInstance().purchase(MainActivity.this, PRODUCT_ID_MONTH);*/
                //real
               // AppPurchase.getInstance().subscribe(MainActivity.this, SubID);
            }
        });


       /* AppPurchase.getInstance().setPurchaseListioner(new PurchaseListioner() {
            @Override
            public void onProductPurchased(String productId,String transactionDetails) {
               Toast.makeText(MainActivity.this,"Purchase success",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void displayErrorMessage(String errorMsg) {
                Toast.makeText(MainActivity.this,"Purchase fall",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onUserCancelBilling() {

                Toast.makeText(MainActivity.this,"Purchase cancel",Toast.LENGTH_SHORT).show();
            }
        });*/
        // reset pay Purchase
       /*AppPurchase.getInstance().consumePurchase(Constants.PRODUCT_ID_MONTH);
        AppPurchase.getInstance().consumePurchase(Constants.PRODUCT_ID_YEAR);
        AppPurchase.getInstance().setPurchaseListioner(new PurchaseListioner() {
            @Override
            public void onProductPurchased(String productId,String transactionDetails) {

            }

            @Override
            public void displayErrorMessage(String errorMsg) {
                Log.e("PurchaseListioner","displayErrorMessage:"+ errorMsg);
            }

            @Override
            public void onUserCancelBilling() {

            }
        });*/
    }

    private void loadAdsNative(){
        List<String> listID = new ArrayList<>();
        listID.add("1");
        listID.add("2");
        listID.add(getString(R.string.ads_test_native));

        Admob.getInstance().loadNativeAdFloor(this, listID, native_ads,R.layout.ads_native_btn_ads_top);
        Admob.getInstance().loadNativeAd(this, "id native", native_ads,R.layout.ads_native);
    }

    private void loadAdInter() {
        AdmobApi.getInstance().loadInterAll(this);
    }
}