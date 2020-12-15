package cn.rookiex.analyze.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author rookiex
 * @date 2020/12/15 17:39
 * @des
 */
@Data
@Entity
@Table(name = "t_class")
public class Clazz {
    @Id
    private int id;
    private int grade;
    private String name;
}
