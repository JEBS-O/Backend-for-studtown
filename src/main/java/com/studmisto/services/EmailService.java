package com.studmisto.services;

import com.studmisto.entities.Appeal;
import com.studmisto.entities.User;
import com.studmisto.entities.enums.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final UserService userService;

    @Value("${spring.mail.username}")
    private String username;

    public EmailService(JavaMailSender mailSender, UserService userService) {
        this.mailSender = mailSender;
        this.userService = userService;
    }

    @Async
    public void sendEmail(String subject, String text, String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setSubject(subject);
        message.setText(text);
        message.setTo(to);
        mailSender.send(message);
    }

    @Async
    public void sendNotificationAboutNewAppeal(Appeal appeal) {
        String subject = "Надійшло нове звернення";
        String text = "Тема звернення: " + appeal.getTitle() + "\nВід: " + appeal.getAuthor().toString() + "\nПереглянути: qweqweqwe.com";
        for(User user : userService.getUsers(appeal.getRecipient())) {
            sendEmail(subject, text, user.getEmail());
        }
        for(User user : userService.getUsers(Status.ADMINISTRATION)) {
            sendEmail(subject, text, user.getEmail());
        }
    }

    @Async
    public void sendNotificationAboutNewMessage(User messageSender, Appeal appeal) {
        String subject = "Прийшла нова відповідь щодо звернення " + appeal.getTitle();
        String text = "Переглянути: link";
        if(messageSender.getId().longValue() == appeal.getAuthor().getId().longValue()) {
            for(User user : userService.getUsers(appeal.getRecipient())) {
                sendEmail(subject, text, user.getEmail());
            }
            for(User user : userService.getUsers(Status.ADMINISTRATION)) {
                sendEmail(subject, text, user.getEmail());
            }
        } else {
            sendEmail(subject, text, messageSender.getEmail());
        }
    }

    @Async
    public void sendCongratulationForNewUser(String email, String password) {
        String subject = "Дані для входу в обліковий запис мешканця гуртожитку";
        String text = formulateCongratulationForUser(email, password);
        sendEmail(subject, text, email);
    }

    private String formulateCongratulationForUser(String email, String password) {
        return "Вітаємо з реєстрацією у системі STUDMISTO від ІФНТУНГ. Розповімо коротко про сайт:\n" +
                "...\n...\n...\n" +
                "Ваші дані для входу: (при першому вході поміняйте, обов'язково, пароль)\n" +
                "email: " + email +
                "\npassword: " + password;
    }
}
