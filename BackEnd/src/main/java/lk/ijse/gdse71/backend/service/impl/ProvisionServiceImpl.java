package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.*;
import lk.ijse.gdse71.backend.entity.Job;

import lk.ijse.gdse71.backend.entity.Product;
import lk.ijse.gdse71.backend.entity.Provision;
import lk.ijse.gdse71.backend.entity.ProvisionItem;
import lk.ijse.gdse71.backend.repo.JobRepository;
import lk.ijse.gdse71.backend.repo.ProductRepository;
import lk.ijse.gdse71.backend.repo.ProvisionItemRepository;
import lk.ijse.gdse71.backend.repo.ProvisionRepository;
import lk.ijse.gdse71.backend.service.ProductService;
import lk.ijse.gdse71.backend.service.ProvisionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProvisionServiceImpl implements ProvisionService {
    private final ProductRepository productRepository;
    private final JobRepository jobRepository;
    private final ProvisionRepository provisionRepository;
    private final ProvisionItemRepository provisionItemRepository;
    private final ProductService productService;

    @Override
    public List<ProductDTO> getAllProductsForCombo() {
        return productService.getAllProducts();
    }

    @Override
    public ProductDTO getProductDetail(Long id) {
        return productService.getProductById(id);
    }



    @Override
    public ProvisionDTO saveProvision(ProvisionDTO dto) {
        Job job = jobRepository.findById(dto.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // If provision reference not provided, generate automatically
        if(dto.getProvisionReference() == null || dto.getProvisionReference().isEmpty()){
            long count = provisionRepository.countByJobId(job.getId());
            dto.setProvisionReference(job.getJobReference() + "-PR" + String.format("%05d", count + 1));
        }

        // Create Provision entity
        Provision provision = Provision.builder()
                .provisionReference(dto.getProvisionReference())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .provisionDate(dto.getProvisionDate())
                .job(job)
                .items(new ArrayList<>())
                .build();

        // Create ProvisionItem entities
        List<ProvisionItem> provisionItems = new ArrayList<>();
        for (ProvisionItemDTO itemDTO : dto.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            ProvisionItem item = ProvisionItem.builder()
                    .product(product)
                    .productCode(itemDTO.getProductCode())
                    .productName(itemDTO.getProductName())
                    .uomCode(itemDTO.getUomCode())
                    .unitPrice(itemDTO.getUnitPrice())
                    .quantity(itemDTO.getQuantity())
                    .remark(itemDTO.getRemark())
                    .provision(provision) // set parent
                    .build();

            provisionItems.add(item);
        }

        provision.setItems(provisionItems);

        Provision savedProvision = provisionRepository.save(provision);

        // Return DTO with saved provision reference
        dto.setProvisionReference(savedProvision.getProvisionReference());
        return dto;
    }

    @Override
    public List<JobDTO> getJobsByServiceNames(List<String> serviceNames) {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream()
                .filter(j -> j.getService() != null
                        && j.getService().getServiceName() != null
                        && serviceNames.contains(j.getService().getServiceName()))
                .map(j -> {
                    JobDTO dto = new JobDTO();
                    dto.setId(j.getId());
                    dto.setJobReference(j.getJobReference());
                    dto.setStatus(j.getStatus());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public String generateNextProvisionReference(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        long count = provisionRepository.countByJobId(jobId);

        return job.getJobReference() + "-PR" + String.format("%05d", count + 1);
    }

    @Override
    public List<ProvisionDTO> getProvisionsByJobId(Long jobId) {
        List<Provision> provisions = provisionRepository.findByJobId(jobId);
        return provisions.stream().map(p -> {
            ProvisionDTO dto = new ProvisionDTO();
            dto.setProvisionReference(p.getProvisionReference());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public ProvisionDTO getProvisionDetails(Long jobId, String provisionRef) {
        Provision provision = provisionRepository.findByJobIdAndProvisionReference(jobId, provisionRef)
                .orElseThrow(() -> new RuntimeException("Provision not found"));

        ProvisionDTO dto = new ProvisionDTO();
        dto.setJobId(provision.getJob().getId());
        dto.setProvisionReference(provision.getProvisionReference());
        dto.setDescription(provision.getDescription());
        dto.setStatus(provision.getStatus());
        dto.setProvisionDate(provision.getProvisionDate());

        List<ProvisionItemDTO> items = provision.getItems().stream().map(item -> {
            ProvisionItemDTO itemDTO = new ProvisionItemDTO();
            itemDTO.setProductId(item.getProduct().getId());
            itemDTO.setProductCode(item.getProductCode());
            itemDTO.setProductName(item.getProductName());
            itemDTO.setUomCode(item.getUomCode());
            itemDTO.setUnitPrice(item.getUnitPrice());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setRemark(item.getRemark());
            return itemDTO;
        }).collect(Collectors.toList());

        dto.setItems(items);
        return dto;
    }

    @Override
    public Provision getProvisionByJobAndRef(Long jobId, String provisionRef) {
        return provisionRepository.findByJobIdAndProvisionReference(jobId, provisionRef)
                .orElseThrow(() -> new RuntimeException(
                        "Provision not found for Job ID: " + jobId + " and Provision Ref: " + provisionRef));

    }
}

