package com.rockercats.open_api.service.auth;

import com.rockercats.open_api.model.User;

public interface UserService {
    User getUserByUsername(String username);

}

