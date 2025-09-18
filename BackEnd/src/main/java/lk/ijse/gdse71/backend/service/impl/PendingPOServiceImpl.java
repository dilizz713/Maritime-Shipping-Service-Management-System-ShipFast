package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.PendingPODTO;
import lk.ijse.gdse71.backend.entity.PendingPO;
import lk.ijse.gdse71.backend.repo.PendingPORepository;
import lk.ijse.gdse71.backend.service.PendingPOService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PendingPOServiceImpl implements PendingPOService {
    private final PendingPORepository pendingPORepository;

    @Override
    public List<PendingPODTO> getAllPendingPOs() {
        var formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");

        return pendingPORepository.findAll().stream().map(po -> {
            var job = po.getJob();
            return PendingPODTO.builder()
                    .id(po.getId())
                    .description(po.getDescription())
                    .date(po.getDate())
                    .jobId(job.getId())
                    .jobReference(job.getJobReference())
                    .remark(job.getRemark())
                    .status(po.getStatus())
                    .customerName(job.getCustomer().getCompanyName())
                    .vesselName(job.getVessel().getName())
                    .portName(job.getPort().getPortName())
                    .serviceName(job.getService().getServiceName())
                    .employeeName(job.getEmployee().getName())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public void updateDescription(Long pendingPOId, String description) {
        PendingPO pendingPO = pendingPORepository.findById(pendingPOId)
                .orElseThrow(() -> new RuntimeException("Pending PO not found"));

        pendingPO.setDescription(description);
        pendingPORepository.save(pendingPO);
    }
}
