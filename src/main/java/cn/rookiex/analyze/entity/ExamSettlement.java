package cn.rookiex.analyze.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author rookiex
 * @date 2020/12/4 12:21
 * @des
 */
@Getter
@Setter
@Entity
@Table(name = "exam_setttlement")
@IdClass(ExamResultKey.class)
public class ExamSettlement {
    @Id
    private int examId;

    @Id
    private int sId;

    private int classId;

    private int absent = 0;

    private double score = -1;

    private int classRank;

    private int gradeRank;
}
