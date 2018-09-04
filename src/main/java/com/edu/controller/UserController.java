package com.edu.controller;


import com.edu.model.User;
import com.edu.service.impl.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;


/**
 *
 * @Author: 何有悠然
 * @ClassName: UserController
 * @CreateDate: 2018/8/7 15:07
 **/
@Controller
public class UserController {
    @Resource
    UserService userService;
    private static Logger LOGGER = LogManager.getLogger();

    @ResponseBody
    @GetMapping(value = "/all")
    public List<User> index() throws Exception {
        return userService.show();
    }


}
