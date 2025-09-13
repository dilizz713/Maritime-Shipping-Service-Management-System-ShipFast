package lk.ijse.gdse71.backend.service.impl;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BillNumberGenerator {
    private final AtomicInteger dailyCounter = new AtomicInteger(0);
    private String lastDate = "";

    public synchronized String generateBillNumber() {
        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        if (!today.equals(lastDate)) {
            dailyCounter.set(0);
            lastDate = today;
        }
        int count = dailyCounter.incrementAndGet();
        return String.format("BILL-%s-%04d", today, count);
    }
}
