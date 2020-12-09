package cn.rookiex.analyze.controller;

import cn.rookiex.analyze.bean.ExamResults;
import cn.rookiex.analyze.entity.Exam;
import cn.rookiex.analyze.entity.Student;
import cn.rookiex.analyze.service.AnalyzeService;
import cn.rookiex.analyze.service.DataService;
import com.alibaba.fastjson.JSONObject;
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
@Controller
public class DataController {

    @Autowired
    private DataService dataService;

    @GetMapping(path = "/data/students")
    @ResponseBody
    public String getAllStuByClassAndGrade(@RequestParam int classId, @RequestParam int grade) {
        List<Student> studentList = dataService.getAllStuByClassAndGrade(classId,grade);
        return JSONObject.toJSONString(studentList);
    }

    @GetMapping(path = "/data/exams")
    @ResponseBody
    public String getAllExamByGrade(@RequestParam int grade) {
        List<Exam> exams = dataService.getAllExamByGrade(grade);
        return JSONObject.toJSONString(exams);
    }
}
