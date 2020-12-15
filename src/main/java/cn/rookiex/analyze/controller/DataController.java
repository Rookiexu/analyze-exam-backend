package cn.rookiex.analyze.controller;

import cn.rookiex.analyze.constants.MessageConstants;
import cn.rookiex.analyze.entity.Exam;
import cn.rookiex.analyze.entity.ExamResult;
import cn.rookiex.analyze.entity.Student;
import cn.rookiex.analyze.message.ClassesData;
import cn.rookiex.analyze.service.DataService;
import cn.rookiex.analyze.service.ResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author rookiex
 * @date 2020/12/4 18:30
 * @des
 */
@Slf4j
@Controller
public class DataController {

    @Autowired
    private DataService dataService;

    @Autowired
    private ResultService resultService;

    @GetMapping(path = "/data/students")
    @ResponseBody
    public String getAllStuByClassAndGrade(@RequestParam int classId, @RequestParam int grade) {
        List<Student> studentList;
        if (grade == MessageConstants.CLIENT_ALL_ID){
            studentList = dataService.getAllStu();
        }else {
            if (classId == MessageConstants.CLIENT_ALL_ID) {
                studentList = dataService.getAllStuByGrade(grade);
            } else {
                studentList = dataService.getAllStuByClassAndGrade(classId, grade);
            }
        }
        return resultService.getResult(studentList);
    }

    @GetMapping(path = "/data/exams")
    @ResponseBody
    public String getExams(@RequestParam int grade, @RequestParam int examId) {
        List<Exam> exams;
        if (examId != MessageConstants.CLIENT_ALL_ID){
            exams = dataService.getExam(examId);
        }else {
            if (grade == MessageConstants.CLIENT_ALL_ID) {
                exams = dataService.getAllExam();
            } else {
                exams = dataService.getAllExam(grade);
            }
        }
        return resultService.getResult(exams);
    }

    @GetMapping(path = "/data/examResults")
    @ResponseBody
    public String getAllExamByGrade(@RequestParam int grade, @RequestParam int classId, @RequestParam int examId) {
        List<ExamResult> exams;
        if (classId == MessageConstants.CLIENT_ALL_ID) {
            if (examId == MessageConstants.CLIENT_ALL_ID) {
                exams = dataService.getAllExamResultsByGrade(grade);
            } else {
                exams = dataService.getAllExamResultsByGradeAndExamId(grade,examId);
            }
        } else {
            if (examId == MessageConstants.CLIENT_ALL_ID) {
                exams = dataService.getAllExamResultsByGradeAndClassId(grade, classId);
            } else {
                exams = dataService.getAllExamResultsByGradeAndClassIdAndExamId(grade, classId, examId);
            }
        }

        return resultService.getResult(exams);
    }

    @GetMapping(path = "/data/classes")
    @ResponseBody
    public String getClasses() {
        ClassesData data = dataService.getAllClassInfo();
        return resultService.getResult(data);
    }
}
