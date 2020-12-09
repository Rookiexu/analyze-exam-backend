package cn.rookiex.analyze.service;

import cn.rookiex.analyze.dao.ExamRepository;
import cn.rookiex.analyze.dao.ExamResultRepository;
import cn.rookiex.analyze.dao.StudentRepository;
import cn.rookiex.analyze.entity.Exam;
import cn.rookiex.analyze.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rookiex
 * @date 2020/12/4 18:30
 * @des
 */
@Service
public class DataService {
    @Autowired
    private ExamResultRepository examResultRepository;
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStuByClassAndGrade(int classId, int grade) {
        return studentRepository.findAllByClassIdAndGrade(classId,grade);
    }

    public List<Exam> getAllExamByGrade(int grade) {
        return examRepository.findAllByGrade(grade);
    }
}
