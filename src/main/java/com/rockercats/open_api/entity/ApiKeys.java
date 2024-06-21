package com.rockercats.open_api.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ApiKeys {
    private String apiUuid;
    private String apiPath;
    private String userId;
    private Timestamp expiredTime;
}
