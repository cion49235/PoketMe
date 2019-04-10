package kr.picture.poketme.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import kr.picture.poketme.MainActivity;
import kr.picture.poketme.R;
import kr.picture.poketme.cms.base.BaseActivity;
import kr.picture.poketme.util.Intent_Helper;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout layout_join, layout_find_info;
    private EditText edit_id, edit_password;
    private Button bt_login_register;
    Context context;
    Activity activity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        activity = this;
        init_ui();
    }


    private void init_ui(){
        layout_join = (LinearLayout)findViewById(R.id.layout_join);
        layout_join.setOnClickListener(this);
        edit_id = (EditText)findViewById(R.id.edit_id);
        edit_password = (EditText)findViewById(R.id.edit_password);
        bt_login_register = (Button)findViewById(R.id.bt_login_register);
        bt_login_register.setOnClickListener(this);
        layout_find_info = (LinearLayout)findViewById(R.id.layout_find_info);
        layout_find_info.setOnClickListener(this);
    }

    /*TODO 로그인프로세스*/
    private void do_login_register(){
        //로그인완료 후 메인페이지로 이동
        Intent_Helper.finish_Intent(context, MainActivity.class, activity);
    }

    @Override
    public void onClick(View v) {
        if(v == bt_login_register){//시작하기
            do_login_register();
        }else if(v == layout_join){//회원가입
            Intent_Helper.default_Intent(context, JoinActivity.class);
        }else if(v == layout_find_info){
            Intent_Helper.default_Intent(context, FindMyinfoActivity.class);
        }
    }
}
