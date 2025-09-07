package lk.ijse.gdse71.backend.controller;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {
    private final JavaMailSender mailSender;

    @GetMapping("/email")
    public String testEmail() {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("dilinichamikasilva@gmail.com");
            helper.setTo("dilinichamikasilva@gmail.com");
            helper.setSubject("Test Email");
            helper.setText("<h1>Hello World!</h1>", true);
            mailSender.send(message);
            return "Email Sent!";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Email Failed!";
        }
    }
}
