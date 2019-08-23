package com.endang.cvonline.services;


import com.endang.cvonline.models.UserCV;
import com.endang.cvonline.repositories.UserCVRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserCVServices implements UserDetailsService {

    @Autowired
    UserCVRepositories repositories;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCV usr = repositories.findById(username).get();
        List<GrantedAuthority> grantList = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(usr.getRole());
        grantList.add(authority);

        UserDetails userDetails = new User(usr.getUsername(),usr.getPassword(),grantList);
        return userDetails;
    }
}
