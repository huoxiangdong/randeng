package com.randeng.api.security;

import com.randeng.api.model.Role;
import com.randeng.api.model.User;
import com.randeng.api.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.stream.Collectors;

/**
 * An implementation of the <code>UserDetailsService</code>.
 */
@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username is not found.");
        }
        YeahUserDetails userDetails = new YeahUserDetails();
        userDetails.setUsername(user.getUsername());
        userDetails.setPassword(user.getPassword());
        userDetails.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return userDetails;
    }
}
