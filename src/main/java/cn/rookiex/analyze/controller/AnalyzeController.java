package cn.rookiex.analyze.controller;

import cn.rookiex.analyze.bean.ExamResults;
import cn.rookiex.analyze.message.LineResultData;
import cn.rookiex.analyze.message.Message;
import cn.rookiex.analyze.service.AnalyzeService;
import cn.rookiex.analyze.service.ResultService;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author rookiex
 * @date 2020/12/3 18:17
 * @des
 */
@Slf4j
@Controller
public class AnalyzeController {

    @Autowired
    private AnalyzeService analyzeService;

    @Autowired
    private ResultService resultService;

    @GetMapping(path = "/student/exams")
    @ResponseBody
    public String getStudentAllExam(@RequestParam int rank, @RequestParam String classId, @RequestParam int grade) {
        String[] split = classId.split(",");
        List<Integer> classes = Lists.newArrayList();
        for (String s : split) {
            classes.add(Integer.parseInt(s));
        }
        LineResultData analyzeExam = analyzeService.getClassAllExamResult(classes, grade, rank == 1);
        Message message = new Message();
        message.setData(analyzeExam);
        String s = JSONObject.toJSONString(message);
        log.info(s);
        return s;
    }

    /**
     * 获得班级单次考试的成绩分析柱状图
     * @param examId 考试id
     * @param classId 班级id
     * @param grade 年级
     * @return 单次考试成绩数据
     */
    @GetMapping(path = "/class/exam")
    @ResponseBody
    public String getClassExamResult(@RequestParam int examId, @RequestParam int classId, @RequestParam int grade) {
        return null;
    }

    @GetMapping(path = "/class/exams")
    @ResponseBody
    public String getClassAllExamHistory(@RequestParam int classId, @RequestParam int grade) {
            LineResultData analyzeExam = analyzeService.getClassAllExamHistory(classId, grade);
            return resultService.getResult(analyzeExam);
//            ExamResults analyzeExam = analyzeService.getClassAllExamHistoryWithPrams(classId, grade, prams);
//            return JSONObject.toJSONString(analyzeExam);
    }
}
