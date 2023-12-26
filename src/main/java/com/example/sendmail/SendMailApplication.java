package com.example.sendmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class SendMailApplication {

    @Autowired
    private EmailSenderService emailSenderService;

    public static void main(String[] args) {
        SpringApplication.run(SendMailApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sendMAil() {
        // Gửi văn bản
        emailSenderService.sendTextMail("hieumai1905it@gmail.com", "Subject", "This is a text email.");

        // Gửi tập tin đính kèm
        String attachmentPath = "/Users/Documents/java/springboot/sendMail/src/main/java/com/example/sendmail/SendMailApplication.java";
        emailSenderService.sendMailWithAttachment("hieumai1905it@gmail.com", "Subject", "Text with attachment", attachmentPath);

        // Gửi HTML
        String htmlBody = "<html><body><h1>This is an HTML email.</h1></body></html>";
        emailSenderService.sendHtmlMail("hieumai1905it@gmail.com", "Subject", htmlBody);

    }

}
