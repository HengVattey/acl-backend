package cab.kh.com.access_control_list.configuration;
// package com.example.acl.security
import cab.kh.com.access_control_list.Util.JwtUtil;
import cab.kh.com.access_control_list.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

@Component @RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService uds;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        String header = req.getHeader("Authorization");
        String token = null, username = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            if (jwtUtil.validate(token)) username = jwtUtil.getUsername(token);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = uds.loadUserByUsername(username);
            var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(req, res);
    }
}
