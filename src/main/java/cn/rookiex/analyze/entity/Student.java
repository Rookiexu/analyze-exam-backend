package cn.rookiex.analyze.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author rookiex
 * @date 2020/12/3 18:33
 * @des
 */
@Entity
@Table(name = "t_student")
@Getter
@Setter
public class Student {

    @Id
    private int sId;

    private int grade;

    private int classId;

    private String name;

    private String title;
}
