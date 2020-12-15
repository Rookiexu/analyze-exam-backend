package cn.rookiex.analyze.constants;

/**
 * @author rookiex
 * @date 2020/12/15 11:26
 * @des
 */
public enum MessageErrCode {
    TOKEN_ERR(50008);

    private final int errCode;

    MessageErrCode(int errCode) {
        this.errCode = errCode;
    }

    public int getErrCode() {
        return errCode;
    }
}

