package com.asatisamaj.matrimony.service;

import javax.mail.MessagingException;

import com.asatisamaj.matrimony.context.AbstractEmailContext;

public interface EmailService {

    void sendMail(final AbstractEmailContext email) throws MessagingException;
}
