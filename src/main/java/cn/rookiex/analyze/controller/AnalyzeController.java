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

    @GetMapping(path = "/exams/student")
    @ResponseBody
    public String getStudentAllExam(@RequestParam int rank, @RequestParam int sId) {
        LineResultData analyzeExam = analyzeService.getStudentAllExamResult(sId, rank == 1);
        return JSONObject.toJSONString(analyzeExam);
    }

    @GetMapping(path = "/exams/students")
    @ResponseBody
    public String getStudentAllExam(@RequestParam int rank, @RequestParam int classId, @RequestParam int grade) {
        LineResultData analyzeExam = analyzeService.getClassAllExamResult(classId, grade, rank == 1);
        Message message = new Message();
        message.setData(analyzeExam);
        String s = JSONObject.toJSONString(message);
        log.info(s);
        return s;
    }

    @GetMapping(path = "/exam/class")
    @ResponseBody
    public String getClassAllExamHistory(@RequestParam String needPrams, @RequestParam int classId, @RequestParam int grade) {
        if (needPrams == null || needPrams.isEmpty()){
            ExamResults analyzeExam = analyzeService.getClassAllExamHistory(classId, grade);
            return JSONObject.toJSONString(analyzeExam);
        }else {
            String[] prams = needPrams.split(",");
            ExamResults analyzeExam = analyzeService.getClassAllExamHistoryWithPrams(classId, grade, prams);
            return JSONObject.toJSONString(analyzeExam);
        }

    }
}
