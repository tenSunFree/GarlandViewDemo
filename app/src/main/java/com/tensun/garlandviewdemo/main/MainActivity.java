package com.tensun.garlandviewdemo.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ramotion.garlandview.TailLayoutManager;
import com.ramotion.garlandview.TailRecyclerView;
import com.ramotion.garlandview.TailSnapHelper;
import com.ramotion.garlandview.header.HeaderTransformer;
import com.tensun.garlandviewdemo.GarlandApp;
import com.tensun.garlandviewdemo.R;
import com.tensun.garlandviewdemo.details.DetailsActivity;
import com.tensun.garlandviewdemo.main.inner.InnerData;
import com.tensun.garlandviewdemo.main.inner.InnerItem;
import com.tensun.garlandviewdemo.main.outer.OuterAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.bloco.faker.Faker;

/**
 * Q: Faker 是什麼?
 * A: Java库生成用于测试或填充开发数据库的假数据
 */
public class MainActivity extends AppCompatActivity implements GarlandApp.FakerReadyListener {

    private final static int OUTER_COUNT = 10;                                                    // 總共有10個直列
    private final static int INNER_COUNT = 20;                                                    // 每個直列 包含20個Item

    private Images images = new Images();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((GarlandApp)getApplication()).addListener(this);                                           // 取得Faker數據 的第一步行為
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);                                              // 在此Activity啟用EventBus
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);                                            // 在Activity停用EventBus, 讓Subscribe停止接收
    }

    @Override
    public void onFakerReady(Faker faker) {                                                         // 取得Faker數據 的第二步行為
        final List<List<InnerData>> outerData = new ArrayList<>();                                  // 建立 outerData數據容器
        for (int i = 0; i < OUTER_COUNT; i++) {                                                   // 把10個innerData 放入outerData
            final List<InnerData> innerData = new ArrayList<>();
            for (int j = 0; j < INNER_COUNT; j++) {                                               // 把20個Item 放入innerData
                innerData.add(createInnerData(faker));                                              // 透過faker + createInnerData(), 可以得到一個InnerData類型的數據, 再把數據放入innerData
            }
            outerData.add(innerData);                                                               // 把innerData數據組 放入outerData數據組
        }

        initRecyclerView(outerData);                                                                // 實現資料畫面
    }

    private void initRecyclerView(List<List<InnerData>> data) {
        findViewById(R.id.progressBar).setVisibility(View.GONE);                                     // 顯示TailRecyclerView的時候, 隱藏ProgressBar

        final TailRecyclerView rv = (TailRecyclerView) findViewById(R.id.recycler_view);
        rv.setLayoutManager(
                new TailLayoutManager(this).setPageTransformer(new HeaderTransformer()));   // 設置不同頁之間的間距, 以及換頁的動畫
        rv.setAdapter(new OuterAdapter(data));

        new TailSnapHelper().attachToRecyclerView(rv);                                              // 實體化RecyclerView
    }

    private InnerData createInnerData(Faker faker) {                                                // 透過Faker, 產生一組InnerData類型的數據
        return new InnerData(
                faker.book.title(),
                faker.name.name(),
                faker.address.city() + ", " + faker.address.stateAbbr(),
                images.randomImages(),                                                              // 隨機產生圖片的網址
                faker.number.between(20, 50)                                                        // 取得20~50之間的隨機數字
        );
    }

    /** 註冊Subscribe，觀察目標為InnerItem */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnInnerItemClick(InnerItem item) {                                                  // 當收到class InnerItem的post(), 就會觸發此方法
        final InnerData itemData = item.getItemData();
        if (itemData == null) {
            return;
        }

        /** 將點擊的Item 的資料和View, 傳遞給DetailsActivity */
        DetailsActivity.start(
                this,
                item.getItemData().name,
                item.mAddress.getText().toString(),
                item.getItemData().avatarUrl,
                item.itemView,
                item.mAvatarBorder
        );
    }
}
