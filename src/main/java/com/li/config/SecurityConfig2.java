package com.li.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**  设置用户名密码的第三种方式 通过实现 userDetailsService 接口
 * @author liql
 * @date 2021/9/21
 */
@Configuration
public class SecurityConfig2 extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

       auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    //不注入PasswordEncoder 会报下面的错误
    //java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
 /*      http.formLogin().loginPage("/login.html") //登录界面
               .loginProcessingUrl("/user/login") //用户密码需要提交到的路径 该路径由security管理 不需要我们定义
               .defaultSuccessUrl("/test/index")//登录成功后跳转到的页面
               .permitAll()// 无条件允许访问 但必须先认证然后不校验权限
                .and()
               .authorizeRequests()//后面写认证配置
               .anyRequest() //任何请求
               .authenticated()//都需要认证操作
//               .antMatchers("/","/test/hello").permitAll()  //设置哪些路径可以不用认证就能访问
               .and()
               .csrf().disable();//关闭csrf防护 关闭跨域保护*/

        http.formLogin().loginPage("/login.html") //登录界面
                .loginProcessingUrl("/user/login") //用户密码需要提交到的路径 该路径由security管理 不需要我们定义
                .defaultSuccessUrl("/test/index")//登录成功后跳转到的页面
                .permitAll()// 无条件允许访问 但必须先认证然后不校验权限
                .and()
                .authorizeRequests()
                .antMatchers("/test/hello")
                .hasAnyAuthority("perm_hello")//满足该权限 ，只能写单个权限
                .antMatchers("/test/hello2")
                //满足下面权限的一个即可放行
                .hasAnyAuthority("perm_hello,perm_hello2")
                .antMatchers("/test/hello2")
                //满足下面角色即可放行
                .hasRole("admin")
                .antMatchers("/test/hello3")
                //满足其中的一个角色即可放行
                .hasAnyRole("admin,admin2")
                .anyRequest() //任何请求
                .authenticated()//都需要认证操作
                //下面是开启记住我功能的配置
                .and().
                rememberMe().
                tokenRepository(persistentTokenRepository())//引入操作数据库的类
                .tokenValiditySeconds(600)//设置有效时长，单位秒
                .userDetailsService(userDetailsService)
                .and()
                .csrf().disable();//关闭csrf防护 关闭跨域保护;

        //设置无权限时返回的页面
        http.exceptionHandling().accessDeniedPage("/test/unauthen");
        //配置退出
        http.logout()
                .logoutUrl("/logout") //输入该url表示退出
                .logoutSuccessUrl("/test/logout")//成功退出后跳转的页面
                .permitAll();
    }

    //注入数据源 使用rememberme-me 时需要
    @Autowired
    private DataSource dataSource;
    //配置操作数据库的对象
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        //下面这个api需要导入 orm框架相关的架包 如 mybatisplus
        jdbcTokenRepository.setDataSource(dataSource);
        //下面这个api可以帮我们自动在数据库创建 rememberme-me  时需要的数据库表一般第一次使用的时候打开
        //如果表已经存在会报错
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

}
