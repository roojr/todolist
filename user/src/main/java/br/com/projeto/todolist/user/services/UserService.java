package br.com.projeto.todolist.user.services;


import br.com.projeto.todolist.user.dtos.*;
import br.com.projeto.todolist.user.models.User;
import br.com.projeto.todolist.user.pub.config.security.TokenService;
import br.com.projeto.todolist.user.pub.exception.BusinessException;
import br.com.projeto.todolist.user.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public ResponseEntity<?> delete(Long id) {
        Optional<User> userDB = userRepository.findById(id);
        if (userDB.isPresent()) {
            User user = userDB.get();
            user.setInActive(false);
            userRepository.save(user);

            return ResponseEntity.ok("User successfully deleted.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

    public ResponseEntity<?> findUserByFilter(UserFilterDTO userFilterDTO) {

        if(userFilterDTO.id() != null) {
            Optional<User> userDB = userRepository.findById(userFilterDTO.id());

            if(userDB.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id not found.");
            }

            return ResponseEntity.status(HttpStatus.OK).body(UserFilterDTO.fromUserFilterDTO(userDB.get()));
        }

        if(userFilterDTO.username() != null) {
            List<Optional<User>> userDB = userRepository.findUsersByUsernameContainingIgnoreCase(userFilterDTO.username());

            if(userDB.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username not found.");
            }

            List<UserFilterDTO> responseUserDTO = userDB.stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(UserFilterDTO::fromUserFilterDTO)
                    .toList();

            return ResponseEntity.status(HttpStatus.OK).body(responseUserDTO);
        }

        if(userFilterDTO.email() != null) {
            List<Optional<User>> userDB = userRepository.findUserByEmailContainingIgnoreCase(userFilterDTO.email());

            if(userDB.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("E-mail not found.");
            }

            List<UserFilterDTO> responseUserDTO = userDB.stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(UserFilterDTO::fromUserFilterDTO)
                    .toList();

            return ResponseEntity.status(HttpStatus.OK).body(responseUserDTO);
        }

        if(userFilterDTO.cpf() != null) {
            Optional<User> userDB = userRepository.findUserByCpf(userFilterDTO.cpf());

            if(userDB.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CPF not found.");
            }

            return ResponseEntity.status(HttpStatus.OK).body(UserFilterDTO.fromUserFilterDTO(userDB.get()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");

    }
}
