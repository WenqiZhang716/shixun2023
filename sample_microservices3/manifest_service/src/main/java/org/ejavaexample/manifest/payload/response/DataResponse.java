package org.ejavaexample.manifest.payload.response;

/**
 * @author zhangwq
 */
public class DataResponse {
    private int code;
    private Object data;

    public DataResponse(int code, Object data) {
        this.code=code;
        this.data=data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
