package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.ReleaseNoteDTO;
import lk.ijse.gdse71.backend.entity.Product;
import lk.ijse.gdse71.backend.entity.ProvisionItem;
import lk.ijse.gdse71.backend.entity.ReleaseNote;
import lk.ijse.gdse71.backend.repo.ProductRepository;
import lk.ijse.gdse71.backend.repo.ProvisionItemRepository;
import lk.ijse.gdse71.backend.repo.ReleaseNoteRepository;
import lk.ijse.gdse71.backend.service.ReleaseNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReleaseNoteServiceImpl implements ReleaseNoteService {
    private final ReleaseNoteRepository releaseNoteRepository;
    private final ProvisionItemRepository provisionItemRepository;
    private final ProductRepository productRepository;

    @Override
    public void saveReleaseNotes(List<ReleaseNoteDTO> notes) {
        for (ReleaseNoteDTO dto : notes) {
            ProvisionItem provisionItem = provisionItemRepository.findById(dto.getProvisionItemId())
                    .orElseThrow(() -> new RuntimeException("Provision item not found"));

            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            ReleaseNote note = ReleaseNote.builder()
                    .date(new Date())
                    .stockQty(provisionItem.getQuantity())
                    .remark(dto.getRemark())
                    .item(provisionItem)
                    .product(product)
                    .build();

            releaseNoteRepository.save(note);
        }
    }
}
