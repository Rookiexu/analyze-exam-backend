package cn.rookiex.analyze.bean;

import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Set;


/**
 * @author rookiex
 * @date 2020/12/3 21:09
 * @des
 */
@Data
public class ExamResults {

    /**
     * y最大值
     */
    private int height = 100;

    /**
     * y步进
     * */
    private int stepHeight = 10;

    /**
     * 降序开关
     */
    private int fromBig = 1;

    /**
     * 下标名字
     * */
    private String[] index;

    /**
     * 折线数据
     * */
    private Set<lineData> datasets = Sets.newHashSet();
}
