package com.rockercats.open_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class LoginLog {
    @Id
    private String id;
    private String userId;
    private Timestamp accessTime;
    private Timestamp expiredTime;
    private String refreshToken;
    private String role;
}
