package kr.picture.poketme.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class Intent_Helper {
    public static void default_Intent(Context context, Class cls){
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void finish_Intent(Context context, Class cls, Activity activity){
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
        activity.finish();
    }


}
