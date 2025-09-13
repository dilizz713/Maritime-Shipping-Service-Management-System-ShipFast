package lk.ijse.gdse71.backend.controller;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lk.ijse.gdse71.backend.dto.QuotationRequestDTO;
import lk.ijse.gdse71.backend.service.impl.BillNumberGenerator;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quotation")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class QuotationController {
    private final JavaMailSender mailSender;



    @PostMapping("/request")
    public ResponseEntity<APIResponse> requestQuotation(@RequestBody QuotationRequestDTO dto) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("EMAIL_USERNAME");
            helper.setTo("dilinichamikasilva@gmail.com");
            helper.setSubject("New Quotation Request from " + dto.getCompanyName());
            helper.setText(
                    "<h3>Quotation Request Details</h3>" +
                            "<p><strong>Company:</strong> " + dto.getCompanyName() + "</p>" +
                            "<p><strong>Email:</strong> " + dto.getEmail() + "</p>" +
                            "<p><strong>Port:</strong> " + dto.getHarbour() + "</p>" +
                            "<p><strong>Position:</strong> " + dto.getPosition() + "</p>" +
                            "<p><strong>Service:</strong> " + dto.getService() + "</p>" +
                            "<p><strong>Message:</strong><br>" + dto.getMessage() + "</p>",
                    true
            );

            mailSender.send(message);

            return ResponseEntity.ok(new APIResponse(200, "Quotation request sent successfully!", true));
        } catch (MessagingException e) {
            log.error("Email sending failed: ", e);
            return ResponseEntity.status(500)
                    .body(new APIResponse(500, "Failed to send email. Please try again later.", false));
        }
    }



}
