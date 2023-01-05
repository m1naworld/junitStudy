package com.example.junit.util;

import org.springframework.stereotype.Component;

@Component // 미리 개발을 위해 만든 가짜
public class MailSenderStub implements MailSender {

    @Override
    public boolean send() {
        return true;
    }
}
