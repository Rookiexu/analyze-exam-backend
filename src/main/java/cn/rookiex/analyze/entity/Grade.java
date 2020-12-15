package cn.rookiex.analyze.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author rookiex
 * @date 2020/12/15 17:38
 * @des
 */
@Data
@Entity
@Table(name = "t_grade")
public class Grade {
    @Id
    private int id;
    private String name;
}
