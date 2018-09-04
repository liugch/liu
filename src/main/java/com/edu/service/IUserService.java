package com.edu.service;

import com.edu.model.User;

import java.util.List;

/**
 * @Author: 何有悠然
 * @ClassName: IUserService
 * @CreateDate: 2018/8/8 16:08
 **/
public interface IUserService {

    List<User> show() throws Exception;

    User getUserById(Integer id) throws Exception;

    boolean delUserById(Integer id) throws Exception;

    User addUser(User user) throws Exception;

    User updateUser(User user) throws Exception;
}
