package com.tensun.garlandviewdemo.details;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tensun.garlandviewdemo.GarlandApp;
import com.tensun.garlandviewdemo.R;
import com.tensun.garlandviewdemo.main.MainActivity;
import com.tensun.garlandviewdemo.profile.ProfileActivity;

import java.util.ArrayList;

import io.bloco.faker.Faker;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class DetailsActivity extends AppCompatActivity implements GarlandApp.FakerReadyListener {

    private static final int ITEM_COUNT = 4;

    private static final String BUNDLE_NAME = "BUNDLE_NAME";
    private static final String BUNDLE_INFO = "BUNDLE_INFO";
    private static final String BUNDLE_AVATAR_URL = "BUNDLE_AVATAR_URL";

    private final ArrayList<DetailsData> mListData = new ArrayList<>();                             // 創建一個 DetailsData類型的列表

    public static void start(final MainActivity activity,
                             final String name, final String address, final String url,
                             final View card, final View avatar) {
        Intent starter = new Intent(activity, DetailsActivity.class);

        /** 把想要傳到DetailsActivity的資料, 在startActivity()之前 先塞進Intent */
        starter.putExtra(BUNDLE_NAME, name);
        starter.putExtra(BUNDLE_INFO, address);
        starter.putExtra(BUNDLE_AVATAR_URL, url);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {                               // // 5.0以上的版本 才有支援轉場動畫

            /** 設置轉場動畫, Pair.create(第一個Activity 從哪個物件開始, 搜尋第二個Activity 有共同transitionName的物件 那個物件即為終點) */
            final Pair<View, String> p1 = Pair.create(card, activity.getString(R.string.transition_card));
            final Pair<View, String> p2 = Pair.create(avatar, activity.getString(R.string.transition_avatar_border));

            final ActivityOptions options = ActivityOptions                                         // 有多个共享元素时, 可以使用的過渡動畫
                    .makeSceneTransitionAnimation(activity, p1, p2);
            activity.startActivity(starter, options.toBundle());
        } else {
            activity.startActivity(starter);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ((GarlandApp)getApplication()).addListener(this);

        ((TextView) findViewById(R.id.tv_name)).setText(getIntent().getStringExtra(BUNDLE_NAME));
        ((TextView) findViewById(R.id.tv_info)).setText(getIntent().getStringExtra(BUNDLE_INFO));

        /**  */
        Glide.with(this)
                .load(getIntent().getStringExtra(BUNDLE_AVATAR_URL))                            // Glide允许我们加载不同途径的图片
                .placeholder(R.drawable.avatar_placeholder)                                             // 占位图, 也就是从开始加载到加载结束这段时间内显示的默认图片
                .bitmapTransform(new CropCircleTransformation(this))                        // 可以對圖片進行多種效果處理
                .into((ImageView) findViewById(R.id.avatar));
    }

    /** 補充: Faker的數據 每次的產生都是隨機的 都是不同的 */
    @Override
    public void onFakerReady(Faker faker) {                                                         // 如果有需要使用到Faker數據的, 都可以在此方法裡面完成
        ((TextView) findViewById(R.id.tv_status)).setText(faker.book.title());

        for (int i = 0; i < ITEM_COUNT; i++) {
            mListData.add(new DetailsData(faker.book.title(), faker.name.name()));
        }

        ((RecyclerView)findViewById(R.id.recycler_view)).setAdapter(new DetailsAdapter(mListData));
    }

    public void onCloseClick(View v) {
        super.onBackPressed();                                                                      // 返回上一個操作
    }

    /** 點擊DETAILS按鈕之後, 會搭配View 實現轉場動畫, 然後帶著資料 跳轉到ProfileActivity, 並銷毀DetailsActivity */
    public void onDetailsClick(View v) {                                                            // 點擊之後, 會帶著資料 跳轉到ProfileActivity, 並銷毀這個DetailsActivity
        /**  */
        ProfileActivity.start(
                this,

                /** 資料的部分, 要copy到ProfileActivity裡面使用 */
                getIntent().getStringExtra(BUNDLE_AVATAR_URL),
                getIntent().getStringExtra(BUNDLE_NAME),
                getIntent().getStringExtra(BUNDLE_INFO),
                ((TextView) findViewById(R.id.tv_status)).getText().toString(),

                /** 只有在 Activity跳轉的過程中使用 */
                findViewById(R.id.avatar),
                findViewById(R.id.card),
                findViewById(R.id.iv_background),
                findViewById(R.id.recycler_view),

                /** 資料的部分, 要copy到ProfileActivity裡面使用 */
                mListData
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();                                                                // 先運行退出動畫 再結束DetailsActivity
        } else {
            finish();
        }
    }
}
