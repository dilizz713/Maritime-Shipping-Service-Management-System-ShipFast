package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.MeetingDTO;
import lk.ijse.gdse71.backend.entity.Employee;
import lk.ijse.gdse71.backend.entity.Meeting;
import lk.ijse.gdse71.backend.repo.EmployeeRepository;
import lk.ijse.gdse71.backend.service.MeetingService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/meetings")
@AllArgsConstructor
@CrossOrigin
public class MeetingController {

    private final MeetingService meetingService;
    private final EmployeeRepository employeeRepository;

    @PostMapping("/create")
    public ResponseEntity<APIResponse> createMeeting(@RequestBody MeetingDTO dto) {
        Meeting meeting = meetingService.createMeeting(dto);
        return ResponseEntity.ok(new APIResponse(201, "Meeting created", meeting));
    }

    @GetMapping("/by-code/{code}")
    public ResponseEntity<APIResponse> getByCode(@PathVariable String code) {
        Meeting m = meetingService.getByCode(code);
        if (m == null) return ResponseEntity.status(404).body(new APIResponse(404,"Not found",null));
        return ResponseEntity.ok(new APIResponse(200,"Found",m));
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<APIResponse> getEmployee(@PathVariable Long id) {
        Employee e = employeeRepository.findById(id).orElse(null);
        if (e == null) return ResponseEntity.status(404).body(new APIResponse(404,"Employee not found",null));
        return ResponseEntity.ok(new APIResponse(200,"Found",e));
    }


}
