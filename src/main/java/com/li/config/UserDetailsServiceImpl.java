package com.li.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 配置第三种用户密码的方式的实现类  UserDetailsService接口是security框架的
 * @author liql
 * @date 2021/9/21
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    //定义一个安全类集合模拟数据库
    private static ConcurrentHashMap<String,String> mydb=new ConcurrentHashMap();
    static {
        mydb.put("lql", "111");
        mydb.put("xiaohong", "123");
    }

    /**
     *
     * @param username  该参数为登录界面输入的用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username ) throws UsernameNotFoundException {
        //设置权限
        List<GrantedAuthority> authorityList =
                AuthorityUtils
                 //该方法   commaSeparatedStringToAuthorityList 技能设置权限又能设置角色，设置角色时加前缀 ROLE_XXX , 多个权限和角色中间用逗号隔开
                        //ROLE_admin 给用户设置 admin角色
                .commaSeparatedStringToAuthorityList("perm_hello1,perm_hello2,ROLE_admin");

        //模拟通过数据库查密码
        String password=mydb.get(username);
        // if(查库没查到){
        //    throw new UsernameNotFoundException("用户名不存在")
        //  }
        //把数据库查到的密码传入进去 security框架底层会帮我们自动做校验 第三个参数为设置权限和角色，不能为空。
        return new User(username, new BCryptPasswordEncoder().encode(password), authorityList);
    }
}
