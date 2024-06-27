package com.rockercats.open_api.repository;

import com.rockercats.open_api.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User getUserById(String userId);
}
