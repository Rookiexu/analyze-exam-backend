package cn.rookiex.analyze.dao;

import cn.rookiex.analyze.entity.ExamResult;
import cn.rookiex.analyze.entity.ExamResultKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author rookiex
 * @date 2020/12/3 18:38
 * @des
 */
@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, ExamResultKey> {
    /**
     * @param examId 考试id
     * @param classId 班级id
     * @return 考试成绩
     */
    List<ExamResult> findAllByExamIdAndClassId(int examId, int classId);

    /**
     * @param examIds 考试id
     * @param classId 班级id
     * @return 考试成绩
     */
    List<ExamResult> findAllByExamIdInAndClassId(Collection<Integer> examIds, int classId);

    /**
     * @param examId 考试id
     * @return 考试成绩
     */
    List<ExamResult> findAllByExamId(int examId);

    /**
     * @param examId 考试id
     * @param classIds  班级id
     * @return 考试成绩
     */
    List<ExamResult> findAllByExamIdAndClassIdIn(int examId,List<Integer> classIds);
}
