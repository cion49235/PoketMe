package kr.picture.poketme.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.io.OptionalDataException;

import kr.picture.poketme.MainActivity;
import kr.picture.poketme.R;
import kr.picture.poketme.util.Intent_Helper;
import kr.picture.poketme.util.PreferenceUtil;

import static android.os.Build.VERSION.SDK_INT;

public class PermissionActivity extends AppCompatActivity implements View.OnClickListener {
    Button bt_agree;
    Context context;
    AppCompatActivity activity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_permission);
        context = this;
        activity = this;
        if(PreferenceUtil.getBooleanSharedData(context, PreferenceUtil.PREF_PERMISSION_GRANTED, false) == false){
            init_ui();
        }else{
            Intent_Helper.finish_Intent(context, LoginActivity.class, activity);
        }
    }

    private void init_ui(){
        bt_agree = (Button)findViewById(R.id.bt_agree);
        bt_agree.setOnClickListener(this);
    }

    private void permission_check() {
        if (SDK_INT >= Build.VERSION_CODES.M) {
//            if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                // 1. 주소록 퍼미션과 외부 저장소 퍼미션을 가지고 있는지 체크합니다.
                int contactsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
                int writeExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if ( contactsPermission == PackageManager.PERMISSION_GRANTED
                        && writeExternalStoragePermission == PackageManager.PERMISSION_GRANTED) {
                    Intent_Helper.finish_Intent(context, LoginActivity.class, activity);
                    // 2. 이미 퍼미션을 가지고 있다면
                    // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
                }else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.
                    // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                            || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {
                        // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                        ActivityCompat.requestPermissions( PermissionActivity.this, REQUIRED_PERMISSIONS,
                                PERMISSIONS_REQUEST_CODE);
                    } else {
                        // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                        // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                        ActivityCompat.requestPermissions( this, REQUIRED_PERMISSIONS,
                                PERMISSIONS_REQUEST_CODE);
                    }
                }
            } else {
                 Intent_Helper.finish_Intent(context, LoginActivity.class, activity);
        }
    }

    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.READ_CONTACTS, // 주수록
            Manifest.permission.WRITE_EXTERNAL_STORAGE};  // 외부 저장소
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        if ( requestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            boolean check_result = true;
            // 모든 퍼미션을 허용했는지 체크합니다.
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }
            if ( check_result ) {//권한획득 로그인페이지로 이동
                Log.i("dsu", "로그인페이지");
                PreferenceUtil.setBooleanSharedData(context, PreferenceUtil.PREF_PERMISSION_GRANTED, true);
                Intent_Helper.finish_Intent(context, LoginActivity.class, activity);
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {
                    // 사용자가 거부만 선택한 경우에는 앱을 다시 실행하여 허용을 선택하면 앱을 사용할 수 있습니다.
                    Return_AlertShow(context.getString(R.string.permissionActivity_10));
                }else {
                    // “다시 묻지 않음”을 사용자가 체크하고 거부를 선택한 경우에는 설정(앱 정보)에서 퍼미션을 허용해야 앱을 사용할 수 있습니다.
                    Return_AlertShow(context.getString(R.string.permissionActivity_10));
                }
            }
        }
    }

    public void Return_AlertShow(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage(msg);
        builder.setNeutralButton(context.getString(R.string.txt_finish_yes), new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int whichButton){
                PreferenceUtil.setBooleanSharedData(context, PreferenceUtil.PREF_PERMISSION_GRANTED, false);
                finish();
                dialog.dismiss();
            }
        });
        AlertDialog myAlertDialog = builder.create();
        myAlertDialog.show();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
    @Override
    public void onClick(View v) {
        if(v == bt_agree){
            if (SDK_INT >= Build.VERSION_CODES.M) {
                permission_check();
            }else{
                PreferenceUtil.setBooleanSharedData(context, PreferenceUtil.PREF_PERMISSION_GRANTED, true);
                Intent_Helper.finish_Intent(context, LoginActivity.class, activity);
            }
        }
    }
}
