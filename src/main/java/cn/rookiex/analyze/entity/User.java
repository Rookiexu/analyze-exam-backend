package cn.rookiex.analyze.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author rookiex
 * @date 2020/12/15 10:47
 * @des
 */
@Data
@Entity
@Table(name = "t_user")
public class User {

    @Id
    private int id;

    /**
     * user name
     */
    private String userName;

    /**
     * password
     */
    private String password;/**
     * 名字
     */
    private String name;

    /**
     * 角色
     */
    private String roles;

    /**
     * 介绍
     */
    private String introduction;

    /**
     * 头像图床地址
     */
    private String avatar;



    /**
     * token
     */
    private String token;
}
