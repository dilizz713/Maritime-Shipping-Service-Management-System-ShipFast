package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.CurrencyConvertResponse;
import lk.ijse.gdse71.backend.service.CurrencyService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_URL = "https://api.exchangerate.host/convert";

    @Override
    public CurrencyConvertResponse convert(String from, String to, double amount) {
        String url = API_URL + "?from=" + from + "&to=" + to + "&amount=" + amount;
        return restTemplate.getForObject(url, CurrencyConvertResponse.class);
    }
}
