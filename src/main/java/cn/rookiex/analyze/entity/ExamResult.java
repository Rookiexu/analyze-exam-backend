package cn.rookiex.analyze.entity;

import cn.rookiex.analyze.message.MessageObj;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author rookiex
 * @date 2020/12/3 18:29
 * @des
 */
@Getter
@Setter
@Entity
@Table(name = "exam_result")
@IdClass(ExamResultKey.class)
public class ExamResult implements Comparable<ExamResult>, MessageObj {

    @Id
    private int examId;

    @Id
    private int sId;

    private int classId;

    private String title;

    private int absent = 0;

    private double score = -1;

    @Transient
    private int grade;

    @Transient
    private int rank;

    @Transient
    private String name;

    @Transient
    private String examName;

    @Override
    public int compareTo(ExamResult o) {
        return Double.compare(o.score,this.score);
    }
}
