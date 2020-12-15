package cn.rookiex.analyze.controller;

import cn.rookiex.analyze.entity.User;
import cn.rookiex.analyze.message.Message;
import cn.rookiex.analyze.message.UserData;
import cn.rookiex.analyze.service.ResultService;
import cn.rookiex.analyze.service.UserService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author rookiex
 * @date 2020/12/15 10:29
 * @des
 */
@Slf4j
@Controller
public class UserController {

    @Autowired
    private ResultService resultService;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/user/login")
    @ResponseBody
    public String login(@RequestParam String userName, @RequestParam String password) {
        String token = userService.login(userName,password);
        return resultService.getResult(token);
    }

    @GetMapping(path = "/user/getInfo")
    @ResponseBody
    public String getInfo(@RequestParam String token) {
        User user = userService.getInfo(token);
        return resultService.getResult(new UserData(user));
    }

    @PostMapping(path = "/user/logout")
    @ResponseBody
    public String logout(@RequestParam String token) {
        userService.logout(token);
        return resultService.getResult(null);
    }
}
