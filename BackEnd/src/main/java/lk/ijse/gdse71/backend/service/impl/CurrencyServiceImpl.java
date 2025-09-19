package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
    @Value("${currency.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Object getExchangeRates(String baseCurrency) {
        String url = String.format(
                "https://v6.exchangerate-api.com/v6/%s/latest/%s",
                apiKey, baseCurrency
        );
        return restTemplate.getForObject(url, Object.class);
    }

    @Override
    public double convert(double amount, String from, String to) {
        if (from.equalsIgnoreCase(to)) return amount;
        Object response = getExchangeRates(from);
        Map<?, ?> json = (Map<?, ?>) response;
        Map<?, ?> rates = (Map<?, ?>) json.get("conversion_rates");
        Double rate = Double.valueOf(rates.get(to).toString());

        return amount * rate;
    }
}
