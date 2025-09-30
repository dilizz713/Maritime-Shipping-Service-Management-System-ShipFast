package lk.ijse.gdse71.backend.service;

import jakarta.mail.MessagingException;
import lk.ijse.gdse71.backend.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface InquiryService {
    InquiryDTO saveInquiry(InquiryDTO dto) throws IOException;

    InquiryDTO updateInquiry(InquiryDTO dto) throws IOException;

    List<InquiryDTO> getAllInquiries();

    InquiryDTO getInquiryById(Long id);

    void updateInquiryFromExcel(Long inquiryId, byte[] excelData) throws Exception;

    String saveExcelForInquiry(Long inquiryId, MultipartFile file) throws IOException;

    List<Map<String, Object>> parseExcelToJson(MultipartFile file) throws IOException;

    InquiryItemDTO updateInquiryItem(Long inquiryId, Long itemId, InquiryItemDTO dto);

    void deleteInquiryItem(Long inquiryId, Long itemId);

    ConfirmInquiryDTO confirmInquiry(Long inquiryId, String description) throws MessagingException;

    List<ReceivedProductCheckDTO> getProductsToVerify(Long confirmId);

    GRNDTO saveVerifiedProducts(Long confirmId, List<ReceivedProductCheckDTO> products);

    Long getConfirmIdByInquiryId(Long inquiryId);

    ConfirmInquiryDTO getConfirmedInquiryById(Long confirmId);
}
