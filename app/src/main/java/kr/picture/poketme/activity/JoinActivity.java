package kr.picture.poketme.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import kr.picture.poketme.MainActivity;
import kr.picture.poketme.R;
import kr.picture.poketme.cms.base.BaseActivity;
import kr.picture.poketme.util.Intent_Helper;

public class JoinActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout layout_login;
    private EditText edit_phone_number, edit_id, edit_nickname, edit_password, edit_password_confirm;
    private CheckBox cb_agree01, cb_agree02;
    private Button bt_join_register;
    Context context;
    Activity activity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        context = this;
        activity = this;
        init_ui();
    }

    private void init_ui(){
        layout_login = (LinearLayout)findViewById(R.id.layout_login);
        layout_login.setOnClickListener(this);

        edit_phone_number = (EditText)findViewById(R.id.edit_phone_number);
        edit_id = (EditText)findViewById(R.id.edit_id);
        edit_nickname = (EditText)findViewById(R.id.edit_nickname);
        edit_password = (EditText)findViewById(R.id.edit_password);
        edit_password_confirm = (EditText)findViewById(R.id.edit_password_confirm);

        bt_join_register = (Button)findViewById(R.id.bt_join_register);
        bt_join_register.setOnClickListener(this);
    }

    /*TODO 회원가입프로세스*/
    private void do_join_register(){
        //회원가입완료 후 메인페이지로 이동
        Intent_Helper.finish_Intent(context, MainActivity.class, activity);
    }

    @Override
    public void onClick(View v) {
        if(v == layout_login){
            Intent_Helper.default_Intent(context, LoginActivity.class);
        }else if(v == bt_join_register){
            do_join_register();
        }
    }
}
