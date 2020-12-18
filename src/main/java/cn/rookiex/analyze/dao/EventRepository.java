package cn.rookiex.analyze.dao;

import cn.rookiex.analyze.entity.Event;
import cn.rookiex.analyze.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author rookiex
 * @date 2020/12/4 11:37
 * @des
 */
@Repository
public interface EventRepository extends JpaRepository<Event,Integer> {

}
