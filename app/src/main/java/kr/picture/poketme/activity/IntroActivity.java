package kr.picture.poketme.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;

import kr.picture.poketme.MainActivity;
import kr.picture.poketme.R;
import kr.picture.poketme.cms.base.BaseActivity;
import kr.picture.poketme.cms.base.BaseModel;
import kr.picture.poketme.cms.base.BaseResponse;
import kr.picture.poketme.cms.data.AppVersion;
import kr.picture.poketme.util.Dialog;
import kr.picture.poketme.util.Intent_Helper;
import kr.picture.poketme.util.NetworkHelper;

import static android.os.Build.VERSION.SDK_INT;

public class IntroActivity extends BaseActivity {
    Context context;
    boolean alert_view = false;
    BaseActivity activity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);
        context = this;
        activity = this;
        alert_view = true;
        version_check();
//        intent_next();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        alert_view = false;
    }

    String m_mac;
    int versionCode;
    private void version_check() {
        if (SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            m_mac = NetworkHelper.getMacAddress_v6();
        } else {
            m_mac = NetworkHelper.getMACAddress("wlan0");
        }
        PackageInfo pi = null;
        try {
            pi = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionCode = pi.versionCode;
            urlreq.init(R.string.app_version, m_mac);
            urlreq.post(new BaseResponse<AppVersion>(this) {
                @Override
                public void onResponse(AppVersion response) {
                    intent_next();
                    return;
                   /* // 시스템에러
                    Log.i("dsu", "AppVersion : " + response.toString());
                    if (response != null && !response.code.equals("000")) {
                        Dialog.Return_AlertShow(context, response.msg, alert_view);
                        return;
                    }
                    if ((versionCode < Integer.parseInt(response.app_version)) && (versionCode > 0)) {
                       new android.app.AlertDialog.Builder(context)
                                .setTitle(context.getString(R.string.introAcitivty_01))
                                .setIcon(R.mipmap.ic_launcher)
                                .setMessage(context.getString(R.string.introAcitivty_02))
                                .setCancelable(false)
                                .setPositiveButton(context.getString(R.string.introAcitivty_03), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        String packageName = "";
                                        try {
                                            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                                            packageName = getPackageName();
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                                        } catch (PackageManager.NameNotFoundException e) {
                                        } catch (ActivityNotFoundException e) {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
                                        }
                                    }
                                })
                                .setNegativeButton(context.getString(R.string.introAcitivty_04), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .create().show();
                    } else {
                        intent_next();
                    }*/
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NullPointerException e) {
        } catch (Exception e) {
        }
    }

    private final int SPLASH_DISPLAY_LENGTH = 1500;
    private void intent_next(){
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent_Helper.finish_Intent(context, PermissionActivity.class, activity);
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
