package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.JobDTO;
import lk.ijse.gdse71.backend.dto.ProductDTO;
import lk.ijse.gdse71.backend.dto.ProvisionDTO;
import lk.ijse.gdse71.backend.entity.Provision;

import java.util.List;

public interface ProvisionService {
    List<ProductDTO> getAllProductsForCombo();

    ProductDTO getProductDetail(Long id);

    ProvisionDTO saveProvision(ProvisionDTO provisionDTO);

    List<JobDTO> getJobsByServiceNames(List<String> strings);

    String generateNextProvisionReference(Long jobId);

    List<ProvisionDTO> getProvisionsByJobId(Long jobId);

    ProvisionDTO getProvisionDetails(Long jobId, String provisionRef);

    Provision getProvisionByJobAndRef(Long jobId, String provisionRef);
}
