package br.com.projeto.todolist.user.dtos;

import br.com.projeto.todolist.user.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;


public record UserDTO(
        @NotBlank(message = "Please provide username") String username,
        @NotBlank(message = "Please provide password") String password,
        @NotBlank(message = "Please provide email") @Email(message = "Invalid Email format") String email,
        @NotBlank(message = "Please provide firtName") String firstName,
        @NotBlank(message = "Please provide lastName") String lastName,
        LocalDate birthDate,
        @NotBlank(message = "Please provide cpf") @CPF(message = "Invalid CPF format") String cpf) {

    public static User fromUserDTO(UserDTO userDTO){
        User user = User.builder()
                .username(userDTO.username)
                .email(userDTO.email)
                .firstName(userDTO.firstName)
                .lastName(userDTO.lastName)
                .birthDate(userDTO.birthDate)
                .cpf(userDTO.cpf)
                .build();
        return user;
    }

}
