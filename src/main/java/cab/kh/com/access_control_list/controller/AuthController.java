package cab.kh.com.access_control_list.controller;
import cab.kh.com.access_control_list.Util.JwtUtil;
import cab.kh.com.access_control_list.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;


@RestController @RequestMapping("/auth") @RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager am; private final JwtUtil jwt;

    @PostMapping("/login")
    public ResponseEntity<?> login( @RequestBody LoginRequest req){
        try{
            am.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
            String token = jwt.generateToken(req.getUsername());
            System.out.println(req.getUsername());
            return ResponseEntity.ok(new TokenResponse(token));
        }catch(AuthenticationException ex){
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
            String newToken = jwt.generateToken(username);

            return ResponseEntity.ok(new TokenResponse(newToken));
        } catch (Exception e)
        {
            return ResponseEntity.status(500).body("Token refresh failed");
        }
    }

}

