package cab.kh.com.access_control_list.controller;


import cab.kh.com.access_control_list.Util.JwtUtil;
import cab.kh.com.access_control_list.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController @RequestMapping("/auth") @RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager am; private final JwtUtil jwt;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req){
        try{
            am.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
            String token = jwt.generateToken(req.getUsername());
            return ResponseEntity.ok(new TokenResponse(token));
        }catch(AuthenticationException ex){ return ResponseEntity.status(401).body("Bad credentials"); }
    }
}

