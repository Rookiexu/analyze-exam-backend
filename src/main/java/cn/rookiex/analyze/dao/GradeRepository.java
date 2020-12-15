package cn.rookiex.analyze.dao;

import cn.rookiex.analyze.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author rookiex
 * @date 2020/12/15 17:43
 * @des
 */
public interface GradeRepository extends JpaRepository<Grade,Integer>  {
}
