package cab.kh.com.access_control_list.controller;
import cab.kh.com.access_control_list.Util.JwtUtil;
import cab.kh.com.access_control_list.dto.LoginRequest;
import cab.kh.com.access_control_list.dto.TokenResponse;
import cab.kh.com.access_control_list.model.User;
import cab.kh.com.access_control_list.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager am;
    private final JwtUtil jwt;
    private final UserRepo userRepo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        try {
            // 1) Authenticate username + password
            am.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
            // 2) Load full user (so we can include roles inside JWT)
            User user = userRepo.findByUsername(req.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            // 3) Generate token with ROLES included
            String token = jwt.generateToken(user);
//            log.info("Login token: {}", token);
            return ResponseEntity.ok(new TokenResponse(token));

        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401).body("Bad credentials");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String bearer) {
        try {
            if (bearer == null || !bearer.startsWith("Bearer ")) {
                return ResponseEntity.status(400).body("Missing Authorization header");
            }
            String oldToken = bearer.substring(7);
            if (!jwt.validate(oldToken)) {
                return ResponseEntity.status(401).body("Invalid or expired token");
            }
            String username = jwt.getUsername(oldToken);
            User user = userRepo.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String newToken = jwt.generateToken(user);
            return ResponseEntity.ok(new TokenResponse(newToken));

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Token refresh failed");
        }
    }
}
