package cn.kiroe.mall.user.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Kiro
 * @Date 2024/01/29 11:29
 **/
@SpringBootTest
class UserServiceImplTest {

      void testMd5(){
        String s = DigestUtils.md5DigestAsHex("111111".getBytes());
        System.out.println("s = " + s);
    }

}