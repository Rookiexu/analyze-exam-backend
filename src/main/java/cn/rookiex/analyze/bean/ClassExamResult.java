package cn.rookiex.analyze.bean;

import cn.rookiex.analyze.anno.Name;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * @author rookiex
 * @date 2020/12/4 14:18
 * @des
 */
@Getter
@Builder
public class ClassExamResult {

    public final int examId;

    @Name(name = "满分数")
    public final int perfectCount;

    @Name(name = "a等数量")
    public final int aCount;

    @Name(name = "b等数量")
    public final int bCount;

    @Name(name = "c等数量")
    public final int cCount;

    @Name(name = "d等数量")
    public final int dCount;

    @Name(name = "b等率")
    public final double bRate;

    @Name(name = "a等率")
    public final double aRate;

    @Name(name = "c等率")
    public final double cRate;

    @Name(name = "d等率")
    public final double dRate;

    @Name(name = "平均分")
    public final double avg;

    @Name(name = "考试人数")
    public final int testCount;

    @Name(name = "缺考数")
    public final int absentCount;
}
