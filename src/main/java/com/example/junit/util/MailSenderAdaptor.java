package com.example.junit.util;

import org.springframework.stereotype.Component;

// 추후에 mail 클래스가 완성되면 코드를 완성하면 됨
//@Component
public class MailSenderAdaptor implements MailSender {
//    private Mail mail;
//    public MailSenderAdaptor() {
//        this.mail = new Mail();
//    }

    @Override
    public boolean send() {
//        return mail.sendMail();
        return true;
    }
}
