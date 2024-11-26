package br.com.projeto.todolist.user.services;


import br.com.projeto.todolist.user.dtos.LoginDTO;
import br.com.projeto.todolist.user.dtos.TokenInfo;
import br.com.projeto.todolist.user.dtos.UserDTO;
import br.com.projeto.todolist.user.dtos.UserResponseDTO;
import br.com.projeto.todolist.user.models.User;
import br.com.projeto.todolist.user.pub.config.security.TokenService;
import br.com.projeto.todolist.user.pub.exception.BusinessException;
import br.com.projeto.todolist.user.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenService;

    public ResponseEntity<Object> save(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findUserByUsername(userDTO.username());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        String passwordCripted = new BCryptPasswordEncoder().encode(userDTO.password());
        User user = UserDTO.fromUserDTO(userDTO);
        user.setPassword(passwordCripted);
        user.setInActive(Boolean.TRUE);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseDTO.toDTO(user));
    }

    public ResponseEntity<?> login(LoginDTO loginDTO) {
        Optional<User> userDB = userRepository.findUserByUsername(loginDTO.username());
        if (userDB.isPresent()) {
            User user = userDB.get();
            var userPassword = new UsernamePasswordAuthenticationToken(user.getUsername(), loginDTO.password());
            try {
                Authentication authentication = authenticationManager.authenticate(userPassword);
                TokenInfo token = tokenService.generateToken((User) authentication.getPrincipal());
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
            } catch (BadCredentialsException e) {
                throw new BadCredentialsException("Invalid credentials. Check the username and password.");
            }
        }
        throw new BusinessException("Username not found");
    }

    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = tokenService.recoverToken(request);
        if(token != null) {
            tokenService.invalidateToken(token);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Logout successful");
    }
}
