package cn.rookiex.analyze.message;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @author rookiex
 * @date 2020/12/7 16:53
 * @des
 */
@Data
public class LineResultData implements MessageObj {
    /**
     * 线数据
     */
    private List<SeriesData> seriesData = Lists.newArrayList();

    /**
     * 标题数据
     */
    private TitleInfo titleInfo;




}
