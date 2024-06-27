package com.rockercats.open_api.controller;

import com.rockercats.open_api.service.UserAccessService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
public class UserAccessController {
    private final UserAccessService userAccessService;

    @GetMapping(value= "/public/checkUserAccess")
    public ResponseEntity<Boolean> checkUserAccess(HttpServletRequest request)  {
        return userAccessService.insertUserAccessLog(request);
    }

    @GetMapping(value= "/protected/checkUserAccess")
    public ResponseEntity<Boolean> checkUserProtectedAccess(HttpServletRequest request)  {
        return userAccessService.insertUserAccessLog(request);
    }
}
