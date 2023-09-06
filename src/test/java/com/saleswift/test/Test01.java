package com.saleswift.test;


import com.saleswift.tool.MD5Crypt;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class Test01 {


    @Test
    public void test01(){
        String encode = new MD5Crypt().encode(123456 + "");
        System.out.println(encode);
    }
}
