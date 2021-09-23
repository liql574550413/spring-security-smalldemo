//package com.li.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
///**  设置用户名密码的第二种方式 配置类
// * @author liql
// * @date 2021/9/21
// */
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        //密码需要加密 不然报 Encoded password does not look like BCrypt 错误
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String password = passwordEncoder.encode("123456");
//        //角色不能不设置，不然报错
//        auth.inMemoryAuthentication().withUser("lql").password(password).roles("");
//
//    }
//
//    //不注入PasswordEncoder 会报下面的错误
//    //java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//}
