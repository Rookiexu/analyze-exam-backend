package cn.rookiex.analyze.message;

import lombok.Data;

/**
 * @author rookiex
 * @date 2020/12/8 16:23
 * @des
 */
@Data
public class Message {
    private int code = 20000;

    private Object data;

    private String message;


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
