package cn.rookiex.analyze.service;

import cn.rookiex.analyze.dao.UserRepository;
import cn.rookiex.analyze.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author rookiex
 * @date 2020/12/15 10:31
 * @des
 */
@Service
public class UserService {

    public static final String MD5_KEY = "ziHan";
    public static final String Delimiter = "::";

    @Resource
    UserRepository userRepository;

    public String login(String userName, String password) {
        User byUserNameAndPassword = userRepository.findByUserNameAndPassword(userName, password);
        if (byUserNameAndPassword != null){
            String token1 = byUserNameAndPassword.getToken();
            if (token1 != null &&  !token1.isEmpty()){
                return token1;
            }

            int currentTime = (int) (System.currentTimeMillis() / 1000);
            String token = MD5_KEY + Delimiter + userName + Delimiter + currentTime;
            byUserNameAndPassword.setToken(token);
            userRepository.save(byUserNameAndPassword);
            return token;
        }
        return null;
    }

    public User getInfo(String token) {
        return userRepository.findByToken(token);
    }

    public void logout(String token) {
        User byToken = userRepository.findByToken(token);
        if (byToken != null){
            byToken.setToken("");
            userRepository.save(byToken);
        }
    }
}
