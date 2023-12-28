package com.mattae.snl.plugins.flowable.services;

import io.github.jbella.snl.core.api.services.MailService;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MailServiceImpl implements MailService {
    @Override
    public void sendEmail(String from, String to, String subject, String content, boolean isMultipart, boolean isHtml) {

    }

    @Override
    public void sendEmail(MimeMessageHelper message) {

    }

    @Override
    public MimeMessageHelper getMimeMessageHelper(boolean isMultipart) {
        return null;
    }

}
