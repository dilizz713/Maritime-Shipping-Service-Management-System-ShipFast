package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.ProvisionManageDTO;
import lk.ijse.gdse71.backend.entity.Job;
import lk.ijse.gdse71.backend.repo.JobRepository;
import lk.ijse.gdse71.backend.service.ProvisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProvisionServiceImpl implements ProvisionService {
    private final JobRepository jobRepository;

    @Override
    public List<ProvisionManageDTO> getProvisionJobs() {
        List<Job> jobs = jobRepository.findProvisionJobs();

        return jobs.stream()
                .map(job -> ProvisionManageDTO.builder()
                        .jobId(job.getId())
                        .jobReference(job.getJobReference())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<ProvisionManageDTO> searchProvisionJobs(String ref) {
        List<Job> jobs = jobRepository.searchProvisionJobs(ref);

        return jobs.stream()
                .map(job -> ProvisionManageDTO.builder()
                        .jobId(job.getId())
                        .jobReference(job.getJobReference())
                        .build())
                .collect(Collectors.toList());
    }
}
