package lk.ijse.gdse71.backend.dto;

import lombok.Data;

@Data
public class CurrencyConvertResponse {
    private boolean success;
    private Query query;
    private Info info;
    private double result;

    @Data
    public static class Query {
        private String from;
        private String to;
        private double amount;
    }

    @Data
    public static class Info {
        private double rate;
    }
}
