package com.rockercats.open_api.service.auth;

import com.rockercats.open_api.entity.User;

public interface UserService {
    User getUserByUsername(String username);

}

