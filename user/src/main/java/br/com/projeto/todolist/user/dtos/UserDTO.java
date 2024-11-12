package br.com.projeto.todolist.user.dtos;

import br.com.projeto.todolist.user.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public record UserDTO(
        @NotBlank(message = "Please provide login") String login,
        @NotBlank(message = "Please provide password") String password,
        @NotBlank(message = "Please provide firtName") String firstName,
        @NotBlank(message = "Please provide lastName") String lastName,
        @NotNull(message = "Please provide birthDate") LocalDate birthDate,
        @NotBlank(message = "Please provide lastName") String cpf) {

    public static User fromUserDTO(UserDTO userDTO){
        User user = User.builder()
                .login(userDTO.login)
                .firstName(userDTO.firstName)
                .lastName(userDTO.lastName)
                .birthDate(userDTO.birthDate)
                .cpf(userDTO.cpf)
                .build();
        return user;
    }

}
