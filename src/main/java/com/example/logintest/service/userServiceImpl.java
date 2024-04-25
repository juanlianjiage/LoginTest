package com.example.logintest.service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.logintest.config.JwtUntil;
import com.example.logintest.config.TestResult;
import com.example.logintest.mapper.userMapper;
import com.example.logintest.pojo.user;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
@Service
@Slf4j
public class userServiceImpl extends ServiceImpl<userMapper, user> implements userService {
    private static final String PHONE_REGEX="^1[3-9]\\d{9}$";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public TestResult userLogin(String iphoneNumber, String pwd) {
        if (StringUtils.isEmpty(iphoneNumber)||StringUtils.isEmpty(pwd))
        {return TestResult.fail("非法参数！");
        }
        //检查手机号位数
        if (iphoneNumber.length()!=11)
        {return TestResult.fail("手机号为11为，请检查手机号");
        }
        //检查手机号格式是否正确//1.定义正则表达式
        if (!iphoneNumber.matches(PHONE_REGEX))
        {return TestResult.fail("手机号格式错误，请检查手机号格式！！");
        }
        LambdaQueryWrapper<user> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(user::getIphone, iphoneNumber);
        user user = getOne(queryWrapper); //判断用户是否存在
        if (user==null)
        {
            log.error("手机号为"+iphoneNumber+"的用户不存在");
            return TestResult.fail("用户不存在，请进行注册！");
        }
        //对密码进行加密
        String pwdMd5;
        pwdMd5= DigestUtils.md5DigestAsHex(pwd.getBytes(StandardCharsets.UTF_8));
        if (!pwdMd5.equals(user.getPwd()))
        {
            return handlePasswordFailure(iphoneNumber);
        }
        else {
            return handleSuccessfulLogin(iphoneNumber, pwd);
        }
    }
    private TestResult<?> handleSuccessfulLogin(String iphoneNumber, String pwd) {
        Object o = stringRedisTemplate.opsForValue().get("loginRetry:" + iphoneNumber);
        if (o==null||Integer.parseInt(o.toString())!=0)
        {
            stringRedisTemplate.delete("loginRetry:" + iphoneNumber); //登陆成功，删除重试缓存
            String token = JwtUntil.makeToken(iphoneNumber, pwd); //生成jwt,根据自定义的JwtUntil生成token
            stringRedisTemplate.opsForHash().put("user:Online:"+ iphoneNumber,"token",token);//将用户登录信息保存到Redis
            stringRedisTemplate.expire("user:Online"+ iphoneNumber,30,TimeUnit.MINUTES);//用户不停的访问，不断地刷新过期时间
            log.info("用户"+iphoneNumber+"登录成功！");
            return TestResult.success("登录成功，生成的token为：" + token);
        }
        //账号冻结逻辑
        log.error("手机号为"+iphoneNumber+"的用户账号被冻结！");
        return TestResult.fail("账号被冻结，请明日尝试！");
    }
    private TestResult<Object> handlePasswordFailure(String iphoneNumber) {
        Object userRetry = stringRedisTemplate.opsForValue().get("loginRetry:" + iphoneNumber);
        if (userRetry==null)
        {
            stringRedisTemplate.opsForValue().set("loginRetry:"+ iphoneNumber,"5",24*60, TimeUnit.MINUTES);
        }
        Object retryCount = stringRedisTemplate.opsForValue().get("loginRetry:" + iphoneNumber);

        int i = Integer.parseInt(Objects.requireNonNull(retryCount).toString());
        if (i!=0)//判断剩余重试次数
        {
            stringRedisTemplate.opsForValue().decrement("loginRetry:"+ iphoneNumber,1);
            return TestResult.fail("密码错误，请重试，今日还有" + i + "次机会");
        }
        return TestResult.fail("今日重试次数过多，请明天再重试！");
    }
}
