package cn.rookiex.analyze.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author rookiex
 * @date 2020/12/4 10:57
 * @des
 */
@Getter
@Setter
public class ExamResultKey implements Serializable {
    protected int examId;
    protected int sId;
}
