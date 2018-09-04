package com.edu.service.impl;

import com.edu.dao.IUserDao;
import com.edu.model.User;
import com.edu.service.IUserService;
import com.edu.utils.RedisUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 何有悠然
 * @ClassName: UserService
 * @CreateDate: 2018/8/8 15:59
 **/
@Service(value = "userService")
@Transactional
public class UserService implements IUserService {

    private Logger LOGGER = LogManager.getLogger(UserService.class);

    @Resource
    IUserDao userDao;

    @Resource
    RedisUtil redisUtil;

    @Override
    @Cacheable(value = "allUser")
    public List<User> show() throws Exception {
        LOGGER.info("我执行了这条就没哟走缓存!!!show()");
        List<User> list = userDao.selectAllUser();
        return list;
    }

    @Override
    @CachePut(value = {"user1"}, key = "'user_'+#user.id", condition = "#result.id ne null")
    public User addUser(User user) throws Exception {
        int rs = userDao.insertUser(user);
        LOGGER.info("插入这个用户完成!" + user);
        //return user.getId(); //返回id
        return user; //返回受影响的行

    }


    // 把id>0条件 的某个用户 放到名叫'user'的缓存中,并指定key的名称,
    // 在执行之前就会判断缓存中是否存在,是: 直接从缓存中去,否则:查找后放入到缓存中
    @Override
    @Cacheable(value = {"user1"}, key = "'user_'+#id", condition = "#id gt 0")
    public User getUserById(Integer id) throws Exception {
        LOGGER.info("我执行了这条就没哟走缓存!!!getUserById()");
        User user = userDao.selectUserById(id);
        return user;
    }


    // 把结果放入到缓存中
    @Override
    @CachePut(value = {"user1"}, key = "'user_'+#user.id", condition = "#result.id gt 0")
    public User updateUser(User user) throws Exception {
        LOGGER.info("我执行了这条就没哟走缓存!!!updateUser(User user)");
        userDao.updateUser(user);
        LOGGER.info(user);
        return user;
    }


    @Override
    @CacheEvict(value = {"user1"}, key = "'user_'+#id")
    public boolean delUserById(Integer id) throws Exception {
        int rs = userDao.deleteUser(id);
        LOGGER.info("删除该用户完成!");
        return rs > 0 ? true : false;
    }
}
