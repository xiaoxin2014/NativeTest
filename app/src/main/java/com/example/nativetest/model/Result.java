package com.example.nativetest.model;


import com.example.nativetest.common.ErrorCode;
import com.example.nativetest.common.NetConstant;
import com.example.nativetest.model.sc.NetResponse;

/**
 * 网络请求结果基础类
 * @param <T> 请求结果的实体类
 */
public class Result<T> {

    public T RsData;
    public int RsCode;
    public String RsMsg;
    public NetResponse.RsDetailBean RsDetail;
    public String RsNote;

    public T getRsData() {
        return RsData;
    }

    public void setRsData(T RsData) {
        this.RsData = RsData;
    }

    public int getRsCode() {
        return RsCode;
    }

    public void setRsCode(int RsCode) {
        this.RsCode = RsCode;
    }

    public String getRsMsg() {
        return RsMsg;
    }

    public void setRsMsg(String RsMsg) {
        this.RsMsg = RsMsg;
    }

    public NetResponse.RsDetailBean getRsDetail() {
        return RsDetail;
    }

    public void setRsDetail(NetResponse.RsDetailBean RsDetail) {
        this.RsDetail = RsDetail;
    }

    public String getRsNote() {
        return RsNote;
    }

    public void setRsNote(String RsNote) {
        this.RsNote = RsNote;
    }


    public static class RsDetailBean {
    }
}
