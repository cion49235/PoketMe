package kr.picture.poketme.cms.base;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;

import cz.msebera.android.httpclient.Header;
import kr.picture.poketme.cms.helper.LoadingDialogHelper;
import kr.picture.poketme.util.Dialog;

/** 통신 결과 처리를 수행할 클래스 정의 */
// --> import com.loopj.android.http.AsyncHttpResponseHandler;
public abstract class BaseResponse<T> extends AsyncHttpResponseHandler {

    Context context;

    public BaseResponse(Context context) {
        this.context = context;
    }

    /** 상속받아서 재정의 해야 하는 메서드 */
    public abstract void onResponse(T response);

    /** 통신 시작시에 호출된다. */
    @Override
    public void onStart() {
        super.onStart();
        LoadingDialogHelper.getInstance(this.context).show();
    }

    /**
     * 통신 접속 성공시 호출된다.
     * @param statusCode    상태코드. (정상결과일 경우 200)
     * @param headers       HTTP Header
     * @param responseBody  HTTP Body (브라우저에 보여지는 내용)
     */
    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        // 통신 결과를 문자열로 변환한다.
        String response = null;
        try {
            response = new String(responseBody, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        T object = null;

        if (response != null) {
            try{
                object = (new Gson()).fromJson(response, (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
            }catch (JsonSyntaxException e){
                Log.e("dsu",  e.getMessage().toString());
            }

        }

        this.onResponse(object);
    }

    /**
     * 통신 접속 실패시 호출된다.
     * // --> import cz.msebera.android.httpclient.Header;
     * @param statusCode        상태코드. (404:Page Not Found, 50x: Server Error)
     * @param headers           HTTP Header 정보
     * @param responseBody      HTTP Body 정보 (브라우저에 보여지는 내용)
     * @param error             에러정보 객체
     */
    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        String response = null;
        T object = null;
        try {
            response = new String(responseBody, "UTF-8");
            if (response != null) {
                try{
                    object = (new Gson()).fromJson(response, (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
                }catch (JsonSyntaxException e){
                    Dialog.Return_AlertShow(context, e.getMessage().toString(), true);
                }
            }
        } catch (NullPointerException e){
        } catch (UnsupportedEncodingException e) {
        } catch (Exception e){
        }
        this.onResponse(object);
    }

    /** 통신 성공,실패에 상관없이 종료시에 호출된다. */
    @Override
    public void onFinish() {
        super.onFinish();
        LoadingDialogHelper.getInstance(this.context).hide();
    }
}
