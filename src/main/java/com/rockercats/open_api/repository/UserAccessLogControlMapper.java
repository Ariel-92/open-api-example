package com.rockercats.open_api.repository;

import com.rockercats.open_api.model.user.UserAccessControlRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAccessLogControlMapper {
    void insertUserAccessLog(UserAccessControlRequest userAccessControlRequest);

    int selectUserAccessLog(UserAccessControlRequest userAccessControlRequest);
}
