package cn.rookiex.analyze.message;

/**
 * @author rookiex
 * @date 2020/12/8 16:23
 * @des
 */
public class Message {
    private int code = 20000;

    private MessageObj data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public MessageObj getData() {
        return data;
    }

    public void setData(MessageObj data) {
        this.data = data;
    }
}
