package cn.rookiex.analyze.controller;

import cn.rookiex.analyze.message.Message;
import cn.rookiex.analyze.service.DataService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author rookiex
 * @date 2020/12/9 18:35
 * @des
 */
@Slf4j
@Controller
public class EditController {

    @Autowired
    private DataService dataService;

    @GetMapping(path = "/edit/examResult")
    @ResponseBody
    public String editExamResult(@RequestParam int examId, @RequestParam int sId, @RequestParam String title) {
        dataService.editExamResultTitle(examId,sId,title);
        Message message = new Message();
        return JSONObject.toJSONString(message);
    }

    @GetMapping(path = "/edit/exam")
    @ResponseBody
    public String editExamTitle(@RequestParam int examId,@RequestParam String title) {
        dataService.editExamTitle(examId,title);
        Message message = new Message();
        return JSONObject.toJSONString(message);
    }

    @GetMapping(path = "/edit/student")
    @ResponseBody
    public String editStudentTitle(@RequestParam int sId,@RequestParam String title) {
        dataService.editStudentTitle(sId,title);
        Message message = new Message();
        return JSONObject.toJSONString(message);
    }
}
