package com.asatisamaj.matrimony.event;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.asatisamaj.matrimony.bruteforce.BruteForceProtectionService;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private static Logger LOG  = LoggerFactory.getLogger(AuthenticationSuccessListener.class);

    @Resource(name="bruteForceProtectionService")
    private BruteForceProtectionService bruteForceProtectionService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username= event.getAuthentication().getName();
        LOG.info("********* login successful for user {} ", username);
        bruteForceProtectionService.resetBruteForceCounter(username);
    }
}
