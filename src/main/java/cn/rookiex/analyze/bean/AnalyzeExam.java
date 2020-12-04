package cn.rookiex.analyze.bean;

import cn.rookiex.analyze.entity.ExamResult;
import lombok.Data;

import java.util.Set;

/**
 * @author rookiex
 * @date 2020/12/3 18:28
 * @des
 */
@Data
public class AnalyzeExam {
    private int classId;

    private int grade;

    private int examId;

    private Set<ExamResult> students;

    private int height = 100;

    private int stepHeight = 10;

    private int fromBig = 0;
}
