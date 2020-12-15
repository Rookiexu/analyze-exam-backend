package cn.rookiex.analyze.controller;

import cn.rookiex.analyze.constants.MessageErrCode;
import cn.rookiex.analyze.entity.User;
import cn.rookiex.analyze.message.UserData;
import cn.rookiex.analyze.service.ResultService;
import cn.rookiex.analyze.service.UserService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rookiex
 * @date 2020/12/15 10:29
 * @des
 */
@Slf4j
@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private ResultService resultService;

    @Autowired
    private UserService userService;

    @PostMapping(path = "/login")
    @ResponseBody
    public String login(@RequestBody Map<String,String> data) {
        String username = data.get("username");
        String password = data.get("password");

        if (username == null || password == null){
            return resultService.getErrResult(MessageErrCode.LOGIN_FAIL,"登录失败");
        }
        String token = userService.login(username, password);
        if (token == null){
            return resultService.getErrResult(MessageErrCode.LOGIN_FAIL,"登录失败");
        }else {
            Map<String, Object> map = Maps.newHashMap();
            map.put("token",token);
            return resultService.getResult(map);
        }
    }

    @GetMapping(path = "/info")
    @ResponseBody
    public String getInfo(@RequestParam String token) {
        User user = userService.getInfo(token);
        if (user == null){
            return resultService.getErrResult(MessageErrCode.TOKEN_ERR,"获取信息err");
        }else {
            return resultService.getResult(new UserData(user));
        }
    }

    @PostMapping(path = "/logout")
    @ResponseBody
    public String logout(@RequestParam String token) {
        try {
            userService.logout(token);
        }catch (Exception e){
            return resultService.getErrResult(MessageErrCode.TOKEN_ERR,"登出err");
        }
        return resultService.getResult(null);
    }
}
