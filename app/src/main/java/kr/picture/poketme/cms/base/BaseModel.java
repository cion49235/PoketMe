package kr.picture.poketme.cms.base;

/**
 * Created by LeeDongSu
 */

public class BaseModel {
    public String code;
    public String msg;

    @Override
    public String toString() {
        return "BaseModel{" +
                "code='" + code + '\'' +
                "msg='" + msg + '\'' +
                '}';
    }
}
