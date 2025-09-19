package lk.ijse.gdse71.backend.service;

public interface CurrencyService {
    Object getExchangeRates(String baseCurrency);
    double convert(double amount, String from, String to);
}
