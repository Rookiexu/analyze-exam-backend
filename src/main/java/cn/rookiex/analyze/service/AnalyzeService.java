package cn.rookiex.analyze.service;

import cn.rookiex.analyze.message.LineResultData;
import cn.rookiex.analyze.dao.ExamRepository;
import cn.rookiex.analyze.dao.StudentRepository;
import cn.rookiex.analyze.entity.Exam;
import cn.rookiex.analyze.entity.ExamResult;
import cn.rookiex.analyze.bean.ExamResults;
import cn.rookiex.analyze.dao.ExamResultRepository;
import cn.rookiex.analyze.entity.Student;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author rookiex
 * @date 2020/12/3 18:17
 * @des
 */
@Service
public class AnalyzeService {

    @Autowired
    private ExamResultRepository examResultRepository;
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ResultService resultService;

    private Map<Integer,Exam> getExamMaps(List<Exam> allByGrade) {
        Map<Integer,Exam> examIds = Maps.newTreeMap();
        for (Exam exam : allByGrade) {
            examIds.put(exam.getExamId(),exam);
        }
        return examIds;
    }

    public LineResultData getStudentAllExamResult(int sId, boolean isRank) {
        Optional<Student> studentOpt = studentRepository.findById(sId);
        if (studentOpt.isPresent()){
            Student student = studentOpt.get();
            int classId = student.getClassId();

            List<Exam> allGradeExam = examRepository.findAllByGrade(student.getGrade());

            Map<Integer,Exam> examMaps = getExamMaps(allGradeExam);

            List<ExamResult> allByExamIdInAndClassId = examResultRepository.findAllByExamIdInAndClassId(examMaps.keySet(), classId);

            return resultService.respGetClassAllExamResult(isRank, Lists.newArrayList(student), examMaps, allByExamIdInAndClassId);
        }
        return resultService.respExamResultsFail("no student");
    }

    public LineResultData getClassAllExamResult(int classId, int grade, boolean isRank) {
        //全班学生
        List<Student> classStudents = studentRepository.findAllByClassIdAndGrade(classId, grade);
        //年级所有考试
        List<Exam> allGradeExam = examRepository.findAllByGrade(grade);

        Map<Integer,Exam> examMaps = getExamMaps(allGradeExam);

        //班级所有成绩
        List<ExamResult> allByExamIdInAndClassId = examResultRepository.findAllByExamIdInAndClassId(examMaps.keySet(), classId);

        return resultService.respGetClassAllExamResult(isRank, classStudents, examMaps, allByExamIdInAndClassId);
    }

    public ExamResults getClassAllExamHistory(int classId, int grade) {
        //年级所有考试
        List<Exam> allGradeExam = examRepository.findAllByGrade(grade);
        Map<Integer,Exam> examMaps = getExamMaps(allGradeExam);
        //班级所有成绩
        List<ExamResult> allByExamIdInAndClassId = examResultRepository.findAllByExamIdInAndClassId(examMaps.keySet(), classId);

        return resultService.respGetClassAllExamHistory(examMaps, allByExamIdInAndClassId);
    }

    public ExamResults getClassAllExamHistoryWithPrams(int classId, int grade, String[] prams) {
        //年级所有考试
        List<Exam> allGradeExam = examRepository.findAllByGrade(grade);
        Map<Integer,Exam> examMaps = getExamMaps(allGradeExam);
        //班级所有成绩
        List<ExamResult> allByExamIdInAndClassId = examResultRepository.findAllByExamIdInAndClassId(examMaps.keySet(), classId);

        Set<String> objects = Sets.newHashSet();
        Collections.addAll(objects, prams);
        return resultService.respGetClassAllExamHistory(examMaps, allByExamIdInAndClassId,objects);
    }
}
