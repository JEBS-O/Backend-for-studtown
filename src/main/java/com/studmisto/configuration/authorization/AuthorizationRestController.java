package com.studmisto.configuration.authorization;

import com.studmisto.entities.User;
import com.studmisto.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthorizationRestController {
    private final AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;

    public AuthorizationRestController(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
            this.authenticationManager = authenticationManager;
            this.userRepository = userRepository;
            this.jwtTokenProvider = jwtTokenProvider;
        }

        @PostMapping("/login")
        public ResponseEntity<?> authenticate(@RequestParam String email, @RequestParam String password) {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
                User user = userRepository.findByEmail(email);
                String token = jwtTokenProvider.createToken(email, user.getRole().name());
                Map<Object, Object> response = new HashMap<>();
                response.put("email", email);
                response.put("token", token);
                return ResponseEntity.ok(response);
            } catch (AuthenticationException e) {
                return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
            }
        }

        @PostMapping("/logout")
        public void logout(HttpServletRequest request, HttpServletResponse response) {
            SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
            securityContextLogoutHandler.logout(request, response, null);
        }
    }
