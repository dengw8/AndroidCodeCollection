package com.iflytek.taobaotest;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn, btn2;

    private static String TAOBAO_PKGNAME = "com.taobao.taobao";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn=findViewById(R.id.btn_taobao);
        btn2=findViewById(R.id.btn_tianmao);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toShopByTaoBaoApp("taobao://shop.m.taobao.com/shop/shop_index.htm?shop_id=185441148");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toShopByTaoBaoWebView("https://detail.tmall.com/item.htm?spm=a1z0d.6639537.1997196601.3.45d07484uw9hPZ&id=565570128470");
            }
        });
    }

    private void toShopByTaoBaoApp(String path) {
        if (isPkgInstalled(TAOBAO_PKGNAME)) {
            try {
                Log.d("mydebug", "安装了");
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(path));
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e("OPEN_SHOP","您还没有安装淘宝客户端！");
        }
    }

    private void toShopByTaoBaoWebView(String path) {
        if (isPkgInstalled(TAOBAO_PKGNAME)) {
            try {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri uri = Uri.parse(path); // 商品地址
                intent.setData(uri);
                intent.setClassName("com.taobao.taobao", "com.taobao.tao.detail.activity.DetailActivity");
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e("OPEN_SHOP","您还没有安装淘宝客户端！");
        }
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param pkgName 应用包名
     * @return true:已安装；false：未安装
     */
    public boolean isPkgInstalled(String pkgName) {
        PackageInfo packageInfo;
        try {
            packageInfo = getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }
}
