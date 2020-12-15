package cn.rookiex.analyze.dao;

import cn.rookiex.analyze.entity.Exam;
import cn.rookiex.analyze.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author rookiex
 * @date 2020/12/4 11:37
 * @des
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    /**
     * find by token
     * @param token token
     * @return user
     */
    User findByToken(String token);

    /**
     * @param userName userName
     * @param password password
     * @return user
     */
    User findByUserNameAndPassword(String userName,String password);
}
