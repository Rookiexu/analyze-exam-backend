package cn.rookiex.analyze.controller;

import cn.rookiex.analyze.bean.ExamResults;
import cn.rookiex.analyze.message.LineResultData;
import cn.rookiex.analyze.message.Message;
import cn.rookiex.analyze.service.AnalyzeService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping(path = "/student/exams")
    @ResponseBody
    public String getStudentAllExam(@RequestParam int rank, @RequestParam int classId, @RequestParam int grade) {
        LineResultData analyzeExam = analyzeService.getClassAllExamResult(classId, grade, rank == 1);
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
            ExamResults analyzeExam = analyzeService.getClassAllExamHistory(classId, grade);
            return JSONObject.toJSONString(analyzeExam);
//            ExamResults analyzeExam = analyzeService.getClassAllExamHistoryWithPrams(classId, grade, prams);
//            return JSONObject.toJSONString(analyzeExam);
    }
}
