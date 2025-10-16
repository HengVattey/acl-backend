package cab.kh.com.access_control_list.Util;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${app.jwt.secret}") private String secret;
    @Value("${app.jwt.expiration-ms}") private long expirationMs;

    public String generateToken(String username) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationMs))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsername(String token) { return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject(); }
    public boolean validate(String token) {
        try { Jwts.parser().setSigningKey(secret).parseClaimsJws(token); return true; }
        catch (JwtException | IllegalArgumentException e) { return false; }
    }
}
