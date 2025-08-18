package lk.ijse.gdse71.backend.controller;

import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse71.backend.dto.AuthDTO;
import lk.ijse.gdse71.backend.dto.AuthResponseDTO;
import lk.ijse.gdse71.backend.dto.SignupDTO;
import lk.ijse.gdse71.backend.repo.UserRepository;
import lk.ijse.gdse71.backend.service.AuthService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<APIResponse> signup(@RequestBody SignupDTO signupDTO){
        authService.signup(signupDTO);
        return ResponseEntity.ok(new APIResponse(200,"User Signup Successfully!" , signupDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(@RequestBody AuthDTO authDTO, HttpServletResponse response) {
        AuthResponseDTO authResponseDTO = authService.authenticate(authDTO);

        //create secure cookie
        ResponseCookie cookie = ResponseCookie.from("accessToken",authResponseDTO.getAccessToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(24 * 60 * 60)   //1 day
                .sameSite("None")
                .build();

        response.addHeader("Set-Cookie",cookie.toString());
        return ResponseEntity.ok(new APIResponse(
                200,
                "User logged in successfully",
                new AuthResponseDTO(null , authResponseDTO.getUserName(),authResponseDTO.getRole())
        ));
    }


}
