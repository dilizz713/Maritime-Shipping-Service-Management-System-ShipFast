package lk.ijse.gdse71.backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.gdse71.backend.dto.ProvisionInvoiceDTO;
import lk.ijse.gdse71.backend.dto.ProvisionInvoiceItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProvisionInvoiceService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${invoice.api.key}")
    private String apiKey;

    public byte[] generateInvoice(ProvisionInvoiceDTO invoiceDTO) throws Exception {
        String url = "https://invoice-generator.com";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(apiKey);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("from", invoiceDTO.getFrom());
        formData.add("to", invoiceDTO.getTo());
        formData.add("logo", invoiceDTO.getLogoUrl());
        formData.add("number", invoiceDTO.getNumber());
        formData.add("date", invoiceDTO.getDate());
        formData.add("due_date", invoiceDTO.getDueDate());
        formData.add("notes", invoiceDTO.getNotes());
        formData.add("currency", invoiceDTO.getCurrency());

        List<ProvisionInvoiceItemDTO> items = invoiceDTO.getItems();
        for (int i = 0; i < items.size(); i++) {
            ProvisionInvoiceItemDTO item = items.get(i);
            formData.add("items[" + i + "][name]", item.getName());
            formData.add("items[" + i + "][quantity]", item.getQuantity().toString());
            formData.add("items[" + i + "][unit_cost]", item.getUnitCost().toString());
        }

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                byte[].class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Invoice generation failed with status: " + response.getStatusCode());
        }
    }






}