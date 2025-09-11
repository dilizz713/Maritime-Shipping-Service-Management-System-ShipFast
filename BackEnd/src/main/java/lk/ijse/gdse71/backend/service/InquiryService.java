package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.InquiryDTO;

import java.util.List;

public interface InquiryService {
    InquiryDTO saveInquiry(InquiryDTO dto);

    InquiryDTO updateInquiry(InquiryDTO dto);

    List<InquiryDTO> getAllInquiries();

    InquiryDTO getInquiryById(Long id);
}
