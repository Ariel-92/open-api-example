package com.rockercats.open_api.repository;

import com.rockercats.open_api.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginLogMapper {
    void insertLoginLog(LoginLog loginLog);
    LoginLog getLoginLogByUuid(String id);
}
