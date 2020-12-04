package cn.rookiex.analyze.dao;

import cn.rookiex.analyze.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author rookiex
 * @date 2020/12/4 11:37
 * @des
 */
@Repository
public interface ExamRepository extends JpaRepository<Exam,Integer> {
    List<Exam> findAllByGrade(int grade);
}
