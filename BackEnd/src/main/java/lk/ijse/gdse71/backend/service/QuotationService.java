package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.QuotationDTO;
import lk.ijse.gdse71.backend.entity.Quotation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface QuotationService {

    List<QuotationDTO> getQuotationsByJob(Long jobId);

    QuotationDTO saveQuotationFile(Long jobId, MultipartFile file) throws IOException;
}
