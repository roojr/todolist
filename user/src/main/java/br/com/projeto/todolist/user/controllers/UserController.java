package br.com.projeto.todolist.user.controllers;

import br.com.projeto.todolist.user.dtos.LoginDTO;
import br.com.projeto.todolist.user.dtos.UserDTO;
import br.com.projeto.todolist.user.dtos.UserFilterDTO;
import br.com.projeto.todolist.user.models.User;
import br.com.projeto.todolist.user.repositories.UserRepository;
import br.com.projeto.todolist.user.dtos.UserUpdtDTO;
import br.com.projeto.todolist.user.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    @Transactional
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserDTO userDTO){
        return userService.save(userDTO);
    }

    @PostMapping("auth/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginDTO loginDTO){
        return userService.login(loginDTO);
    }
    @PostMapping("/auth/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request){
        return userService.logout(request);
    }

    @GetMapping("/search-service")
    public ResponseEntity<?> getSearchService(){
      return ResponseEntity.status(HttpStatus.OK).body("Deu certo");
    }

    @GetMapping
    public ResponseEntity<List<?>> getAllUser(){
        return userService.getAll();
    }

    @PutMapping("update/{id}")
    @Transactional
    public ResponseEntity<?> updateUser(@PathVariable long id,@RequestBody @Valid UserUpdtDTO userUpdtDTO){
        return userService.update(id, userUpdtDTO);
    }

    @DeleteMapping("/delete-user/{id}")
    @Transactional
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
       return userService.delete(id);
    }

    @GetMapping("/find-user-by")
    public ResponseEntity<?>findUserByFilter(@RequestBody UserFilterDTO userFilterDTO) {
        return userService.findUserByFilter(userFilterDTO);
    }
}
