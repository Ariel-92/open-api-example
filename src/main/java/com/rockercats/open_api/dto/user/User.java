package com.rockercats.open_api.dto.user;

import com.rockercats.open_api.constants.GrantType;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class User {
    private String userId;
    private String password;
    private String refreshToken;
    private String grantType;

    public static User from(SignUpRequest request, PasswordEncoder encoder) {	// 파라미터에 PasswordEncoder 추가
        return User.builder()
                .userId(request.getUserId())
                .password(encoder.encode(request.getPassword()))	// pw 암호화
                .grantType(GrantType.USER.name()) // 기본값 USER 고정
                .build();
    }

// 업데이트 기능 보류
//    public void update(UserUpdateRequest newUser, PasswordEncoder encoder) {	// 파라미터에 PasswordEncoder 추가
//        this.password = newUser.newPassword() == null || newUser.newPassword().isBlank()
//                ? this.password : encoder.encode(newUser.newPassword());	// 수정
//        this.name = newUser.name();
//    }

    public Collection<GrantedAuthority> getAuthorities() {
        return null;
    }
}