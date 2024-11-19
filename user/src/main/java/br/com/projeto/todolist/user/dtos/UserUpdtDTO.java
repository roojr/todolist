package br.com.projeto.todolist.user.dtos;

import br.com.projeto.todolist.user.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

public record UserUpdtDTO(
        @NotBlank(message = "Please provide email") @Email(message = "Invalid Email format") String email,
        @NotBlank(message = "Please provide email") String username,
        @NotBlank(message = "Please provide password")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,}$",
                message ="{password.pattern}")String password,
        @NotBlank(message = "Please provide firtName") String firstName,
        @NotBlank(message = "Please provide lastName") String lastName,
        @NotNull(message = "Please provide birthDate") LocalDate birthDate){

    public void uptFromDto(User user, UserUpdtDTO userUpdtDTO){
        String passwordCripted = new BCryptPasswordEncoder().encode(userUpdtDTO.password());
        user.setEmail(userUpdtDTO.email());
        user.setUsername(userUpdtDTO.username());
        user.setFirstName(userUpdtDTO.firstName());
        user.setLastName(userUpdtDTO.lastName());
        user.setBirthDate(userUpdtDTO.birthDate());
        user.setPassword(passwordCripted);
    }
}
