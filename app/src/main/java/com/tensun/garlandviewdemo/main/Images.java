package com.tensun.garlandviewdemo.main;

/**
 * Created by Administrator on 2017/9/18.
 */

public class Images {

    /** 建立10個圖片網址的數組, 為了提供隨機圖片的網址 */
    String[] images = {
            "http://www.tenniscanada.com/wp-content/uploads/2016/03/logo_small.png",
            "http://www.acusports.com/images/responsive/cal_logo.png",
            "http://22iiaa2jpzc63c93rf3p9oti14j8.wpengine.netdna-cdn.com/wp-content/uploads/trifacta-logo-square.png",
            "https://www.providentmetals.com/skin/frontend/pm/bootstrap/images/market-alerts-logo.png",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0e/Esko_Praha_logo.svg/100px-Esko_Praha_logo.svg.png",
            "http://brandicoot.com.au/files/2016/10/logo2.png",
            "http://www.cscss.com.cn/Public/cscss/images/logo-red.png",
            "http://2hngz4f6s7y2dzzb25efew8p.wpengine.netdna-cdn.com/wp-content/uploads/2017/08/MFS_Africa-latest-logo-e1501670238871-100x100.png",
            "https://kokatat.com/magento/media/catalog/product/cache/1//100x/9df78eab33525d08d6e5fb8d27136e95/a/c/acuhatna-logo-hat-navy-no-sticker.jpg",
            "http://www.designatives.com/images/contacts-form-logo-green.png"
    };

    public Images() {

    }

    public String randomImages() {
        return images[(int) (Math.random()*10)];
    }
}
