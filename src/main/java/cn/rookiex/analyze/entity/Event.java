package cn.rookiex.analyze.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author rookiex
 * @date 2020/12/16 16:42
 * @des
 */
@Entity
@Data
@Table(name = "t_event")
public class Event {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String title;
    private int type;
    private String startDate;
    private String endDate;
    private boolean eventComplete;
    private String descData;
}
