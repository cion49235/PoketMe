package kr.picture.poketme.cms.base;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import kr.picture.poketme.cms.helper.JsonClient;
import kr.picture.poketme.cms.helper.UrlReq;


public class BaseActivity extends AppCompatActivity {
    public JsonClient client;
    public UrlReq urlreq;
    public String m_mac;
    public String androidId;
    public String nickname;
    public String app_uid;
    public String m_id;
    public String fcm_token;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = JsonClient.getInstance(this);
        urlreq = UrlReq.getInstance(this);
        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
