package edu.as.sys.common;

import edu.as.sys.model.Info;

/**
 * Created by dell on 2016/12/13.
 */
public class ResponseResult {
    public String msg = "";
    public Info info = null;
    public boolean status = false;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
