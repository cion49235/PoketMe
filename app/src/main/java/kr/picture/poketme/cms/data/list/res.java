package kr.picture.poketme.cms.data.list;


import kr.picture.poketme.cms.base.BaseModel;

public class res extends BaseModel {
    public String r01;
    public String r02;

    @Override
    public String toString() {
        return "pro{" +
                "r01='" + r01 + '\'' +
                "r02='" + r02 + '\'' +
                "} " + super.toString();
    }
}
