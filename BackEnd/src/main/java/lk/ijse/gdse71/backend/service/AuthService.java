package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.AuthDTO;
import lk.ijse.gdse71.backend.dto.AuthResponseDTO;
import lk.ijse.gdse71.backend.dto.SignupDTO;
import lk.ijse.gdse71.backend.entity.User;

import java.util.List;

public interface AuthService {
    String signup(SignupDTO signupDTO);

    AuthResponseDTO authenticate(AuthDTO authDTO);


    List<SignupDTO> getAllUsers();

    void updateRole(Long userId, String role);
}
