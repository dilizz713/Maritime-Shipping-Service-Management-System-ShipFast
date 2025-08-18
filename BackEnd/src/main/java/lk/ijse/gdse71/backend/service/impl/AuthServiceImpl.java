package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.AuthDTO;
import lk.ijse.gdse71.backend.dto.AuthResponseDTO;
import lk.ijse.gdse71.backend.dto.SignupDTO;
import lk.ijse.gdse71.backend.entity.Role;
import lk.ijse.gdse71.backend.entity.User;
import lk.ijse.gdse71.backend.exception.ResourceAlreadyExists;
import lk.ijse.gdse71.backend.exception.ResourceNotFoundException;
import lk.ijse.gdse71.backend.repo.UserRepository;
import lk.ijse.gdse71.backend.service.AuthService;
import lk.ijse.gdse71.backend.util.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @Override
    public String signup(SignupDTO signupDTO) {
        if(userRepository.findByUsernameAndName(
                signupDTO.getUsername(),
                signupDTO.getName()
                ).isPresent()){
            throw new ResourceAlreadyExists("This User is already exists");
        }
        User user = User.builder()
                .name(signupDTO.getName())
                .username(signupDTO.getUsername())
                .password(passwordEncoder.encode(signupDTO.getPassword()))
                .role(Role.CUSTOMER)
                .build();
        userRepository.save(user);
        return "User signup successfully";
    }

    @Override
    public AuthResponseDTO authenticate(AuthDTO authDTO) {
        User user = userRepository.findByUsername(authDTO.getUsername()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if(!passwordEncoder.matches(
                authDTO.getPassword(),
                user.getPassword())){
            throw new BadCredentialsException("Invalid Credentials");
        }

        String token = jwtUtil.generateToken(authDTO.username);
        return new AuthResponseDTO(token , user.getUsername() , user.getRole().name());
    }
}
