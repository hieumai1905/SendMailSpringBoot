package com.example.sendmail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@EnableAsync
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendTextMail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        javaMailSender.send(message);
    }

    @Async
    public void sendMailWithAttachment(String to, String subject, String text, String pathToAttachment) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            String fileName = file.getFilename();
            helper.addAttachment(fileName, file);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendHtmlMail(String toEmail, String subject, String htmlBody) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}