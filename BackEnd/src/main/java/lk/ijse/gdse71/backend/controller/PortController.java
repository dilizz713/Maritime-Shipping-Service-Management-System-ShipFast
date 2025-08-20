package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.service.PortService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/port")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class PortController {
    private final PortService portService;
}
