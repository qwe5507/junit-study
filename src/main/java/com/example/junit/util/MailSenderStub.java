package com.example.junit.util;

import org.springframework.stereotype.Component;

@Component
public class MailSenderStub implements MailSender { // Stub은 가정 이라는 뜻
    @Override
    public boolean send() {
        return true;
    }
}
