package com.br.projeto.notification.service;

import com.br.projeto.notification.dto.NotificationDTO;
import com.br.projeto.notification.template.NotificationTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    final JavaMailSender emailSender;
    public NotificationService(JavaMailSender emailSender) {this.emailSender = emailSender;}
    public void sendEmail(NotificationDTO notificationDTO) {
        try {
            NotificationTemplate template = new NotificationTemplate();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(template.getSubject()); // Assunto
            message.setTo(notificationDTO.email()); // Email
            message.setFrom(template.getSender()); // PriorizAI
            message.setText(template.getText()); // Mensagem a ser enviada
            emailSender.send(message);
            System.out.println("Enviado com sucesso para o e-mail " + notificationDTO.email());
        } catch (MailException e) {
            System.out.println("Erro ao enviar e-mail " + notificationDTO.email());
            e.getCause();
        }
    }
}
