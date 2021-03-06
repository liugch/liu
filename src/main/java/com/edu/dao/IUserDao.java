package com.edu.dao;

import com.edu.model.User;

import java.util.List;

/**
 * @Author: 何有悠然
 * @ClassName: IUserDao
 * @CreateDate: 2018/8/7 15:06
 **/
public interface IUserDao {
    /**
     * 新增用戶
     *
     * @param user
     * @return
     * @throws Exception
     */
    public int insertUser(User user) throws Exception;

    /**
     * 修改用戶
     *
     * @param user
     * @return
     * @throws Exception
     */
    public int updateUser(User user) throws Exception;

    /**
     * 刪除用戶
     *
     * @param id
     * @return
     * @throws Exception
     */
    public int deleteUser(int id) throws Exception;

    /**
     * 根据id查询用户信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    public User selectUserById(int id) throws Exception;

    /**
     * 查询所有的用户信息
     *
     * @return
     * @throws Exception
     */
    public List<User> selectAllUser() throws Exception;

}
