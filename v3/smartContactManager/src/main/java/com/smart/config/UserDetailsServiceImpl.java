package com.smart.config;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //Fetch the User from Database and return to UserDetails
        User userByUserName = userRepository.getUserByUserName(s);
        if(userByUserName == null){
            throw new UsernameNotFoundException("No User Found With this UserName");
        }
        CustomUserDetails customUserDetails = new CustomUserDetails(userByUserName);
        return customUserDetails;
    }
}
