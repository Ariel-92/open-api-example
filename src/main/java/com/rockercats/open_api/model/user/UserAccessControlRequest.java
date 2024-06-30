package com.rockercats.open_api.model.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAccessControlRequest {
    private String userId;
    private String apiPath;
    private long userAccessCnt;
    private long dataViewCnt;
}
