package com.integration.networktechdemo.updown;

import com.integration.networktechdemo.R;

/**
 * Created by Wongerfeng on 2019/8/23.
 */
public class PackageInfo {

    public String app_name;
    public String package_name;
    public int package_icon;
    public String download_url;
    public String old_version;
    public String new_version;

    public PackageInfo() {
        app_name = "";
        package_name = "";
        package_icon = 0;
        download_url = "";
        old_version = "";
        new_version = "";
    }

    public static String[] mNameArray = {
            "爱奇艺", "酷狗音乐", "美图秀秀", "微信", "淘宝", "QQ"
    };
    private static String[] mPackageArray = {
            "com.qiyi.video", "com.kugou.android", "com.mt.mtxx.mtxx",
            "com.tencent.mm", "com.taobao.taobao", "com.tencent.mobileqq"
    };
    private static int[] mIconArray = {
            R.drawable.icon_aiqiyi, R.drawable.icon_kugou, R.drawable.icon_meitu,
            R.drawable.icon_weixin, R.drawable.icon_taobao, R.drawable.icon_qq
    };
    public static String[] mUrlArray = {
            "https://3g.lenovomm.com/w3g/yydownload/com.qiyi.video/60020",
            "https://3g.lenovomm.com/w3g/yydownload/com.kugou.android/60020",
            "https://3g.lenovomm.com/w3g/yydownload/com.mt.mtxx.mtxx/60020",
            "https://3g.lenovomm.com/w3g/yydownload/com.tencent.mm/60020",
            "https://3g.lenovomm.com/w3g/yydownload/com.taobao.taobao/60020",
            "https://3g.lenovomm.com/w3g/yydownload/com.tencent.mobileqq/60020"
    };
    private static String[] mVersionArray = {
            "10.2.0", "9.1.5", "8.4.3.0", "7.0.3", "8.5.10", "7.9.9"
    };

}
