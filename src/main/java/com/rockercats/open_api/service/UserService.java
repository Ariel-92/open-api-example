package com.rockercats.open_api.service;

import com.rockercats.open_api.dto.user.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserService {
// 회원가입 보류
//    User createUser(User user);
    User getUserByUsername(String username);

}

