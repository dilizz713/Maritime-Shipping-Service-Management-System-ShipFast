package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.CurrencyConvertResponse;
import lk.ijse.gdse71.backend.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/currency")
@RequiredArgsConstructor
@CrossOrigin
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping("/convert")
    public CurrencyConvertResponse convert(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam double amount) {
        return currencyService.convert(from, to, amount);
    }
}
