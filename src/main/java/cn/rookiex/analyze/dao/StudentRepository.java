package cn.rookiex.analyze.dao;

import cn.rookiex.analyze.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author rookiex
 * @date 2020/12/4 11:37
 * @des
 */
@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    List<Student> findAllByClassIdAndGrade(int classId, int gradeId);
}
