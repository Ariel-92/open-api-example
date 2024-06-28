package com.rockercats.open_api.service.auth;

import com.rockercats.open_api.entity.User;
import com.rockercats.open_api.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userMapper.getUserById(username);

        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add((GrantedAuthority) () -> "ROLE_" + user.getRole());
        
        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(), authorityList);
    }
}
