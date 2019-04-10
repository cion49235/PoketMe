package kr.picture.poketme.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import kr.picture.poketme.R;
import kr.picture.poketme.cms.base.BaseActivity;

public class FindMyinfoActivity extends BaseActivity implements View.OnClickListener {
    LinearLayout layout_find_info, layout_find_result;
    EditText edit_phone_number, edit_phone_confirm;
    Button bt_findmyinfo_register, bt_myinfo_result;
    Context context;
    Activity activity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_myinfo);
        context = this;
        activity = this;
        init_ui();
    }

    private void init_ui(){
        layout_find_info = (LinearLayout)findViewById(R.id.layout_find_info);
        layout_find_result = (LinearLayout)findViewById(R.id.layout_find_result);
        layout_find_info.setVisibility(View.VISIBLE);
        layout_find_result.setVisibility(View.INVISIBLE);

        edit_phone_number = (EditText)findViewById(R.id.edit_phone_number);
        edit_phone_confirm = (EditText)findViewById(R.id.edit_phone_confirm);
        bt_findmyinfo_register = (Button)findViewById(R.id.bt_findmyinfo_register);
        bt_myinfo_result = (Button)findViewById(R.id.bt_myinfo_result);
        bt_findmyinfo_register.setOnClickListener(this);
        bt_myinfo_result.setOnClickListener(this);
    }

    /*TODO */
    private void do_findmyinfo(){

    }

    @Override
    public void onClick(View v) {
        if(v == bt_findmyinfo_register){

        }else if(v == bt_myinfo_result){

        }
    }
}
