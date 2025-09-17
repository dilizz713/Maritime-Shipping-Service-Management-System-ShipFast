package lk.ijse.gdse71.backend.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmailWithAttachment(String to, String subject, String text, byte[] attachment, String attachmentName) throws MessagingException;
    void sendEmailWithOptionalAttachment(String to, String subject, String text, byte[] attachment, String attachmentName) throws MessagingException;
}
