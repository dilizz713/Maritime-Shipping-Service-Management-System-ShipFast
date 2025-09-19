package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/reports")
@RequiredArgsConstructor
@CrossOrigin
public class ReportsController {
    private final JobService jobService;

    @GetMapping("/jobs")
    public ResponseEntity<?> getJobsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate
    ) {
        List<Map<String, Object>> result = jobService.getJobsByDateRange(fromDate, toDate);
        return ResponseEntity.ok(result);
    }
}
