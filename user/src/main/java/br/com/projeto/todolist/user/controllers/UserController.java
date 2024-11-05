package br.com.projeto.todolist.user.controllers;

import br.com.projeto.todolist.user.dtos.LoginDTO;
import br.com.projeto.todolist.user.dtos.UserDTO;
import br.com.projeto.todolist.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserDTO userDTO){
        return userService.save(userDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginDTO loginDTO){
        return userService.login(loginDTO);
    }

    @GetMapping
    public ResponseEntity<?> getAllUser(){
        return ResponseEntity.status(HttpStatus.OK).body("Deu certo");
    }
}
