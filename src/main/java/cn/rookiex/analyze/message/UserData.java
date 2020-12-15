package cn.rookiex.analyze.message;

import cn.rookiex.analyze.entity.User;
import lombok.Getter;

/**
 * @author rookiex
 * @date 2020/12/15 11:02
 * @des
 */
@Getter
public class UserData {
    /**
     * 名字
     */
    private final String name;

    /**
     * 角色
     */
    private final String roles;

    /**
     * 介绍
     */
    private final String introduction;

    /**
     * 头像图床地址
     */
    private final String avatar;

    public UserData(User user){
        this.avatar = user.getAvatar();
        this.name = user.getName();
        this.introduction = user.getIntroduction();
        this.roles = user.getRoles();
    }
}
