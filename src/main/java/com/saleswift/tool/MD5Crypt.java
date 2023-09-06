package com.saleswift.tool;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MD5Crypt {

    public static String encode(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(password);
        return encode;
    }


    public static boolean match(String c1, String c2) {
        System.out.println("c1 "+c1);
        System.out.println("c2 "+c2);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(c1,c2);
    }
}

class Test {
    public static void main(String[] args) {
        String aaa = MD5Crypt.encode("e10adc3949ba59abbe56e057f20f883e");
        //$2a$10$ONAouCn6HXEn/UNe9xQ7OORnQMqxLnelk6AIXbndZZACnD/GPjBm.
        //$2a$10$44m4qWh3lHpOmk6MOoqSIealvEdxuHpmebjP/0sJvevQt6wcI24WO
        System.out.println(MD5Crypt.match("e10adc3949ba59abbe56e057f20f883e",
                "$2a$10$Qrx58lZd3kfwtYrQePbI..IsDTc6k8wOE6mkcmstqA8DwDM9vRZKa"));
    }

}
