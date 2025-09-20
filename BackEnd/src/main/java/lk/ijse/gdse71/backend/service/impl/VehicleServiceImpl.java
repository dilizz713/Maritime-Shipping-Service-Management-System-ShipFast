package lk.ijse.gdse71.backend.service.impl;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lk.ijse.gdse71.backend.dto.CustomerDTO;
import lk.ijse.gdse71.backend.dto.VehicleDTO;
import lk.ijse.gdse71.backend.entity.Customer;
import lk.ijse.gdse71.backend.entity.Vehicle;
import lk.ijse.gdse71.backend.exception.ResourceNotFoundException;
import lk.ijse.gdse71.backend.repo.VehicleLocationRepository;
import lk.ijse.gdse71.backend.repo.VehicleRepository;
import lk.ijse.gdse71.backend.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final String uploadDir = "uploads/";
    private final ModelMapper mapper;
    private final Cloudinary cloudinary;

    private String uploadToCloudinary(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("folder", "vehicles"));
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed", e);
        }
    }

    @Autowired
    private final VehicleRepository vehicleRepository;

    private String saveFile(MultipartFile file) {
        try {
            String filename = System.currentTimeMillis() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            Files.copy(file.getInputStream(), Paths.get(uploadDir + filename));
            return uploadDir + filename;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateVehicle(Long id, VehicleDTO vehicleDTO, MultipartFile file) {

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        vehicle.setName(vehicleDTO.getName());
        vehicle.setNumberPlate(vehicleDTO.getNumberPlate());
        vehicle.setType(vehicleDTO.getType());
        vehicle.setModel(vehicleDTO.getModel());
        vehicle.setStatus(vehicleDTO.getStatus());

        if (file != null && !file.isEmpty()) {
            vehicle.setImage(uploadToCloudinary(file));
        }

        vehicleRepository.save(vehicle);
    }

    @Override
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public Object getVehicleById(Long id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles.stream().map(v -> {
            VehicleDTO dto = new VehicleDTO();
            dto.setId(v.getId());
            dto.setName(v.getName());
            dto.setNumberPlate(v.getNumberPlate());
            dto.setType(v.getType());
            dto.setModel(v.getModel());
            dto.setStatus(v.getStatus());
            dto.setImage(v.getImage());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void addVehicle(VehicleDTO vehicleDTO, MultipartFile file) {
        Vehicle vehicle = new Vehicle();
        vehicle.setName(vehicleDTO.getName());
        vehicle.setNumberPlate(vehicleDTO.getNumberPlate());
        vehicle.setType(vehicleDTO.getType());
        vehicle.setModel(vehicleDTO.getModel());
        vehicle.setStatus(vehicleDTO.getStatus());

        if (file != null && !file.isEmpty()) {
            vehicle.setImage(uploadToCloudinary(file));
        }

        vehicleRepository.save(vehicle);
    }
}
