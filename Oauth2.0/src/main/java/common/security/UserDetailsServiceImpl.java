package common.security;

import model.TbRole;
import model.TbUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by xiexw on 2017/9/5.
 * 自定义Security登录验证
 */
@Service("UserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TbUser user; String password;
        //模拟通过username获取User实体
//        String[] str = username.split("/");
//        if (str.length == 1) {
//            //原登录方式
//            user = userService.getByUsername(username);
//            password = user.getPassword();
//        } else {
//            //第三方登录
//            user = userService.getByUid(str[0]);
//            password = TP_DEFAULT_PASSWORD;
//        }
        user = new TbUser(username, "admin", "", false);
        password = user.getPassword();
        TbRole role = new TbRole();
        role.setName("ROLE_USER");
        user.setRoles(new HashSet<TbRole>(Collections.singleton(role)));
        return new User(user.getUsername(), password, true, true, true, true, getAuthorities(user));
    }

    /**
     * 获取角色
     *
     * @param user 实体
     * @return authorities
     */
    private Set<GrantedAuthority> getAuthorities(TbUser user) {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        for (TbRole role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

}
