package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.CurrencyConvertResponse;

public interface CurrencyService {
    CurrencyConvertResponse convert(String from, String to, double amount);
}
