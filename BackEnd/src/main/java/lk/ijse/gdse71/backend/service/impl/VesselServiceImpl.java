package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.VesselsDTO;
import lk.ijse.gdse71.backend.entity.Customer;
import lk.ijse.gdse71.backend.entity.Vessels;
import lk.ijse.gdse71.backend.entity.VesselsType;
import lk.ijse.gdse71.backend.repo.CustomerRepository;
import lk.ijse.gdse71.backend.repo.VesselsRepository;
import lk.ijse.gdse71.backend.service.VesselService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VesselServiceImpl implements VesselService {
    private final CustomerRepository customerRepository;
    private final VesselsRepository vesselRepository;

    @Override
    public void saveVessel(VesselsDTO dto) {
        Optional<Customer> customerOpt = customerRepository.findById(dto.getCustomerId());
        if (customerOpt.isEmpty()) throw new RuntimeException("Customer not found");
        Vessels vessel = mapToEntity(dto, customerOpt.get());
        vesselRepository.save(vessel);
    }

    @Override
    public void updateVessel(Long id, VesselsDTO dto) {
        Optional<Vessels> existing = vesselRepository.findById(id);
        if (existing.isEmpty()) throw new RuntimeException("Vessel not found");

        Vessels vessel = existing.get();
        vessel.setName(dto.getName());
        vessel.setCapacity(dto.getCapacity());
        vessel.setLocation(dto.getLocation());
        if (dto.getType() != null) vessel.setType(VesselsType.valueOf(dto.getType()));

        if (dto.getCustomerId() != null) {
            Optional<Customer> customerOpt = customerRepository.findById(dto.getCustomerId());
            if (customerOpt.isEmpty()) throw new RuntimeException("Customer not found");
            vessel.setCustomer(customerOpt.get());
        }

        vesselRepository.save(vessel);
    }

    @Override
    public void deleteVessel(Long id) {
        if (!vesselRepository.existsById(id)) throw new RuntimeException("Vessel not found");
        vesselRepository.deleteById(id);
    }

    @Override
    public List<VesselsDTO> getAllVessels() {
        return vesselRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VesselsDTO getVesselById(Long id) {
        Optional<Vessels> vessel = vesselRepository.findById(id);
        if (vessel.isEmpty()) throw new RuntimeException("Vessel not found");
        return mapToDTO(vessel.get());
    }

    private Vessels mapToEntity(VesselsDTO dto, Customer customer) {
        return Vessels.builder()
                .name(dto.getName())
                .capacity(dto.getCapacity())
                .location(dto.getLocation())
                .type(dto.getType() != null ? VesselsType.valueOf(dto.getType()) : null)
                .customer(customer)
                .build();
    }

    private VesselsDTO mapToDTO(Vessels vessel) {
        return VesselsDTO.builder()
                .id(vessel.getId())
                .name(vessel.getName())
                .capacity(vessel.getCapacity())
                .location(vessel.getLocation())
                .type(vessel.getType() != null ? vessel.getType().name() : null)
                .customerId(vessel.getCustomer() != null ? vessel.getCustomer().getId() : null)
                .customerName(vessel.getCustomer() != null ? vessel.getCustomer().getCompanyName() : null)
                .build();
    }
}
