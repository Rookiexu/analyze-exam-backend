package cn.rookiex.analyze.dao;

import cn.rookiex.analyze.entity.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author rookiex
 * @date 2020/12/15 17:41
 * @des
 */
public interface ClazzRepository extends JpaRepository<Clazz,Integer> {

    /**
     * @param grade grade
     * @return classes
     */
    List<Clazz> findAllByGrade(int grade);
}
