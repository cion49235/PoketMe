package kr.picture.poketme.cms.data;


import java.util.List;

import kr.picture.poketme.cms.base.BaseModel;
import kr.picture.poketme.cms.data.list.pro;
import kr.picture.poketme.cms.data.list.res;

public class AppVersion extends BaseModel {
    public String app_version;
    public String cur_domain;
    public String cur_protocol;
    public List<pro> list_pro;
    public List<res> list_res;

    @Override
    public String toString() {
        return "AppVersion{" +
                "app_version='" + app_version + '\'' +
                "cur_domain='" + cur_domain + '\'' +
                "cur_protocol='" + cur_protocol + '\'' +
                "list_pro='" + list_pro + '\'' +
                "list_res='" + list_res + '\'' +
                "} " + super.toString();
    }
}
