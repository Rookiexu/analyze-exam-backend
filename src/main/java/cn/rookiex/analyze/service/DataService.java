package cn.rookiex.analyze.service;

import cn.rookiex.analyze.dao.ExamRepository;
import cn.rookiex.analyze.dao.ExamResultRepository;
import cn.rookiex.analyze.dao.StudentRepository;
import cn.rookiex.analyze.entity.Exam;
import cn.rookiex.analyze.entity.ExamResult;
import cn.rookiex.analyze.entity.ExamResultKey;
import cn.rookiex.analyze.entity.Student;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        return studentRepository.findAllByClassIdAndGrade(classId, grade);
    }

    public List<ExamResult> getAllExamResultsByGrade(int grade) {
        Map<Integer, String> examName = getExamNameMap(examRepository.findAllByGrade(grade));
        Map<Integer, String> studentName = getStudentNameMap(studentRepository.findAllByGrade(grade));
        List<ExamResult> allByExamIdIn = examResultRepository.findAllByExamIdIn(examName.keySet());
        return getExamResults(grade, allByExamIdIn,examName,studentName);
    }

    private Map<Integer, String> getExamNameMap(List<Exam> allByGrade) {
        Map<Integer, String> examName = Maps.newHashMap();
        for (Exam exam : allByGrade) {
            examName.put(exam.getExamId(), exam.getExamName());
        }
        return examName;
    }

    private Map<Integer, String> getStudentNameMap(List<Student> allById) {
        Map<Integer, String> studentName = Maps.newHashMap();
        for (Student student : allById) {
            studentName.put(student.getSId(), student.getName());
        }
        return studentName;
    }

    public List<ExamResult> getAllExamResultsByGradeAndClassId(int grade, int classId) {
        Map<Integer, String> examName = getExamNameMap(examRepository.findAllByGrade(grade));
        Map<Integer, String> studentName = getStudentNameMap(studentRepository.findAllByClassIdAndGrade(classId,grade));
        List<ExamResult> allByExamIdIn = examResultRepository.findAllByExamIdInAndClassId(examName.keySet(),classId);
        return getExamResults(grade, allByExamIdIn,examName,studentName);
    }

    public List<ExamResult> getAllExamResultsByGradeAndClassIdAndExamId(int grade, int classId, int examId) {
        Map<Integer, String> examName = getExamNameMap(examRepository.findAllById(Lists.newArrayList(examId)));
        Map<Integer, String> studentName = getStudentNameMap(studentRepository.findAllByClassIdAndGrade(classId,grade));
        List<ExamResult> allByExamIdIn = examResultRepository.findAllByExamIdInAndClassId(examName.keySet(),classId);
        return getExamResults(grade, allByExamIdIn,examName,studentName);
    }

    public List<ExamResult> getAllExamResultsByGradeAndExamId(int grade, int examId) {
        Map<Integer, String> examName = getExamNameMap(examRepository.findAllById(Lists.newArrayList(examId)));
        Map<Integer, String> studentName = getStudentNameMap(studentRepository.findAllByGrade(grade));
        List<ExamResult> allByExamIdIn = examResultRepository.findAllByExamIdIn(examName.keySet());
        return getExamResults(grade, allByExamIdIn,examName,studentName);
    }


    private List<ExamResult> getExamResults(int grade, List<ExamResult> allByExamIdIn, Map<Integer, String> examName, Map<Integer, String> studentName) {
        List<ExamResult> objects = Lists.newArrayList();
        if (examName.isEmpty() || studentName.isEmpty() ){
            return objects;
        }
        for (ExamResult examResult : allByExamIdIn) {
            examResult.setGrade(grade);
            examResult.setName(studentName.get(examResult.getSId()));
            examResult.setExamName(examName.get(examResult.getExamId()));
        }
        return allByExamIdIn;
    }

    public void editExamResultTitle(int examId, int sId, String title) {
        ExamResultKey examResultKey = new ExamResultKey();
        examResultKey.setExamId(examId);
        examResultKey.setSId(sId);
        Optional<ExamResult> byId = examResultRepository.findById(examResultKey);
        if (byId.isPresent()){
            ExamResult examResult = byId.get();
            examResult.setTitle(title);
            examResultRepository.save(examResult);
        }
    }

    public void editExamTitle(int examId, String title) {
        Optional<Exam> byId = examRepository.findById(examId);
        if (byId.isPresent()){
            Exam exam = byId.get();
            exam.setTitle(title);
            examRepository.save(exam);
        }
    }

    public void editStudentTitle(int sId, String title) {
        Optional<Student> byId = studentRepository.findById(sId);
        if (byId.isPresent()){
            Student student = byId.get();
            student.setTitle(title);
            studentRepository.save(student);
        }
    }

    public List<Exam> getAllExam(int grade) {
        return examRepository.findAllByGrade(grade);
    }

    public List<Exam> getAllExam() {
        return examRepository.findAll();
    }

    public List<Exam> getExam(int examId) {
        return examRepository.findAllById(Lists.newArrayList(examId));
    }

    public List<Student> getAllStuByGrade(int grade) {
        return studentRepository.findAllByGrade(grade);
    }

    public List<Student> getAllStu() {
        return studentRepository.findAll();
    }
}
