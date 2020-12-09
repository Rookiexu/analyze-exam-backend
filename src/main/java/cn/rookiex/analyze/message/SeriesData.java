package cn.rookiex.analyze.message;

import lombok.Data;

/**
 * @author rookiex
 * @date 2020/12/7 17:03
 * @des
 */
@Data
public class SeriesData {
    /**
     * line Name
     */
    private String name;
    /**
     * 分数
     */
    private Number[] score;
}