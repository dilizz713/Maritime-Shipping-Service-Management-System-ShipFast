package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/currencies")
@CrossOrigin
@RequiredArgsConstructor
public class CurrencyController {
    private CurrencyService currencyService;

    @GetMapping("/exchange-rates")
    public Object getExchangeRates(@RequestParam String baseCurrency) {
        return currencyService.getExchangeRates(baseCurrency);
    }
}
