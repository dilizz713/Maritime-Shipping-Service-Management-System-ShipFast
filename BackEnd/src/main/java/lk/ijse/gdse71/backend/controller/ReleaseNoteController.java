package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.ProvisionItemDTO;
import lk.ijse.gdse71.backend.dto.ReleaseNoteDTO;
import lk.ijse.gdse71.backend.entity.Provision;
import lk.ijse.gdse71.backend.service.ProvisionService;
import lk.ijse.gdse71.backend.service.ReleaseNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/release-notes")
@RequiredArgsConstructor
@CrossOrigin
public class ReleaseNoteController {
    private final ProvisionService provisionService;
    private final ReleaseNoteService releaseNoteService;

    @GetMapping("/provision/{ref}")
    public ResponseEntity<?> getProvisionItems(@PathVariable String ref) {
        List<ProvisionItemDTO> items = provisionService.getProvisionItemsByRef(ref);
        return ResponseEntity.ok(items);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveReleaseNotes(@RequestBody List<ReleaseNoteDTO> notes) {
        releaseNoteService.saveReleaseNotes(notes);
        return ResponseEntity.ok("Release notes saved successfully");
    }
}
