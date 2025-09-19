package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.JobDTO;
import lk.ijse.gdse71.backend.dto.ProductDTO;
import lk.ijse.gdse71.backend.dto.ProvisionDTO;

import java.util.List;

public interface ProvisionService {
    List<ProductDTO> getAllProductsForCombo();

    ProductDTO getProductDetail(Long id);

    ProvisionDTO saveProvision(ProvisionDTO provisionDTO);

    List<JobDTO> getJobsByServiceNames(List<String> strings);

    String generateNextProvisionReference(Long jobId);
}
