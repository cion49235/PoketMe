package kr.picture.poketme.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.widget.Toast;

import kr.picture.poketme.R;

public class Dialog {
    public static void Return_AlertFInish(String msg, final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setMessage(msg);
        builder.setNeutralButton(activity.getString(R.string.dialog_finish), new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int whichButton){
                activity.finish();
                dialog.dismiss();
            }
        });
        AlertDialog myAlertDialog = builder.create();
        myAlertDialog.show();
    }

    public static void Return_AlertShow(Context context, String msg, boolean alert_view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage(msg);
        builder.setNeutralButton(context.getString(R.string.setNeutralButton), new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int whichButton){
                dialog.dismiss();
            }
        });
        AlertDialog myAlertDialog = builder.create();
        if(alert_view) myAlertDialog.show();
    }
}
