package kr.picture.poketme;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import kr.picture.poketme.util.NetworkHelper;

public class MyApp extends Application {
    private static MyApp INSTANCE = null;
    private static volatile MyApp obj = null;
    private static volatile Activity currentActivity = null;
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        obj = this;
        context = this;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    public static MyApp getGlobalApplicationContext() {
        return obj;
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    // Activity가 올라올때마다 Activity의 onCreate에서 호출해줘야한다.
    public static void setCurrentActivity(Activity currentActivity) {
        MyApp.currentActivity = currentActivity;
    }
    public static MyApp getApplication(){
        return INSTANCE;
    }
    /* (non-Javadoc)
     * @see android.app.Application#onTerminate()
     */

    @Override
    public void onTerminate() {
        NetworkHelper.getInstance().close();
        INSTANCE = null;
        super.onTerminate();
    }

    public static MyApp getInstance() {
        return INSTANCE;
    }
}
