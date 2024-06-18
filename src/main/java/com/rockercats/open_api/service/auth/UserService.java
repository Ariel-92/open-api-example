package com.rockercats.open_api.service.auth;

import com.rockercats.open_api.dto.user.User;

public interface UserService {
//    User createUser(User user);
    User getUserByUsername(String username);

}

