import com.edu.model.User;
import com.edu.service.impl.UserService;
import com.edu.utils.RedisUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;


/**
 * @Author: 何有悠然
 * @ClassName: Test
 * @CreateDate: 2018/8/8 17:48
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class TestAll {

    private static Logger LOGGER = null;
    @Resource
    UserService userService;

    @Resource
    RedisUtil redisUtil;

    @Resource
    RedisTemplate redisTemplate;

    @BeforeClass
    public static void setLogger() throws MalformedURLException {
        System.setProperty("log4j.configurationFile", "log4j2.xml");
        LOGGER = LogManager.getLogger();
    }


    @Test
    public void show() throws Exception {
        // LOGGER.error(userService.show().get(0));
       /* redisTemplate.opsForValue().set("a", "a");
        LOGGER.info(redisTemplate.opsForValue().get("a"));
        redisUtil.sSet("b","b1","b2","b3","b4");
        Set<Object> b = redisUtil.sGet("b");
        LOGGER.info(b);*/
        List<User> show = userService.show();
        LOGGER.info(show);
    }

    @Test
    public  void adduser() throws Exception {
        User user = new User();
        user.setAge(11);
        user.setNickname("test1");
        userService.addUser(user);
    }

    @Test
    public void getUserById() throws Exception {
        User user = userService.getUserById(4);
        LOGGER.info(user);
    }

    @Test
    public void delUserById() throws Exception {
        boolean b = userService.delUserById(31);
    }

    @Test
    public void updateUser() throws Exception {
        User user = new User();
        user.setId(31);
        user.setAge(40);
        user.setNickname("test2");
        user.setMail("3333@qq.com");
        userService.updateUser(user);
    }



}
