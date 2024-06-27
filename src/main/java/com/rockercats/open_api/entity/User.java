package com.rockercats.open_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {
    @Id
    private String id;
    private String userId;
    private String password;
    private String role;
}