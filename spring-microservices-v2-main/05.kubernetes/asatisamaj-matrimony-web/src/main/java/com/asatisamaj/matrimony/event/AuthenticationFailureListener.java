package com.asatisamaj.matrimony.event;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.asatisamaj.matrimony.bruteforce.BruteForceProtectionService;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private static Logger LOG  = LoggerFactory.getLogger(AuthenticationFailureListener.class);

    @Resource(name="bruteForceProtectionService")
    private BruteForceProtectionService bruteForceProtectionService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String username = event.getAuthentication().getName();
        bruteForceProtectionService.registerLoginFailure(username);
    }
}
