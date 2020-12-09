package cn.rookiex.analyze.message;

import lombok.Data;

/**
 * @author rookiex
 * @date 2020/12/7 17:04
 * @des
 */
@Data
public class TitleInfo {
    /**
     * 标题
     */
    private String title;
    /**
     * 数据选择器
     */
    private String[] legend;
    /**
     * x轴数据
     */
    private String[] xAxis;
    /**
     * y轴数据
     */
    private String[] yAxis;
    /**
     * y轴单位
     */
    private String yName;

    private boolean inverse;
    private int min;

    public void setMin(int min) {
        this.min = min;
    }

    public int getMin() {
        return min;
    }
}
